/**
 * Implementation of the Piece class for a Knight
 * 
 * @author Jonathan Buchanan
 *
 **/
import java.util.ArrayList;

public class Pawn extends Piece {
    /**
     * Create a Pawn object
     * 
     * @param c     determines the side that the pawn belongs to
     **/
    public Pawn(Color c) {
        super(c);

        fenName = 'P';
        if (c.equals(Color.BLACK))
            fenName = Character.toLowerCase(fenName);
    }

    @Override
    /**
     * An accessor for the Pawn's symbol in algebraic notation
     * 
     * @return      symbol for the pawn's piece
     **/
    public String algebraicName() {
        return "";
    }

    @Override
    /**
     * An accessor for the Pawn's symbol in FEN
     * 
     * @return      symbol for the pawn's piece
     **/
    public String fenName() {
        return "" + fenName;
    }

    @Override
    /**
     * Gives every square the Pawn could move to from a given square assuming an empty board
     * 
     * @param start the square the pawn starts on
     * @return      array of squares containing possible moves
     **/
    public Square[] movesFrom(Square start) {
        ArrayList<Square> validSquares = new ArrayList<Square>();
        Square frontSquare, twoFrontSquare;
        if (color.equals(Color.BLACK)) {
            frontSquare = new Square(start.file, (char)(start.rank - 1));
            twoFrontSquare = new Square(start.file, (char)(start.rank - 2 ));
            if (frontSquare.withinBounds()) {
                validSquares.add(frontSquare);

                if (twoFrontSquare.withinBounds() && start.rank == '7') {
                    validSquares.add(twoFrontSquare);
                }
            }

        } else {
            frontSquare = new Square(start.file, (char)(start.rank + 1));
            twoFrontSquare = new Square(start.file, (char)(start.rank + 2 ));
            if (frontSquare.withinBounds()) {
                validSquares.add(frontSquare);

                if (twoFrontSquare.withinBounds() && start.rank == '2') {
                    validSquares.add(twoFrontSquare);
                }
            }
        }
        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
