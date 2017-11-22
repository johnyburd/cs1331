/**
 * class that reperesents an abstract chesspiece
 * @author Jonathan Buchanan
 * @version 1
 */
public abstract class Piece {
    private Color color;

    /**
     * creates a Piece object
     *
     * @param c determines the color of the piece
     */
    public Piece(Color c) {
        color = c;
    }

    /**
     * accessor method for piece's color
     *
     * @return      color
     */
    public Color getColor() {
        return color;
    }

    /**
     * check to confirm the piece is in the board's bounds
     *
     * @param file      the file of the piece
     * @param rank      the rank of the piece
     * @return          true if the piece is within the board paramaters
     */
    public boolean isInBoard(char file, char rank) {
        return file >= 'a' && file <= 'h' && rank >= '1' && rank <= '8';
    }

    /**
     * give the algebraicName of the piece
     *
     * @return the algrebraicname
     */
    public abstract String algebraicName();

    /**
     * give the fen name of the piece
     *
     * @return fenname
     */
    public abstract String fenName();

    /**
     * calculate the squares the piece could move to
     *
     * @param square the starting square
     * @return       the array of potential squares
     */
    public abstract Square[] movesFrom(Square square);

    /**
     * {@inheritDoc}
     *
     * @see Object#toString()
     */
    public String toString() {
        return color.toString() + " " + this.getClass();
    }

    /**
     * check if two pieces are equal
     *
     * @param p    the piece to compare to
     * @return     true if the pieces are equal, otherise false
     */
    @Override
    public boolean equals(Object o) {
        Piece p;
        try {
            p = (Piece) o;
            return this.color == p.color
                && this.getClass().equals(p.getClass());
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * return a hashcode
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return 1;
    }
}
