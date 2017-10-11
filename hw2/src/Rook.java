import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece {
    private char fenName = 'R';
    public Rook(Color c) {
        super(c);
        if (c.equals(Color.BLACK))
            fenName = Character.toLowerCase(fenName);
    }

    @Override
    public String algebraicName() {
        return "" + Character.toUpperCase(fenName);
    }

    @Override
    public String fenName() {
        return "" + fenName;
    }

    @Override
    public Square[] movesFrom(Square start) { 
        ArrayList<Square> validSquares = new ArrayList<Square>();
        validSquares.addAll(Arrays.asList(rayCaster(start, 1, 0)));
        validSquares.addAll(Arrays.asList(rayCaster(start, -1, 0)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 0, 1)));
        validSquares.addAll(Arrays.asList(rayCaster(start, 0, -1)));

        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
