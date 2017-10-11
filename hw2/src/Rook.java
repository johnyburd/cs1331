/**
 * Implementation of the Piece class for a Rook
 * 
 * @author Jonathan Buchanan
 * 
 **/
import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece {
    /**
     * Create a Rook object
     * 
     * @param c     determines the side that the rook belongs to
     **/
    public Rook(Color c) {
        super(c);

        fenName = 'R';
        if (c.equals(Color.BLACK))
            fenName = Character.toLowerCase(fenName);
    }

    @Override
    /**
     * An accessor for the Rook's symbol in algebraic notation
     * 
     * @return      symbol for the Rook's piece
     **/
    public String algebraicName() {
        return "" + Character.toUpperCase(fenName);
    }

    @Override
    /**
     * An accessor for the Rook's symbol in FEN
     * 
     * @return      symbol for the Rook's piece
     **/
    public String fenName() {
        return "" + fenName;
    }

    @Override
    /**
     * Gives every square the Rook could move to from a given square assuming an empty board
     * 
     * @param start the square the rook starts on
     * @return      array of squares containing possible moves
     **/
    public Square[] movesFrom(Square start) { 
        ArrayList<Square> validSquares = new ArrayList<Square>();
        validSquares.addAll(Arrays.asList(rayCaster(start, 1, 0)));
        validSquares.addAll(Arrays.asList(rayCaster(start, -1, 0)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 0, 1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 0, -1)));

        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
