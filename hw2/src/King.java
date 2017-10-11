/**
 * Implementation of the Piece class for a King
 * 
 * @author Jonathan Buchanan
 *
 **/
import java.util.ArrayList;

public class King extends Piece {
    private char fenName = 'K';
    /**
     * Create a King object
     * 
     * @param c     determines the side that the piece starts on
     **/
    public King(Color c) {
        super(c);
        if (c.equals(Color.BLACK))
            fenName = Character.toLowerCase(fenName);
    }

    @Override
    /**
     * An accessor method for the piece's symbol
     * 
     * @return      the symbol for the king in algebraic notation
     **/
    public String algebraicName() {
        return "" + Character.toUpperCase(fenName);
    }

    @Override
    /**
     * An accessor method for the piece's symbol
     * 
     * @return      the symbol for the king in FEN
     **/
    public String fenName() {
        return "" + fenName;
    }

    @Override
    /**
     * Gives every move the King could move to from a given square assuming an empty board
     *
     * @param start the square the king starts on
     * @return      array of squares containing possible moves
     **/
    public Square[] movesFrom(Square start) {
        ArrayList<Square> validSquares = new ArrayList<Square>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Square newSquare = new Square((char)(start.file + i), (char)(start.rank + j));
                if (newSquare.withinBounds() && !(i == 0 && j == 0)) {
                    validSquares.add(newSquare);
                }

            }
        }
        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
