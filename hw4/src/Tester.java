import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Tester {
    public static void main(String[] args) {
        List<Square> list = new ArrayList<>();
        list.add(new Square("a1"));
        list.add(new Square("a3"));
        list.add(new Square("a2"));
        Object[] a = new Object[1];
        SquareSet set = new SquareSet();
        SquareSet set2 = new SquareSet(list);
        System.out.println("Empty? " + set.isEmpty());
        set.addAll(list);
        System.out.println("Empty? " + set.isEmpty());

        System.out.println(Arrays.toString(set.toArray()));

        Square s = new Square("a8");
        //System.out.println("Contains " + s + "? " + set.contains(s));
        //set.clear();
        //set.remove(new Square("a3"));
        //set.removeAll(list);
        //Exception e = (Exception) s;
        
        //System.out.println(Arrays.toString(set.toArray(new Integer[2])));
        System.out.println(set.size());
    }
}
