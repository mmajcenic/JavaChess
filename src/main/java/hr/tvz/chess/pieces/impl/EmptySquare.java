package hr.tvz.chess.pieces.impl;

import hr.tvz.chess.engine.Move;
import hr.tvz.chess.pieces.common.AbstractChessPiece;
import hr.tvz.chess.pieces.common.PieceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marko on 14.8.2016..
 */
public class EmptySquare extends AbstractChessPiece {

    public EmptySquare() {
        super(PieceType.EMPTY);
    }

    @Override
    public List<Move> getPossibleMoves(int i) {
        return new ArrayList<>();
    }
}
