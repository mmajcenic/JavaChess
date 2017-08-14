package hr.tvz.chess.engine;

/**
 * Created by marko on 14/02/2017.
 */
public enum Castling {

    WHITE_KING_SIDE(7, 4, 7, 7, 7, 6, 7, 5),
    WHITE_QUEEN_SIDE(7, 4, 7, 0, 7, 2, 7, 3),
    BLACK_KING_SIDE(0, 4, 0, 7, 0, 6, 0, 5),
    BLACK_QUEEN_SIDE(0, 4, 0, 0, 0, 2, 0, 3);

    private int kingStartingRow;
    private int kingStartingColumn;
    private int rookStartingRow;
    private int rookStartingColumn;
    private int kingEndingRow;
    private int kingEndingColumn;
    private int rookEndingRow;
    private int rookEndingColumn;

    Castling(int kingStartingRow,
             int kingStartingColumn,
             int rookStartingRow,
             int rookStartingColumn,
             int kingEndingRow,
             int kingEndingColumn,
             int rookEndingRow,
             int rookEndingColumn) {
        this.kingStartingRow = kingStartingRow;
        this.kingStartingColumn = kingStartingColumn;
        this.rookStartingRow = rookStartingRow;
        this.rookStartingColumn = rookStartingColumn;
        this.kingEndingRow = kingEndingRow;
        this.kingEndingColumn = kingEndingColumn;
        this.rookEndingRow = rookEndingRow;
        this.rookEndingColumn = rookEndingColumn;
    }

    public int getKingStartingRow() {
        return kingStartingRow;
    }

    public int getKingStartingColumn() {
        return kingStartingColumn;
    }

    public int getRookStartingRow() {
        return rookStartingRow;
    }

    public int getRookStartingColumn() {
        return rookStartingColumn;
    }

    public int getKingEndingRow() {
        return kingEndingRow;
    }

    public int getKingEndingColumn() {
        return kingEndingColumn;
    }

    public int getRookEndingRow() {
        return rookEndingRow;
    }

    public int getRookEndingColumn() {
        return rookEndingColumn;
    }

}
