/**
 * An exception to be thrown when a square is attempted to be constructed
 * out of bounds
 * 
 * It is a runtime exception because the only time a bad square could be
 * consstructed would be in the code (the user would never create a new
 * square)  Therefore if the program is trying to create invalid squares 
 * something else is wrong and it is probably unrecoverable.
 * 
 * @author Jonathan Buchanan
 */
class InvalidSquareException extends RuntimeException {
    public InvalidSquareException(String msg) {
        super(msg);
    }
}
