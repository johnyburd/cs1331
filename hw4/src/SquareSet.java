import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * A set datastructure that stores and manages Squares
 *
 * @author Jonathan Buchanan
 **/

public class SquareSet implements Set<Square> {
    private Square[] squares;

    /**
     * Create a SquareSet with no elements
     **/
    public SquareSet() {
        squares = new Square[0];
    }

    /**
     * Create a SquareSet with the same Squares as another Collection
     *
     * @param c the collection to copy existing Squares from
     **/
    public SquareSet(Collection<? extends Square> c) {
        squares = new Square[0];
        this.addAll(c);
    }

    /**
     * add a Square to the set
     * @param s the Square object to be added
     * @return  true if the set was modified
     **/
    @Override
    public boolean add(Square s) {
        if (s == null) {
            throw new NullPointerException();
        }
        if (this.contains(s)) {
            return false;
        }
        if (squares.length > 0) {
            Square[] temp = new Square[squares.length + 1];
            for (int i = 0; i < squares.length; i++) {
                temp[i] = squares[i];
            }
            temp[squares.length] = s;
            squares = temp;
            return true;
        }
        squares = new Square[] {s};

        return true;
    }

    /**
     * Add all the Squares from another Collection
     * @param c the Collection to copy Squares from
     * @return true if the set was modified.
     **/
    @Override
    public boolean addAll(Collection<? extends Square> c) {
        // this could be more efficient as the array is being reallocted as
        // many times as there are new squares.
        if (c == null) {
            throw new NullPointerException();
        }
        for (Square s : c) {
            if (s == null || contains(s)) {
                return false;
            }
        }
        for (Square s : c) {
            add(s);
        }
        return true;

    }

    /**
     * Remove all elements from the set
     **/
    @Override
    public void clear() {
        squares = new Square[0];
    }

    /**
     * Check if the set contains a specific object
     * @param o the object to search for
     * @return true if the object is in the set
     **/
    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        try {
            Square s = (Square) o;
            for (Square sq : squares) {
                if (s.equals(sq)) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new ClassCastException();
        }
        return false;
    }

    /**
     * Check if the set contains all the objects from another Collection
     * @param c the collection to check the object in
     * @return true if every element from c is in the set.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if two SquareSets are equivalent in content
     * @param ss other SquareSet to compare to
     * @return true if ss has the same elements as this set.
     **/
    @Override
    public boolean equals(Object o) {
        SquareSet ss;
        try {
            ss = (SquareSet) o;
        } catch (Exception e) {
            throw new ClassCastException();
        }
        if (ss == null || ss.size() != this.size()) {
            return false;
        }
        for (Square s : this) {
            if (!ss.contains(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generate a hashCode for the set
     * @return the size of the set
     **/
    @Override
    public int hashCode() {
        return squares.length;
    }

    /**
     * Check if the set is empty
     * @return true if the set is empty
     **/
    @Override
    public boolean isEmpty() {
        return squares.length == 0;
    }

    /**
     * Get an iterator to iterate over the elements of the set
     * @return an Iterator<Square> object that can iterate through the elements
     */
    @Override
    public Iterator<Square> iterator() {
        return new SquareIterator(squares);
    }

    /**
     * Remove an object from the set
     * @return true if the object was removed
     **/
    @Override
    public boolean remove(Object o) {
        Square s;
        Square[] temp;
        if (o == null) {
            throw new NullPointerException();
        }

        try {
            s = (Square) o;
        } catch (Exception e) {
            throw new ClassCastException();
        }

        for (int i = 0; i < squares.length; i++) {
            if (squares[i].equals(s)) {
                temp = new Square[squares.length - 1];
                for (int j = 0; j < squares.length - 1; j++) {
                    if (j < i) {
                        temp[j] = squares[j];
                    } else {
                        temp[j] = squares[j + 1];
                    }
                }
                squares = temp;
                return true;
            }
        }
        return false;
    }

    /**
     * Not implemented
     * @param c na
     * @return na
     **/
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not implemented
     * @param c na
     * @return na
     **/
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * get the length of the set
     * return int the length of the set
     **/
    @Override
    public int size() {
        return squares.length;
    }

    /**
     * Convert the set to an array of Squares
     * @return Square[] containing the elements of the set
     **/
    @Override
    public Square[] toArray() {
        return squares;
    }

    /**
     * Convert the set on an array of generic type
     * @param a an array of the type that the set should be returned in
     * @return an array of the same type as a, containing all elements
     * in the set
     **/
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        T[] arr = a;
        if (a == null) {
            throw new NullPointerException();
        }
        if (squares.length == 0) {
            return a;
        }
        try {
            T[] temp = (T[]) (squares);
        } catch (Exception e) {
            throw new ArrayStoreException();
        }
        if (a.length < squares.length) {
            arr = (T[]) new Square[squares.length];
        }
        for (int i = 0; i < squares.length; i++) {
            arr[i] = (T) squares[i];
        }
        if (a.length > squares.length) {
            arr[squares.length] = null;
        }

        return arr;
    }

    private class SquareIterator implements Iterator<Square> {
        private Square[] squareArray;
        private int cursor;

        public SquareIterator(Square[] sA) {
            squareArray = sA;
            cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < squareArray.length;
        }

        @Override
        public Square next() {
            Square sq = squareArray[cursor];
            cursor++;
            return sq;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
