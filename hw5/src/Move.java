/**
 * class the represents a full move in chess
 * @author Jonathan Buchanan
 * @version 1
 */
public class Move {
    private Ply whitePly;
    private Ply blackPly;

    /**
     * Create a full move object
     *
     * @param w the white ply
     * @param b the black ply
     */
    public Move(Ply w, Ply b) {
        whitePly = w;
        blackPly = b;
    }

    /**
     * accessor method for the white Ply
     *
     * @return      whitePly
     */
    public Ply getWhitePly() {
        return whitePly;
    }

    /**
     * accessor method for the black Ply
     *
     * @return      blackPly
     */
    public Ply getBlackPly() {
        return blackPly;
    }
}
