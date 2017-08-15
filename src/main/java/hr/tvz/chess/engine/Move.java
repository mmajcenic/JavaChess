package hr.tvz.chess.engine;

import hr.tvz.chess.pieces.common.AbstractChessPiece;
import hr.tvz.chess.pieces.common.Color;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Marko on 14.8.2016..
 */
public class Move {

    private int oldPositionRow;
    private int oldPositionColumn;
    private int newPositionRow;
    private int newPositionColumn;
    private AbstractChessPiece pieceThatMoves;
    private AbstractChessPiece capture;
    private boolean isPawnPromotion;
    private boolean isEnPassant;
    private boolean isCastling;
    private AbstractChessPiece promotionPiece;
    private int value;
    private Castling castling;
    private Set<Move> childrenMoves =
            new TreeSet<>((o1, o2) -> {
                if (o1.getValue() > o2.getValue()) {
                    return -1;
                } else if (o1.getValue() < o2.getValue()) {
                    return 1;
                }
                return 0;
            });

    public Move(Castling castling, AbstractChessPiece pieceThatMoves) {
        this.castling = castling;
        this.isPawnPromotion = false;
        this.isEnPassant = false;
        this.isCastling = true;
        this.pieceThatMoves = pieceThatMoves;
    }

    public Move(int oldPositionRow, int oldPositionColumn, int newPositionRow, int newPositionColumn, AbstractChessPiece capture, AbstractChessPiece pieceThatMoves) {
        this.oldPositionRow = oldPositionRow;
        this.oldPositionColumn = oldPositionColumn;
        this.newPositionRow = newPositionRow;
        this.newPositionColumn = newPositionColumn;
        this.capture = capture;
        this.isPawnPromotion = false;
        this.pieceThatMoves = pieceThatMoves;
    }

    public Move(AbstractChessPiece capture, int oldPositionRow, int oldPositionColumn, int newPositionRow, int newPositionColumn, AbstractChessPiece pieceThatMoves) {
        this.oldPositionRow = oldPositionRow;
        this.oldPositionColumn = oldPositionColumn;
        this.newPositionRow = newPositionRow;
        this.newPositionColumn = newPositionColumn;
        this.isPawnPromotion = false;
        this.isEnPassant = true;
        int sideMoveValue = pieceThatMoves.getColor().equals(Color.WHITE) ? 1 : -1;
        this.capture = capture;
        this.pieceThatMoves = pieceThatMoves;
    }

    public Move(int oldPositionColumn, int newPositionColumn, AbstractChessPiece capture, AbstractChessPiece promotionPiece, AbstractChessPiece pieceThatMoves) {
        this.oldPositionColumn = oldPositionColumn;
        this.newPositionColumn = newPositionColumn;
        this.capture = capture;
        this.promotionPiece = promotionPiece;
        this.isPawnPromotion = true;
        this.pieceThatMoves = pieceThatMoves;
    }

    public Move() {
    }

    public int getOldPositionRow() {
        return oldPositionRow;
    }

    public int getOldPositionColumn() {
        return oldPositionColumn;
    }

    public int getNewPositionRow() {
        return newPositionRow;
    }

    public int getNewPositionColumn() {
        return newPositionColumn;
    }

    public AbstractChessPiece getCapture() {
        return capture;
    }

    public boolean isPawnPromotion() {
        return isPawnPromotion;
    }

    public AbstractChessPiece getPromotionPiece() {
        return promotionPiece;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public void setEnPassant(boolean enPassant) {
        isEnPassant = enPassant;
    }

    public boolean isCastling() {
        return isCastling;
    }

    public void setCastling(boolean castling) {
        isCastling = castling;
    }

    public Castling getCastling() {
        return castling;
    }

    public void setCastling(Castling castling) {
        this.castling = castling;
    }

    public AbstractChessPiece getPieceThatMoves() {
        return pieceThatMoves;
    }

    public void setPieceThatMoves(AbstractChessPiece pieceThatMoves) {
        this.pieceThatMoves = pieceThatMoves;
    }

    public Set<Move> getChildrenMoves() {
        return childrenMoves;
    }

    @Override
    public String toString() {
        if (isPawnPromotion) {
            return String.format("%d%d%s%s", oldPositionColumn, newPositionColumn, capture, promotionPiece);
        }
        if (capture != null) {
            return String.format("%d%d%d%d_%d", oldPositionRow, oldPositionColumn, newPositionRow, newPositionColumn, value);
        }
        return "FALSE";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (oldPositionRow != move.oldPositionRow) return false;
        if (oldPositionColumn != move.oldPositionColumn) return false;
        if (newPositionRow != move.newPositionRow) return false;
        if (newPositionColumn != move.newPositionColumn) return false;
        if (isPawnPromotion != move.isPawnPromotion) return false;
        if (isEnPassant != move.isEnPassant) return false;
        if (isCastling != move.isCastling) return false;
        if (pieceThatMoves != null ? !pieceThatMoves.equals(move.pieceThatMoves) : move.pieceThatMoves != null)
            return false;
        if (capture != null ? !capture.equals(move.capture) : move.capture != null) return false;
        if (promotionPiece != null ? !promotionPiece.equals(move.promotionPiece) : move.promotionPiece != null)
            return false;
        return castling == move.castling;
    }

    @Override
    public int hashCode() {
        int result = oldPositionRow;
        result = 31 * result + oldPositionColumn;
        result = 31 * result + newPositionRow;
        result = 31 * result + newPositionColumn;
        result = 31 * result + (pieceThatMoves != null ? pieceThatMoves.hashCode() : 0);
        result = 31 * result + (capture != null ? capture.hashCode() : 0);
        result = 31 * result + (isPawnPromotion ? 1 : 0);
        result = 31 * result + (isEnPassant ? 1 : 0);
        result = 31 * result + (isCastling ? 1 : 0);
        result = 31 * result + (promotionPiece != null ? promotionPiece.hashCode() : 0);
        result = 31 * result + (castling != null ? castling.hashCode() : 0);
        return result;
    }
}
