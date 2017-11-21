public class Ply {
    private Piece piece;
    private Square from;
    private Square to;
    private Optional<String> comment;

    public Ply(Piece p, Square f, Square t, c) {
        piece = p;
        from = f;
        to = t;
        comment = c;
    }
    public Ply(Piece p, Square f, Square t) {
        piece = p;
        from = f;
        to = t;
        comment = null;
    }
    public Piece getPiece() {
        return piece;
    }
    public Square getTo(){
        return to;
    }
    public Square getFrom(){
        return from;
    }
    public Optional<String> getComment() {
        return comment;
    }


}
