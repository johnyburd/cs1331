public class Printer {

    public int garbage;

    public Printer(int g) {
        garbage = g;
        System.out.println(g);
    }

    public int garbageCollect() {
        return garbage;
    }
}
