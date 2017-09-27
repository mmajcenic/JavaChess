package hr.tvz.chess.pieces.common;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.gui.ImageFile;
import hr.tvz.chess.pieces.impl.*;

import java.util.List;

/**
 * Created by Marko on 13.8.2016..
 */
public abstract class AbstractChessPiece {

    private Color color;

    private PieceType type;

    private ImageFile image;

    private int position;

    public AbstractChessPiece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
        setImage();
    }

    public AbstractChessPiece(PieceType type) {
        this.type = type;
    }

    public abstract List<Move> getPossibleMoves(int i);

    public Color getColor() {
        return color;
    }

    public boolean isEmpty(){
        return getType() == PieceType.EMPTY;
    }

    public boolean isOppositePlayerPieceType(PieceType type) {
        return !isEmpty() && (Chessboard.getCurrentPlayer().getColor() != getColor() && type == getType());
    }

    public boolean isCurrentPlayerPieceType(PieceType type) {
        return !isEmpty() && (Chessboard.getCurrentPlayer().getColor() == getColor() && type == getType());
    }

    private void setImage() {
        if (color.equals(Color.BLACK) && getClass() == King.class) {
            image = ImageFile.BLACK_KING;
        } else if (color.equals(Color.WHITE) && getClass() == King.class) {
            image = ImageFile.WHITE_KING;
        } else if (color.equals(Color.BLACK) && getClass() == Queen.class) {
            image = ImageFile.BLACK_QUEEN;
        } else if (color.equals(Color.WHITE) && getClass() == Queen.class) {
            image = ImageFile.WHITE_QUEEN;
        } else if (color.equals(Color.BLACK) && getClass() == Knight.class) {
            image = ImageFile.BLACK_KNIGHT;
        } else if (color.equals(Color.WHITE) && getClass() == Knight.class) {
            image = ImageFile.WHITE_KNIGHT;
        } else if (color.equals(Color.BLACK) && getClass() == Rook.class) {
            image = ImageFile.BLACK_ROOK;
        } else if (color.equals(Color.WHITE) && getClass() == Rook.class) {
            image = ImageFile.WHITE_ROOK;
        } else if (color.equals(Color.BLACK) && getClass() == Bishop.class) {
            image = ImageFile.BLACK_BISHOP;
        } else if (color.equals(Color.WHITE) && getClass() == Bishop.class) {
            image = ImageFile.WHITE_BISHOP;
        } else if (color.equals(Color.BLACK) && getClass() == Pawn.class) {
            image = ImageFile.BLACK_PAWN;
        } else if (color.equals(Color.WHITE) && getClass() == Pawn.class) {
            image = ImageFile.WHITE_PAWN;
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ImageFile getImage() {
        return image;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setImage(ImageFile image) {
        this.image = image;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getType(), getColor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractChessPiece that = (AbstractChessPiece) o;

        if (color != that.color) return false;
        if (type != that.type) return false;
        return image == that.image;

    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + type.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
