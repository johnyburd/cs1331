/**
 * class that represents a square on a chessboard
 * @author Jonathan Buchanan
 * @version 1
 */
public class Square {
    private char rank;
    private char file;
    private String name;

    /**
     * Contructor for a square
     *
     * @param file file of the square
     * @param rank rank of the square
     */
    public Square(char file, char rank) {
        this("" + file + rank);
    }

    /**
     * constructor for a square
     *
     * @param name rank and file of the square
     */
    public Square(String name) {
        this.name = name;
        if (name != null && name.length() == 2) {
            file = name.charAt(0);
            rank = name.charAt(1);
            if (file >= 'a' && file <= 'h' && rank >= '1' && rank <= '8') {
                this.name = name;
            } else {
                throw new InvalidSquareException(name);
            }
        } else {
            throw new InvalidSquareException(name);
        }
    }

    /**
     * accessor method for a the file
     *
     * @return file of the square
     */
    public char getFile() {
        return file;
    }

    /**
     * accessor for the rank of the square
     *
     * @return rank of the square
     */
    public char getRank() {
        return rank;
    }

    /**
     * {@inheritDoc}
     *
     * @see Object#toString()
     */
    public String toString() {
        return name;
    }

    /**
     * {@inheritDoc}
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (o instanceof Square) {
            Square that = (Square) o;
            return that.rank == rank && that.file == file;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see Object#hashCode()
     */
    public int hashCode() {
        return 1;
    }
}
