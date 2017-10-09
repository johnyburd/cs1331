import java.util.ArrayList;

public class Knight extends Piece {
    private char fenName = 'N';
    Knight(Color c) {
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
            if (s.file >= 'a' && s.file <= 'h' && s.rank >= 1 && s.rank <=8) {
                validSquares.add(s);
            }
        }

        return validSquares.toArray(new Square[validSquares.size()]);
    }
}
