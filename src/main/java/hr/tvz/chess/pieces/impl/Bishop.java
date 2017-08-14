package hr.tvz.chess.pieces.impl;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.pieces.common.AbstractChessPiece;
import hr.tvz.chess.pieces.common.Color;
import hr.tvz.chess.pieces.common.PieceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marko on 14.8.2016..
 */
public class Bishop extends AbstractChessPiece {

    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public List<Move> getPossibleMoves(int i) {

        List<Move> moves = new ArrayList<>();

        int r = i / 8;
        int c = i % 8;
        int temp = 1;

        AbstractChessPiece oldPiece;
        AbstractChessPiece startingPosition = Chessboard.getPiece(r, c);

        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                while (Chessboard.isEmptyPiece(r + temp * j, c + temp * k)) {
                    oldPiece = Chessboard.getPiece(r + temp * j, c + temp * k);
                    Chessboard.setPiece(r + temp * j, c + temp * k, Chessboard.getPiece(r, c));
                    Chessboard.setPiece(r, c, new EmptySquare());
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(r, c, r + temp * j, c + temp * k, oldPiece, this);
                        moves.add(move);

                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r + temp * j, c + temp * k, oldPiece);
                    temp++;
                }
                if (Chessboard.isOppositePlayerColor(r + temp * j, c + temp * k)) {
                    oldPiece = Chessboard.getPiece(r + temp * j, c + temp * k);
                    Chessboard.setPiece(r + temp * j, c + temp * k, Chessboard.getPiece(r, c));
                    Chessboard.setPiece(r, c, new EmptySquare());

                    if (Chessboard.kingSafe()) {
                        Move move = new Move(r, c, r + temp * j, c + temp * k, oldPiece, this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r + temp * j, c + temp * k, oldPiece);
                }
                temp = 1;
            }
        }
        return moves;
    }


}
