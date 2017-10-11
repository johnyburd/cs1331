/**
 * Abstract class that gives structure to each of the types of chess pieces
 * 
 * @author Jonathan Buchanan
 *
 **/
import java.util.ArrayList;

public abstract class Piece {
    protected Color color;
    protected char fenName;
    
    /**
     * Create a Piece object, store piece's color
     * 
     * @param c     determines  the side that the knight belongs to
     **/
    public Piece(Color c) {
        color = c;
    }

    /**
     * Accessor method for the color of the piece
     * 
     * @return      the color of the piece
     **/
    public Color getColor() {
        return color;
    }

    /**
     * Finds every square between a square and the edge of the board
     * in a given direction
     * 
     * @param start starting square
     * @param x     x component of direction vector
     * @param y     y component of direction vector
     * @return      array of squares the ray passes through
     **/
    protected static Square[] rayCaster(Square start, int x, int y) {
        ArrayList<Square> raySquares = new ArrayList<Square>();
        int i = 0;
        int j = 0;
        while (true) {
            i += x;
            j += y;
            Square potSquare = new Square((char)(start.file + i), (char)(start.rank + j));
            if (potSquare.withinBounds()) {
                raySquares.add(potSquare);
            } else {
                break;
            }
        }

        return raySquares.toArray(new Square[raySquares.size()]);
    }

    public abstract String algebraicName();
    public abstract String fenName();
    public abstract Square[] movesFrom(Square square);
}
