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
public class Knight extends AbstractChessPiece {
    public Knight(Color color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public List<Move> getPossibleMoves(int i) {
        List<Move> moves = new ArrayList<>();

        int r = i / 8;
        int c = i % 8;

        AbstractChessPiece oldPiece;
        AbstractChessPiece startingPosition = Chessboard.getPiece(r, c);

        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {

                if (Chessboard.isEmptyPiece(r + j, c + k * 2) || Chessboard.isOppositePlayerColor(r + j, c + k * 2)) {
                    oldPiece = Chessboard.getPiece(r + j, c + k * 2);
                    Chessboard.setPiece(r + j, c + k * 2, Chessboard.getPiece(r, c));
                    Chessboard.setPiece(r, c, new EmptySquare());
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(r, c, r + j, c + k * 2, oldPiece, this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r + j, c + k * 2, oldPiece);
                }

                if (Chessboard.isEmptyPiece(r + j * 2, c + k) || Chessboard.isOppositePlayerColor(r + j * 2, c + k)) {
                    oldPiece = Chessboard.getPiece(r + j * 2, c + k);
                    Chessboard.setPiece(r + j * 2, c + k, Chessboard.getPiece(r, c));
                    Chessboard.setPiece(r, c, new EmptySquare());
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(r, c, r + j * 2, c + k, oldPiece, this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r + j * 2, c + k, oldPiece);
                }

            }
        }
        return moves;
    }
}
