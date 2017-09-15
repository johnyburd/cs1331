import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PgnReader {

    private static boolean whitesTurn = true;
    private static int fullMove = 0;
    private static int halfMove = 0;
    private static String castle = "KQkq";
    private static String passant = "-";

    private static char[][] board = {
        {'r','n','b','q','k','b','n','r'},
        {'p','p','p','p','p','p','p','p'},
        {'0','0','0','0','0','0','0','0'},
        {'0','0','0','0','0','0','0','0'},
        {'0','0','0','0','0','0','0','0'},
        {'0','0','0','0','0','P','P','0'},
        {'P','P','P','P','P','0','0','P'},
        {'R','N','B','Q','K','B','N','R'}};

    private static int numMoves(String game)
    {
        String[] numbers;
        numbers = game.split("\\d\\d?[.][ \n]");
        return numbers.length - 1;
    }
    /**
     * idk if this is even supposed to be here
     */
    private static String getFEN() {
        String fen = "";
        char turn = 'b';
        for (int i=0;i<8;i++) {
            int k = 0;
            for (int j=0;j<8;j++) {
                char square = board[i][j];
                if (square != '0')
                {
                    if (k > 0)
                    {
                        fen += k;
                        k = 0;
                    }
                    fen += square;
                }
                else if (k > 0 && j == 7)
                    fen += k;
                else
                    k++;
           }
           fen += "/";
        }

        if (whitesTurn)
            turn = 'w';

        return fen + " " + turn + " " + castle + " " + passant + " " + halfMove + " " + fullMove;
    }

    /**
     * Find the tagName tag pair in a PGN game and return its value.
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
     *
     * @param tagName
     *            the name of the tag whose value you want
     * @param game
     *            a `String` containing the PGN text of a chess game
     * @return the value in the named tag pair
     */
    public static String tagValue(String tagName, String game) {
        Pattern pattern = Pattern.compile("\\["+tagName+" \"(.*?)\"(.|\\s)*");
        Matcher matcher = pattern.matcher(game);
        if (matcher.find())
            return matcher.group(1);
        return "NOT GIVEN";
    }
    
    /**
     * Play out the moves in game and return a String with the game's
     * final position in Forsyth-Edwards Notation (FEN).
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm#c16.1
     *
     * @param game a `Strring` containing a PGN-formatted chess game or opening
     * @return the game's final position in FEN.
     */
    public static String finalPosition(String game) {
        return getFEN();
    }

    /**
     * Reads the file named by path and returns its content as a String.
     *
     * @param path the relative or abolute path of the file to read
     * @return a String containing the content of the file
     */
    public static String fileContent(String path) {
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Add the \n that's removed by readline()
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String game = fileContent(args[0]);
        System.out.format("Event: %s%n", tagValue("Event", game));
        System.out.format("Site: %s%n", tagValue("Site", game));
        System.out.format("Date: %s%n", tagValue("Date", game));
        System.out.format("Round: %s%n", tagValue("Round", game));
        System.out.format("White: %s%n", tagValue("White", game));
        System.out.format("Black: %s%n", tagValue("Black", game));
        System.out.format("Result: %s%n", tagValue("Result", game));
        System.out.println("Final Position:");
        System.out.println(finalPosition(game));
        System.out.format("number of moves: %d%n", numMoves(game));

    }
}
