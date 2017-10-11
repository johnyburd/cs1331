/**
 * Implementation of the Piece class for a Knight
 * 
 * @author Jonathan Buchanan
 *
 **/
import java.util.ArrayList;

public class Knight extends Piece {
    /**
     * Create a Knight object
     * 
     * @param c     determines  the side that the knight belongs to
     **/
    Knight(Color c) {
        super(c);

        fenName = 'N';
        if (c.equals(Color.BLACK)) {
            fenName = Character.toLowerCase(fenName);
        }
    }
    
    @Override
    /**
     * An accessor for the Knight's symbol in algebraic notation
     * 
     * @return      symbol for the knight's piece
     **/
    public String algebraicName() {
        return "" + Character.toUpperCase(fenName);
    }

    @Override
    /**
     * An accessor methof for the Knight's symbol in FEN
     * 
     * @return      symbol for the knight's piece
     **/
    public String fenName() {
        return "" + fenName;
    }

    @Override
    /**
     * Gives every square the Knight could move to from a given square assuming an empty board
     * 
     * @param start the square the knight starts on
     * @return      array of squares containing possible moves
     **/
    public Square[] movesFrom(Square start) {
        ArrayList<Square> validSquares = new ArrayList<Square>();
        Square[] potSquares = {
            new Square((char)(start.file + 2), (char)(start.rank + 1)),
            new Square((char)(start.file + 2), (char)(start.rank - 1)),
            new Square((char)(start.file - 2), (char)(start.rank + 1)),
            new Square((char)(start.file - 2), (char)(start.rank - 1)),
            new Square((char)(start.file + 1), (char)(start.rank + 2)),
            new Square((char)(start.file + 1), (char)(start.rank - 2)),
            new Square((char)(start.file - 1), (char)(start.rank + 2)),
            new Square((char)(start.file - 1), (char)(start.rank - 2))
        };

        for (Square s : potSquares) {
            //System.out.println(s);
            if (s.withinBounds()) {
                validSquares.add(s);
            }
        }

        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
