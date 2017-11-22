/**
 * A data class for a  series of chess moves with options to filter
 * @author Jonathan Buchanan
 * @version 1
 */
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChessGame {
    private List<Move> moves;

    /**
     * Creates an object containing the list of moves given.
     *
     * @param m
     */
    public ChessGame(List<Move> m) {
        moves = new ArrayList<Move>(m);
    }

    /**
     * returns the nth move
     *
     * @param n
     * @return nth move
     */
    public Move getMove(int n){
        return moves.get(n);
    }

    /**
     * filter a List of moves that pass the given Predicate
     *
     * @param filter
     * @return list of moves
     */
    public List<Move> filter(Predicate<Move> filter) {
        return moves.stream().filter(filter).collect(Collectors.<Move>toList());
    }

    /**
     * return the list of moves that have comment
     *
     * @return list of moves with comments
     */
    public List<Move> getMovesWithComment() {
        return filter(new Predicate<Move>() {
            @Override
            public boolean test(Move m) {
                return m.getBlackPly().getComment().isPresent() || m.getWhitePly().getComment().isPresent();
            }
        });
    }

    /**
     * a filter for the list of moves with comments
     *
     * @return list of moves without comments
     */
    public List<Move> getMovesWithoutComment() {
        class CommentPredicate implements Predicate<Move> {
            @Override
            public boolean test(Move m) {
                return !(m.getBlackPly().getComment().isPresent() || m.getWhitePly().getComment().isPresent());
            }
            
        }
        return filter(new CommentPredicate());

    }

    /**
     * a filter for moves that include a specific piece
     *
     * @param p
     * @return moves that involve a certain piece
     */
    public List<Move> getMovesWithPiece(Piece p) {
        return filter(m -> m.getBlackPly().getPiece().equals(p) || m.getWhitePly().getPiece().equals(p));
    }

    /**
     * Getter for the moves list
     *
     * @return list of moves
     */
    public List<Move> getMoves() {
        return moves;
    }
}
