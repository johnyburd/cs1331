/**
 * Exception for a square out of bounds
 *
 * @author Jonathan Buchanan
 * @version 1
 */
public class InvalidSquareException extends RuntimeException {

    /**
     * {@inheritDoc}
     *
     * @see RuntimeException#InvalidSquareException(String)
     */
    public InvalidSquareException(String square) {
        super(square);
    }
}
