import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;

public class PgnReader {

    private static boolean whitesTurn = true;
    private static int fullMove = 0;
    private static int halfMove = 0;
    private static String castle = "KQkq";
    private static String passant = "-";

    private static char[][] board = {
            { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
            { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
            { '0', '0', '0', '0', '0', '0', '0', '0' },
            { '0', '0', '0', '0', '0', '0', '0', '0' },
            { '0', '0', '0', '0', '0', '0', '0', '0' },
            { '0', '0', '0', '0', '0', 'P', 'P', '0' },
            { 'P', 'P', 'P', 'P', 'P', '0', '0', 'P' },
            { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' }};
    private static boolean isWhite(char p)
    {
        return Character.isUpperCase(p);
    }
    private static boolean isBlack(char p)
    {
        return Character.isLowerCase(p);
    }
    /**
     * Cast a ray to find if there is a piece in any direction from a given square.
     *
     * @param theoreticalBoard
     *          board on which the arrays will be cast
     * @param i
     *          y value (rank/row) of the square
     * @param j
     *          x value (file/column) of the square
     * @param x
     *          x component of the vector that determines the direction of the ray
     * @param y
     *          y component of the vector that determines the direction of the ray
     *          
     * @return the coordinates of the first piece the ray collides with, or '0' if none
     */
    private static int[] rayCasterCoords(char[][] theoreticalBoard, int i, int j, int x, int y)
    {
        j += x; // go ahead and add one iteration cause we
        i -= y; // don't want to return our own piece
        while (i>=0 && j>=0 && i<8 && j<8)
        {
            if (theoreticalBoard[i][j] != '0')
                return new int[]{i, j};
            j += x;
            i -= y;
        }
        return new int[]{-1,-1}; // ray ran off the board
    }
    /**
     * Cast a ray to find if there is a piece in any direction from a given square.
     *
     * @param theoreticalBoard
     *          board on which the arrays will be cast
     * @param i
     *          y value (rank/row) of the square
     * @param j
     *          x value (file/column) of the square
     * @param x
     *          x component of the vector that determines the direction of the ray
     * @param y
     *          y component of the vector that determines the direction of the ray
     *          
     * @return the first piece the ray collides with, or '0' if none
     */
    private static char rayCaster(char[][] theoreticalBoard, int i, int j, int x, int y)
    {
        j += x; // go ahead and add one iteration cause we
        i -= y; // don't want to return our own piece
        while (i>=0 && j>=0 && i<8 && j<8)
        {
            if (theoreticalBoard[i][j] != '0')
                return theoreticalBoard[i][j];
            j += x;
            i -= y;
        }
        return '0'; // ray ran off the board
    }

    private static boolean whiteInCheck(char[][] theoreticalBoard)
    {
        for (int i=0;i<8;i++) {
            for (int j=0;j<8;j++) {
                char p = theoreticalBoard[i][j];
                switch (p) {
                    case 'r':
                        if (rayCaster(theoreticalBoard, i, j, 1, 0) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, 0, 1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, -1,0) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, 0,-1) == 'K')
                            return true;
                        break;
                    case 'n':
                        // I don't think there's actually any point in checking
                        // this because a knight can't pin anything.
                        //
                        // ^ that is stupid reasoning and fake news
                        break;
                    case 'b':
                        if (rayCaster(theoreticalBoard, i, j, 1, 1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, -1,-1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, -1, 1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, 1, -1) == 'K')
                            return true;
                        break;
                    case 'q':
                        if (rayCaster(theoreticalBoard, i, j, 1, 0) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, 0, 1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, -1,0) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, 0,-1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, 1, 1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, -1,-1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, -1, 1) == 'K' ||
                                rayCaster(theoreticalBoard, i, j, 1, -1) == 'K')
                            return true;
                        break;
                    case 'k':
                        for (int k=-1;k<2;k++)
                        {
                            for (int l=-1;l<2;l++)
                            {
                                if ((k+i >= 0 && k+i < 8) && (l+j >= 0 && l+j < 8))
                                {
                                    if (theoreticalBoard[i+k][j+l] == 'K')
                                        return true;
                                }

                            }
                        }
                        break;

                    case 'p':
                        //if (theoreticalBoard
                        break;
                }
            }
        }
        return false;
    }
    private static boolean blackInCheck(char[][] theoreticalBoard)
    {
        for (int i=0;i<8;i++) {
            for (int j=0;j<8;j++) {
                // switch entire board so we can reuse whiteInCheck method
                char square = theoreticalBoard[i][j];
                if (isWhite(square))
                    theoreticalBoard[i][j] = Character.toLowerCase(square);
                else if (isBlack(square))
                    theoreticalBoard[i][j] = Character.toUpperCase(square);
            }
        }
        //TODO board has to be flipped vertically in addition for this to actually work
        return whiteInCheck(theoreticalBoard);
    }


