public class Tester {
    public static void main(String[] args) {
		Piece knight = new Knight(Color.BLACK);
		assert knight.algebraicName().equals("N");
		assert knight.fenName().equals("n");
        System.out.println(new Square("f6"));
		Square[] attackedSquares = knight.movesFrom(new Square("f6"));
        for (Square s : attackedSquares) {
            System.out.println(s);
        }
		// test that attackedSquares contains e8, g8, etc.
		Square a1 = new Square("a1");
		Square otherA1 = new Square('a', '1');
		Square h8 = new Square("h8");
		assert a1.equals(otherA1);
		assert !a1.equals(h8);
    }
}
