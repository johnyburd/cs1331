import java.util.ArrayList;

public abstract class Piece {
    protected Color color;
    
    public Piece(Color c) {
        color = c;
    }

    public Color getColor() {
        return color;
    }

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
