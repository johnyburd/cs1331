/**
 * Class that representnss a Bishop piece
 * @author Jonathan Buchanan
 * @version 1
 */
public class Bishop extends Piece {
    /**
     * Constructor for a bishop
     *
     * @param c     color of the piece
     */
    public Bishop(Color c) {
        super(c);
    }

    /**
     * algebraicName of the Bishop
     *
     * @return algebraic name of the bishop
     */
    public String algebraicName() {
        return "B";
    }

    /**
     * fenname of the piece
     *
     * @return fenname of the piece
     */
    public String fenName() {
        return getColor() == Color.WHITE ? "B" : "b";
    }

    /**
     * calculates the squares the piece could move to given a starting place
     *
     * @param square    the starting square
     * @return          an array of potential movement places
     */
    public Square[] movesFrom(Square square) {
        Square[] sq = new Square[27];
        int counter = 0;
        char rank = square.getRank();
        char file = square.getFile();
        for (int i = 1; i <= 8; i++) {
            char[] ranks = new char[]{(char) (rank + i), (char) (rank - i)};
            char[] files = new char[]{(char) (file + i), (char) (file - i)};
            if (isInBoard(files[0], ranks[0])) {
                sq[counter++] = new Square(files[0], ranks[0]);
            }
            if (isInBoard(files[1], ranks[0])) {
                sq[counter++] = new Square(files[1], ranks[0]);
            }
            if (isInBoard(files[0], ranks[1])) {
                sq[counter++] = new Square(files[0], ranks[1]);
            }
            if (isInBoard(files[1], ranks[1])) {
                sq[counter++] = new Square(files[1], ranks[1]);
            }
        }

        Square[] full = new Square[counter];
        for (int i = 0; i < counter; i++) {
            full[i] = sq[i];
        }

        return full;
    }

}
