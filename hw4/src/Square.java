/**
 * Represents a square on chessboard
 *
 * @author Jonathan Buchanan
 *
 **/
public class Square {
    private char file, rank;

    /**
     * Create a Square object
     *
     * @param s     a string which contains the coordinates of the square in
     *              the form "g4"
     **/
    public Square(String s) {
        file = s.charAt(0);
        rank = s.charAt(1);
        if (!(file >= 'a' && file <= 'h' && rank >= '1' && rank <= '8')) {
            throw new InvalidSquareException("Square out of bounds: "
                    + file + "" + rank);
        }

    }

    /**
     * Create a Square object
     *
     * @param f     the file of the square
     * @param r     the rank of the square
     **/
    public Square(char f, char r) {
        this(f + "" + r);
    }

    /**
     * check if a square is on the chess board
     *
     * @return      true or false depending on if the square is on a standard
     *              chess board
     **/
    public boolean withinBounds() {
        return file >= 'a' && file <= 'h' && rank >= '1' && rank <= '8';
    }

    /**
     * get the rank of the square
     * @return      rank of the square
     **/
    public char getRank() {
        return rank;
    }

    /**
     * get the file of the square
     * @return      file of the square
     */
    public char getFile() {
        return file;
    }

    /**
     * give a string representation of a square
     *
     * @return      the rank and file of a square
     **/
    public String toString() {
        return file + "" + rank;
    }

    public int hashCode() {
        return Integer.parseInt(Character.valueOf(file) + "" + rank);
    }

    /**
     * determine if two squares are equal in content
     *
     * @param s Square object to compare to
     *
     * @return  true or false depending on the if the square equal to the other
     **/
    @Override
    public boolean equals(Object o) {
        Square s;
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        try {
            s = (Square) o;
        } catch (Exception e) {
            throw new ClassCastException();
        }
        return s.rank == this.rank && s.file == this.file;
    }
}