    private static boolean isLegal(int iR, int iF, int fF, int fR, boolean whitesTurn) {
        char piece = board[iR][iF];
        char finalSquare = board[fR][fF];
        char[][] theoreticalBoard = board.clone();
        int[] fSquare = new int[]{fR, fF};
        theoreticalBoard[fR][fF] = board[iR][iF]; // new board as if move is done
        theoreticalBoard[iR][iF] = '0';

        if ((isWhite(piece) && isWhite(finalSquare)) || (!isWhite(piece) && !isWhite(finalSquare)))
            return false; // if a piece is trying to move onto its own, there's
                          // something wrong and it's def not legal
        
        if (whitesTurn)
            if (whiteInCheck(theoreticalBoard))
                return false;  // white's move can't put white in check
        else
            if (blackInCheck(theoreticalBoard))
                return false; // black's move can't put black in check

        switch (piece) {
        case 'r':
        case 'R':
            int[] right = rayCasterCoords(theoreticalBoard, iR, iF, 1, 0);
            int[] left = rayCasterCoords(theoreticalBoard, iR, iF, -1, 0);
            int[] up = rayCasterCoords(theoreticalBoard, iR, iF, 0, 1);
            int[] down = rayCasterCoords(theoreticalBoard, iR, iF, 0, -1);
            if (!Arrays.equals(fSquare, up) &&
                !Arrays.equals(fSquare, down) &&
                !Arrays.equals(fSquare, left) &&
                !Arrays.equals(fSquare, right))
                return false; // actual movement is not equal to raycast in any drection; not valid
            break;

        case 'n':
        case 'N':
            //TODO knights.  this looks hard.
            break;
        case 'b':
        case 'B':
            int[] upRight = rayCasterCoords(theoreticalBoard, iR, iF, 1, 1);
            int[] upLeft = rayCasterCoords(theoreticalBoard, iR, iF, -1, 1);
            int[] downRight = rayCasterCoords(theoreticalBoard, iR, iF, 1, -1);
            int[] downLeft = rayCasterCoords(theoreticalBoard, iR, iF, -1, -1);
            if (!Arrays.equals(fSquare, upRight) &&
                !Arrays.equals(fSquare, upRight) &&
                !Arrays.equals(fSquare, upRight) &&
                !Arrays.equals(fSquare, upRight))
                return false; // actual movement is not equal to raycast in any drection; not valid
            break;
        case 'q':
        case 'Q':
            int[] upRight = rayCasterCoords(theoreticalBoard, iR, iF, 1, 1);
            int[] upLeft = rayCasterCoords(theoreticalBoard, iR, iF, -1, 1);
            int[] downRight = rayCasterCoords(theoreticalBoard, iR, iF, 1, -1);
            int[] downLeft = rayCasterCoords(theoreticalBoard, iR, iF, -1, -1);
            int[] right = rayCasterCoords(theoreticalBoard, iR, iF, 1, 0);
            int[] left = rayCasterCoords(theoreticalBoard, iR, iF, -1, 0);
            int[] up = rayCasterCoords(theoreticalBoard, iR, iF, 0, 1);
            int[] down = rayCasterCoords(theoreticalBoard, iR, iF, 0, -1);
            if (!Arrays.equals(fSquare, upRight) &&
                !Arrays.equals(fSquare, upRight) &&
                !Arrays.equals(fSquare, upRight) &&
                !Arrays.equals(fSquare, upRight) &&
                !Arrays.equals(fSquare, up) &&
                !Arrays.equals(fSquare, down) &&
                !Arrays.equals(fSquare, left) &&
                !Arrays.equals(fSquare, right))
                return false; // actual movement is not equal to raycast in any drection; not valid
            break;
        case 'k':
        case 'K':
            break;
        case 'p':
        case 'P':
            break;
        }
        return true;
    }

    private static int numMoves(String game) {
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
        for (int i = 0; i < 8; i++) {
            int k = 0;
            int mod = 1;
            for (int j = 0; j < 8; j++) {
                char square = board[i][j];
                if (square != '0') {
                    if (k > 0) {
                        fen += k;
                        k = 0;
                    }
                    fen += square;
                    mod = 0;
                } else if (k > 0 && j == 7)
                    fen += k + mod;
                else
                    k++;
            }
            fen += "/";
        }

        if (whitesTurn)
            turn = 'w';

        return fen + " " + turn + " " + castle + " " + passant + " " + halfMove
                + " " + fullMove;
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
        Pattern pattern = Pattern
                .compile("\\[" + tagName + " \"(.*?)\"(.|\\s)*");
        Matcher matcher = pattern.matcher(game);
        if (matcher.find())
            return matcher.group(1);
        return "NOT GIVEN";
    }

    /**
     * Play out the moves in game and return a String with the game's final
     * position in Forsyth-Edwards Notation (FEN).
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm#c16.1
     *
     * @param game
     *            a `Strring` containing a PGN-formatted chess game or opening
     * @return the game's final position in FEN.
     */
    public static String finalPosition(String game) {
        return getFEN();
    }

    private static String getMove(int i, String game) {
        Pattern pattern = Pattern
                .compile("(.|\\s)*?"+i+"\\.[ \n]+(.*?)("+(i+1)+"\\.|\\n)");
        Matcher matcher = pattern.matcher(game);
        if (matcher.find())
        {
            return matcher.group(2);
        }
        return "";
    }
    private static String[] getMoves(String game) {
        //TODO redo moves method because it can sometimes backtrace to infinity if the file is wrong
        return new String[]{"hello"};
    }

    private static void fullMove(int i, String move) {
        System.out.println("fullmove: " +move);
        Pattern pattern = Pattern.compile("([^ ]*)( ?)(.*)?");
        Matcher matcher = pattern.matcher(move);
        matcher.matches();

        String whiteMove = matcher.group(1);
        whiteMove = whiteMove.replaceAll("[^a-zA-Z\\d]","");
        String blackMove = "";
        blackMove = matcher.group(3);
        blackMove = blackMove.replaceAll("[^a-zA-Z\\d]","");

        System.out.println("whitemove: " +whiteMove);
        System.out.println("blackmove: " +blackMove);

    }

    /**
     * Reads the file named by path and returns its content as a String.
     *
     * @param path
     *            the relative or abolute path of the file to read
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

        for (int i=1;i<=numMoves(game);i++)
        {
            System.out.println(i);
            fullMove(i,getMove(i, game));
        }
        //int[] a = {1,1};
        //isLegal(a,a);

    }
}
