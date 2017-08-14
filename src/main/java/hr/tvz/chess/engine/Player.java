package hr.tvz.chess.engine;

import hr.tvz.chess.pieces.common.Color;

/**
 * Created by marko on 10/31/2016.
 */
public class Player {

    private Color color;

    private int kingPosition;

    public Player(Color color, int kingPosition) {
        this.color = color;
        this.kingPosition = kingPosition;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getKingPosition() {
        return kingPosition;
    }

    public void setKingPosition(int kingPosition) {
        this.kingPosition = kingPosition;
    }
}
