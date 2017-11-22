/**
 * this class represents a king in chess
 * @author Jonathan Buchanan
 * @version 1
 */
public class King extends Piece {
    /**
     * Creates a king object
     *
     * @param c side of the king object
     */
    public King(Color c) {
        super(c);
    }

    /**
     * gives the algebraic name of the king
     *
     * @return  the algrebraicName of the king
     */
    public String algebraicName() {
        return "K";
    }

    /**
     * gives the fenName of the King
     *
     * @return the fenName of the King
     */
    public String fenName() {
        return getColor() == Color.WHITE ? "K" : "k";
    }

    /**
     * calculate all the squares the king could move to from a given space
     *
     * @param square    starting square
     * @return          array of potential squares
     */
    public Square[] movesFrom(Square square) {
        Square[] sq = new Square[8];
        int counter = 0;
        char rank = square.getRank();
        char file = square.getFile();
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (r == 0 && c == 0) {
                    continue;
                }
                if (isInBoard((char) (file + c), (char) (rank + r))) {
                    sq[counter++] =
                        new Square((char) (file + c), (char) (rank + r));
                }
            }
        }

        Square[] full = new Square[counter];
        for (int i = 0; i < counter; i++) {
            full[i] = sq[i];
        }

        return full;
    }
}
