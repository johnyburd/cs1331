public class Square {
    public char file, rank;

    public Square(String s) {
        file = s.charAt(0);
        rank = s.charAt(1);
    }

    public Square(char f, char r) {
        this(f + "" + r);
    }

    public boolean withinBounds() {
        return file >= 'a' && file <= 'h' && rank >= '1' && rank <='8';
    }

    public String toString() {
        return file + "" + rank;
    }

    public boolean equals(Square s) {
        return s.rank == this.rank && s.file == this.file;
    }
}
