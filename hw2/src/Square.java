/**
 * Represents a square on chessboard
 * 
 * @author Jonathan Buchanan
 *
 **/
public class Square {
    public char file, rank;

    /**
     * Create a Square object
     * 
     * @param s     a string which contains the coordinates of the square in
     *              the form "g4"
     **/
    public Square(String s) {
        file = s.charAt(0);
        rank = s.charAt(1);
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
        return file >= 'a' && file <= 'h' && rank >= '1' && rank <='8';
    }
    
    /**
     * give a string representation of a square
     * 
     * @return      the rank and file of a square
     **/
    public String toString() {
        return file + "" + rank;
    }
    
    /**
     * determine if two squares are equal in content
     * 
     * @param s Square object to compare to
     * 
     * @return  true or false depending on the if the square equal to the other
     **/
    public boolean equals(Square s) {
        if (this == s) {
            return true;
        }
        if (s == null) {
            return false;
        }
        return s.rank == this.rank && s.file == this.file;
    }
}
