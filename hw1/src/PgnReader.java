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
        {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
        {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
        {'0', '0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0', '0'},
        {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
        {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'} };

    private static boolean isWhite(char p) {
        return Character.isUpperCase(p);
    }

    private static boolean isBlack(char p) {
        return Character.isLowerCase(p);
    }

    // is target between start and stop?
    private static boolean isBetween(int[] start, int[] stop, int[] target,
            int x, int y) {
        int rank;
        int file;
        for (int i = 1; i <= 8; i++) {
            rank = start[0] + (y * i * -1);
            file = start[1] + (x * i);
            if (rank < 8 && rank >= 0 && file < 8 && file >= 0) {
                if (rank == target[0] && file == target[1]) {
                    return true;
                }
                if (rank == stop[0] && file == stop[1]) {
                    return false;
                }
            }
        }
        return false;

    }

    /**
     * Cast a ray to find if there is a piece in any direction from a given
     * square.
     *
     * @param theoreticalBoard
     *            board on which the arrays will be cast
     * @param i
     *            y value (rank/row) of the square
     * @param j
     *            x value (file/column) of the square
     * @param x
     *            x component of the vector that determines the direction of the
     *            ray
     * @param y
     *            y component of the vector that determines the direction of the
     *            ray
     *
     * @return the coordinates of the first piece the ray collides with, or '0'
     *         if none
     */
    private static int[] rayCasterCoords(char[][] theoreticalBoard, int i,
            int j, int x, int y) {
        j += x; // go ahead and add one iteration cause we
        i -= y; // don't want to return our own piece
        while (i >= 0 && j >= 0 && i < 8 && j < 8) {
            if (theoreticalBoard[i][j] != '0') {
                return new int[] {i, j};
            }
            j += x;
            i -= y;
        }
        return new int[] {-1, -1}; // ray ran off the board
    }

    /**
     * Cast a ray to find if there is a piece in any direction from a given
     * square.
     *
     * @param theoreticalBoard
     *            board on which the arrays will be cast
     * @param i
     *            y value (rank/row) of the square
     * @param j
     *            x value (file/column) of the square
     * @param x
     *            x component of the vector that determines the direction of the
     *            ray
     * @param y
     *            y component of the vector that determines the direction of the
     *            ray
     *
     * @return the first piece the ray collides with, or '0' if none
     */
    private static char rayCaster(char[][] theoreticalBoard, int i, int j,
            int x, int y) {
        j += x; // go ahead and add one iteration cause we
        i -= y; // don't want to return our own piece
        while (i >= 0 && j >= 0 && i < 8 && j < 8) {
            if (theoreticalBoard[i][j] != '0') {
                return theoreticalBoard[i][j];
            }
            j += x;
            i -= y;
        }
        return '0'; // ray ran off the board
    }

    private static boolean inCheck(char[][] theoreticalBoard, char king) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char p = theoreticalBoard[i][j];
                char rook = 'r';
                char knight = 'n';
                char bishop = 'b';
                char enemyKing = 'k';
                char queen = 'q';
                char pawn = 'p';
                if (isBlack(king)) {
                    rook = 'R';
                    knight = 'N';
                    bishop = 'B';
                    enemyKing = 'K';
                    queen = 'Q';
                    pawn = 'P';
                }
                if (p == rook) {
                    if (rayCaster(theoreticalBoard, i, j, 1, 0) == king
                            || rayCaster(theoreticalBoard, i, j, 0, 1) == king
                            || rayCaster(theoreticalBoard, i, j, -1, 0) == king
                            || rayCaster(theoreticalBoard, i, j, 0,
                                    -1) == king) {
                        return true;
                    }
                }
                //if (p == knight) {
                    //System.out.println();
                    // I don't think there's actually any point in checking
                    // this because a knight can't pin anything.
                    // TODO
                    // ^ that is stupid reasoning and fake news
                    // king could still try to walk into it
                //}
                if (p == bishop) {
                    if (rayCaster(theoreticalBoard, i, j, 1, 1) == king
                            || rayCaster(theoreticalBoard, i, j, -1, -1) == king
                            || rayCaster(theoreticalBoard, i, j, -1, 1) == king
                            || rayCaster(theoreticalBoard, i, j, 1,
                                    -1) == king) {
                        return true;
                    }
                }
                if (p == queen) {
                    if (rayCaster(theoreticalBoard, i, j, 1, 0) == king
                            || rayCaster(theoreticalBoard, i, j, 0, 1) == king
                            || rayCaster(theoreticalBoard, i, j, -1, 0) == king
                            || rayCaster(theoreticalBoard, i, j, 0, -1) == king
                            || rayCaster(theoreticalBoard, i, j, 1, 1) == king
                            || rayCaster(theoreticalBoard, i, j, -1, -1) == king
                            || rayCaster(theoreticalBoard, i, j, -1, 1) == king
                            || rayCaster(theoreticalBoard, i, j, 1,
                                    -1) == king) {
                        return true;
                    }
                }
                if (p == enemyKing) {
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if ((k + i >= 0 && k + i < 8)
                                    && (l + j >= 0 && l + j < 8)) {
                                if (theoreticalBoard[i + k][j + l] == king) {
                                    return true;
                                }
                            }

                        }
                    }
                }

                //if (p == pawn) {
                    //System.out.println();
                    // TODO pawn check
                //}
            }
        }
        return false;
    }

    private static boolean isLegal(String c, boolean isWhitesTurn) {
        char king = 'K';
        char queen = 'Q';
        if (!isWhitesTurn) {
            king = 'k';
            queen = 'q';
        }
        if (c.equals("000")) { // queen side castling
            if (castle.indexOf(queen) == -1) {
                return false;
            }
        }
        if (c.equals("00")) { // king side castling
            if (castle.indexOf(king) == -1) {
                return false;
            }
        }
        // else {
        // return false;
        // }
        return true;
    }

    private static boolean isLegal(int iR, int iF, int fR, int fF,
            boolean isWhitesTurn) {
        char piece = board[iR][iF];
        char finalSquare = board[fR][fF];
        char[][] theoreticalBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                theoreticalBoard[i][j] = board[i][j];
            }
        }
        int[] fSquare = new int[] {fR, fF};
        int[] iSquare = new int[] {iR, iF};
        theoreticalBoard[fR][fF] = board[iR][iF]; // board w/move done
        theoreticalBoard[iR][iF] = '0';
        int passantR = -1;
        int passantF = -1;
        if (!passant.equals("-")) {
            passantR = 8 - Integer.valueOf(passant.charAt(1) + "");
            // subtracting 97 from the char will give a value from 0 to 7
            // that will correspond to the file in the array
            passantF = passant.charAt(0) - 97;
        }

        if ((isWhite(piece) && isWhite(finalSquare))
                || (isBlack(piece) && isBlack(finalSquare))) {

            return false; // if a piece is moving onto its own, not legal
        }


        if (isWhitesTurn) {
            if (inCheck(theoreticalBoard, 'K')) {
                return false; // white's move can't put white in check
            }
        } else {
            if (inCheck(theoreticalBoard, 'k')) {
                return false; // black's move can't put black in check
            }
        }

        int[] upRight = rayCasterCoords(board, iR, iF, 1, 1);
        int[] upLeft = rayCasterCoords(board, iR, iF, -1, 1);
        int[] downRight = rayCasterCoords(board, iR, iF, 1, -1);
        int[] downLeft = rayCasterCoords(board, iR, iF, -1, -1);
        int[] right = rayCasterCoords(board, iR, iF, 1, 0);
        int[] left = rayCasterCoords(board, iR, iF, -1, 0);
        int[] up = rayCasterCoords(board, iR, iF, 0, 1);
        int[] down = rayCasterCoords(board, iR, iF, 0, -1);

        switch (piece) {
        case 'r':
        case 'R':
            if (!isBetween(iSquare, up, fSquare, 0, 1)
                    && !isBetween(iSquare, down, fSquare, 0, -1)
                    && !isBetween(iSquare, left, fSquare, -1, 0)
                    && !isBetween(iSquare, right, fSquare, 1, 0)) {
                return false;
            }
            break;

        case 'n':
        case 'N':
            if (fF == iF + 2 || fF == iF - 2) {
                if (iR + 1 != fR && iR - 1 != fR) {
                    return false;
                }
            } else if (fR == iR - 2 || fR == iR + 2) {
                if (iF + 1 != fF && iF - 1 != fF) {
                    return false;
                }
            } else {
                return false; // if it doesn't move like a knight it's illegal
            }

            break;
        case 'b':
        case 'B':
            if (!isBetween(iSquare, upRight, fSquare, 1, 1)
                    && !isBetween(iSquare, downRight, fSquare, 1, -1)
                    && !isBetween(iSquare, downLeft, fSquare, -1, -1)
                    && !isBetween(iSquare, upLeft, fSquare, -1, 1)) {
                return false; // actual movement is not equal to raycast in any
                              // drection; not valid
            }
            break;
        case 'q':
        case 'Q':

            if (!isBetween(iSquare, upRight, fSquare, 1, 1)
                    && !isBetween(iSquare, downRight, fSquare, 1, -1)
                    && !isBetween(iSquare, downLeft, fSquare, -1, -1)
                    && !isBetween(iSquare, upLeft, fSquare, -1, 1)
                    && !isBetween(iSquare, up, fSquare, 0, 1)
                    && !isBetween(iSquare, down, fSquare, 0, -1)
                    && !isBetween(iSquare, left, fSquare, -1, 0)
                    && !isBetween(iSquare, right, fSquare, 1, 0)) {
                return false; // actual movement is not equal to raycast in any
                              // drection; not valid
            }
            break;
        case 'k':
        case 'K':
            if (Math.abs(fF - iF) > 1 || Math.abs(fR - iR) > 1) {
                return false; // king can't move more than one square
            }
            break;
        case 'p':
            if (iR >= fR) {

                return false; // black pawn can't move up the board
            }
            if (fR - iR > 1) {
                if (iR != 1 || fR - iR > 2) {
                    return false; // pawn can sometimes move two if it is second
                                  // row
                }
            }
            if (iF != fF) {
                if (Math.abs(iF - fF) > 1 || !isWhite(board[fR][fF])
                        && !(passantF == fF && passantR == fR)) {
                    return false;
                    // can't move more than one over and there has to be a white
                    // piece to take or it's moving onto the passant square
                }
            }
            break;
        case 'P':
            if (iR <= fR) {
                return false; // white pawn can't move down the board
            }
            if (iR - fR > 1) {
                if (iR != 6 || iR - fR > 2) {
                    return false; // pawn can sometimes move two if it is 7th
                                  // row
                }
            }
            if (iF != fF) {
                if (Math.abs(iF - fF) > 1 || !isBlack(board[fR][fF])
                        && !(passantF == fF && passantR == fR)) {

                    return false; // can't move more than one over
                                  // and there has to be a black piece to take
                                  // or it's moving into the passant square
                }
            }
            break;
        default:
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
            for (int j = 0; j < 8; j++) {
                char square = board[i][j];
                if (square != '0') {
                    if (k > 0) {
                        fen += k;
                        k = 0;
                    }
                    fen += square;
                } else if (k > 0 && j == 7) {
                    fen += (k + 1);
                } else if (k == 0 && j == 7) {
                    fen += 1;
                } else {
                    k++;
                }
            }
            if (i != 7) {
                fen += "/";
            }
        }

        if (whitesTurn) {
            turn = 'w';
        }

        if (castle.length() == 0) {
            castle = "-";
        }

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
        if (matcher.find()) {
            return matcher.group(1);
        }
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
        game = game.replaceAll("\n", " ");
        Pattern pattern = Pattern.compile(
                "(.|\\s)*?" + i + "\\.[ ]+(.*?)(" + (i + 1) + "\\.|$)");

        Matcher matcher = pattern.matcher(game);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }

    private static void fullMove(int i, String move) {

        Pattern pattern = Pattern.compile("([^ ]*)( ?)(.*)?");
        Matcher matcher = pattern.matcher(move);

        matcher.matches();

        String whiteMove = matcher.group(1);
        whiteMove = whiteMove.replaceAll("[^a-zA-Z\\d]", "");
        String blackMove = "";
        blackMove = matcher.group(3);
        blackMove = blackMove.replaceAll("[^a-zA-Z\\d]", "");

        // System.out.println("whitemove: " + whiteMove);
        if (!whiteMove.equals("01") && !whiteMove.equals("1212")) {
            halfMove(whiteMove);
            whitesTurn = false;
        }

        // halfMove++;
        // System.out.println("blackmove: " + blackMove);
        if (!blackMove.equals("") && !blackMove.equals("10")
                && !blackMove.equals("1212")) {
            halfMove(blackMove);
            whitesTurn = true;
        }
        fullMove++;

    }

    private static void halfMove(String s) {
        Pattern pattern = Pattern.compile(
                "([KQNBRPO]?)([a-h]??)([1-8]??)x?([a-hO])([1-8O])([QNBR]?)");
        Matcher matcher = pattern.matcher(s);
        matcher.matches();
        String piece = matcher.group(1);
        String iFile = matcher.group(2);
        String iRank = matcher.group(3);
        String fFile = matcher.group(4);
        String fRank = matcher.group(5);
        String promotion = matcher.group(6);
        int iF = -1;
        int iR = -1;
        int fF = -1;
        int fR = -1;

        if (!iFile.equals("")) {
            iF = iFile.charAt(0) - 97;
        }
        fF = fFile.charAt(0) - 97;
        if (!iRank.equals("")) {
            iR = 8 - Integer.valueOf(iRank.charAt(0) + "");
        }
        if (!fRank.equals("O")) {
            fR = 8 - Integer.valueOf(fRank.charAt(0) + "");
        }

        if (piece.equals("")) {
            piece = "P";
        }

        if (s.indexOf('x') != -1 || piece.equals("P")) {
            halfMove = 0; // piece captured or pawn moved; reset clock
        } else {
            halfMove++;
        }

        char p = piece.charAt(0);
        if (!whitesTurn) {
            p = Character.toLowerCase(p);
        }
        if (piece.equals("O")) { // queen castle
            if (isLegal(s, whitesTurn)) {
                int rank = 0;
                char king = 'k';
                char rook = 'r';
                if (whitesTurn) {
                    rank = 7;
                    king = 'K';
                    rook = 'R';
                    castle = castle.replace("KQ", "");
                } else {
                    castle = castle.replace("kq", "");
                }
                board[rank][4] = '0'; // move king
                board[rank][2] = king;
                board[rank][0] = '0'; // move rook
                board[rank][3] = rook;
            }
        } else if (fFile.equals("O") && !piece.equals("O")) {
            if (isLegal(s, whitesTurn)) {
                int rank = 0;
                char king = 'k';
                char rook = 'r';
                if (whitesTurn) {
                    rank = 7;
                    king = 'K';
                    rook = 'R';
                    castle = castle.replace("KQ", "");
                } else {
                    castle = castle.replace("kq", "");
                }
                board[rank][4] = '0'; // move king
                board[rank][6] = king;
                board[rank][7] = '0'; // move rook
                board[rank][5] = rook;
            }
        } else {
            boolean madeMove = false;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] == p && (iR == i || iR == -1)
                            && (iF == j || iF == -1)) {
                        if (isLegal(i, j, fR, fF, whitesTurn)) {
                            board[i][j] = '0';
                            board[fR][fF] = p;
                            if (!passant.equals("-") && fR == (8
                                    - Integer.valueOf(passant.charAt(1) + ""))
                                    && fF == passant.charAt(0) - 97) {
                                if (whitesTurn) {
                                    board[fR + 1][fF] = '0';
                                } else {
                                    board[fR - 1][fF] = '0';
                                }
                            }
                            // is this really more readable??
                            madeMove = true;

                            // en passant
                            passant = "-";
                            if (Character.toLowerCase(p) == 'p'
                                    && Math.abs(fR - i) == 2) {
                                passant = "";
                                passant += (char) (j + 97);
                                passant += 8 - (fR + i) / 2;
                            }
                            // promotions
                            if (!promotion.equals("")) {
                                char newPiece = promotion.charAt(0);
                                if (!whitesTurn) {
                                    newPiece = Character.toLowerCase(newPiece);
                                }
                                board[fR][fF] = newPiece;
                            }
                            // castling
                            if (i == 7 && j == 0) {
                                castle = castle.replace("Q", "");
                            }
                            if (i == 7 && j == 7) {
                                castle = castle.replace("K", "");
                            }
                            if (i == 0 && j == 0) {
                                castle = castle.replace("q", "");
                            }
                            if (i == 0 && j == 7) {
                                castle = castle.replace("k", "");
                            }
                        }
                    }
                }
            }
            if (!madeMove) {
                System.out.println("Error, no legal move found.");
                System.out.println("No point in continuing.  Exiting...");
                System.exit(0);
            }
        }
       // System.out.println("move: " + s);
       // printBoard();
    }

    public static void printBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
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
        System.
        String game = fileContent(args[0]);
        System.out.format("Event: %s%n", tagValue("Event", game));
        System.out.format("Site: %s%n", tagValue("Site", game));
        System.out.format("Date: %s%n", tagValue("Date", game));
        System.out.format("Round: %s%n", tagValue("Round", game));
        System.out.format("White: %s%n", tagValue("White", game));
        System.out.format("Black: %s%n", tagValue("Black", game));
        System.out.format("Result: %s%n", tagValue("Result", game));
        System.out.println("Final Position:");

        for (int i = 1; i <= numMoves(game); i++) {
            fullMove(i, getMove(i, game));
        }
        System.out.println(finalPosition(game));
    }
}
