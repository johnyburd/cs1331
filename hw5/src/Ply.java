import java.util.Optional;

/**
 * a class representatino of a chess halfmove
 *
 * @author Jonathan Buchanan
 * @version 1
 */
public class Ply {
    private Piece piece;
    private Square from;
    private Square to;
    private Optional<String> comment;

    /**
     * Constructs a Ply object
     *
     * @param p piece that moves
     * @param f starting square
     * @param t final square
     * @param c comment
     */
    public Ply(Piece p, Square f, Square t, Optional<String> c) {
        piece = p;
        from = f;
        to = t;
        comment = c;
    }

    /**
     * Constructs a Ply Object
     *
     * @param p piece that moves
     * @param f starting square
     * @param t final square
     */
    public Ply(Piece p, Square f, Square t) {
        piece = p;
        from = f;
        to = t;
        comment = null;
    }

    /**
     * accessor method for the piece moved
     *
     * @return the piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * accessor method for the final square
     *
     * @return the final square
     */
    public Square getTo() {
        return to;
    }

    /**
     * accessor method for the initial square
     *
     * @return the initial square
     */
    public Square getFrom() {
        return from;
    }

    /**
     * accessor for the comment
     *
     * @return the comment
     */
    public Optional<String> getComment() {
        return comment;
    }


}
