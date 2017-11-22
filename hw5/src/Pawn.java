/**
 * class that represents a pawn chesspiece
 * @author Jonathan Buchanan
 * @version 1
 */
public class Pawn extends Piece {
    /**
     * Creates a Pawn object
     *
     * @param c     determines the color of the piece
     */
    public Pawn(Color c) {
        super(c);
    }

    /**
     * gives the algebraicName of the pwan
     *
     * @return      algebraicName
     */
    public String algebraicName() {
        return "";
    }

    /**
     * gives the fen name of the pawn
     *
     * @return fenname
     */
    public String fenName() {
        return getColor() == Color.WHITE ? "P" : "p";
    }

    /**
     * calculates the squares the pawn could move to
     *
     * @param square    starting square
     * @return          arary of potential squares
     */
    public Square[] movesFrom(Square square) {
        char rank = square.getRank();
        char file = square.getFile();
        if (getColor() == Color.WHITE) {
            if (rank == '8') {
                return new Square[0];
            } else if (rank == '2') {
                return new Square[]{new Square(file, '4'),
                    new Square(file, '3')};
            } else {
                return new Square[]
                    {new Square(file, (char) (rank + 1))};
            }
        } else {
            if (rank == '1') {
                return new Square[0];
            } else if (rank == '7') {
                return new Square[]{new Square(file, '5'),
                    new Square(file, '6')};
            } else {
                return new Square[]{new Square(file, (char) (rank - 1))};
            }
        }
    }
}
