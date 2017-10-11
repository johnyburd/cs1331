/**
 * Implementation of the Piece class for a Knight
 * 
 * @author Jonathan Buchanan
 *
 **/
import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Piece {
    /**
     * Create a Queen object
     * 
     * @param c     determines  the side that the knight belongs to
     **/
    public Queen(Color c) {
        super(c);

        fenName = 'Q';
        if (c.equals(Color.BLACK))
            fenName = Character.toLowerCase(fenName);
    }

    @Override
    /**
     * An accessor methof for the Queen's symbol in FEN
     * 
     * @return      symbol for the Queen's piece
     **/
    public String algebraicName() {
        return "" + Character.toUpperCase(fenName);
    }

    @Override
    /**
     * An accessor methof for the Queen's symbol in FEN
     * 
     * @return      symbol for the Queen's piece
     **/
    public String fenName() {
        return "" + fenName;
    }

    @Override
    /**
     * Gives every square the Queen could move to from a given square assuming an empty board
     * 
     * @param start the square the queen starts on
     * @return      array of squares containing possible moves
     **/
    public Square[] movesFrom(Square start) { 
        ArrayList<Square> validSquares = new ArrayList<Square>();
        validSquares.addAll(Arrays.asList(rayCaster(start, 1, 0)));
        validSquares.addAll(Arrays.asList(rayCaster(start, -1, 0)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 0, 1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 0, -1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 1, 1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, -1, 1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, -1, -1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 1, -1)));

        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
