/**
 * Implementation of the Piece class for a Bishop
 * 
 * @author Jonthan Buchanan
 *
 **/
import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece {
    /**
     * Creates a Bishop object.
     * 
     * @param c     determines the side that the chess piece belongs to.
     **/
    public Bishop(Color c) {
        super(c);

        fenName = 'B';
        if (c.equals(Color.BLACK)) {
            fenName = Character.toLowerCase(fenName);
        }
    }

    @Override
    /**
     * An accessor method for the piece's symbol
     * 
     * @return      the symbol for the piece in algebraic notation
     **/
    public String algebraicName() {
        return "" + Character.toUpperCase(fenName);
    }

    @Override
    /**
     * An accessor method for the piece's symbol
     * 
     * @return      the symbol for the piece in FEN
     **/
    public String fenName() {
        return "" + fenName;
    }

    @Override
    /**
     * Gives every move the bishop could move to from a given square assuming an empty board
     *
     * @param start the square the bishop starts on
     * @return      array of squares containing possible moves
     **/
    public Square[] movesFrom(Square start) { 
        ArrayList<Square> validSquares = new ArrayList<Square>();
        validSquares.addAll(Arrays.asList(rayCaster(start, 1, 1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, -1, 1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, -1, -1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 1, -1)));

        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
