package hr.tvz.chess.pieces.impl;

import hr.tvz.chess.engine.Castling;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.pieces.common.AbstractChessPiece;
import hr.tvz.chess.pieces.common.Color;
import hr.tvz.chess.pieces.common.PieceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marko on 13.8.2016..
 */
public class King extends AbstractChessPiece {

    public King(Color color) {
        super(color, PieceType.KING);
    }

    @Override
    public List<Move> getPossibleMoves(int i) {

        List<Move> moves = new ArrayList<>();

        int r = i / 8;
        int c = i % 8;

        AbstractChessPiece oldPiece;
        AbstractChessPiece startingPosition = Chessboard.getPiece(r, c);

        for (int j = 0; j < 9; j++) {
            if (j != 4) {
                if (Chessboard.isEmptyPiece(r - 1 + j / 3, c - 1 + j % 3) ||
                        Chessboard.isOppositePlayerColor(r - 1 + j / 3, c - 1 + j % 3)) {
                    oldPiece = Chessboard.getPiece(r - 1 + j / 3, c - 1 + j % 3);
                    Chessboard.setPiece(r - 1 + j / 3, c - 1 + j % 3, Chessboard.getPiece(r, c));
                    Chessboard.setPiece(r, c, new EmptySquare());
                    int kingTemp = Chessboard.getCurrentPlayer().getKingPosition();
                    Chessboard.getCurrentPlayer().setKingPosition(i + (j / 3) * 8 + j % 3 - 9);
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(r, c, r - 1 + j / 3, c - 1 + j % 3, oldPiece, this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r - 1 + j / 3, c - 1 + j % 3, oldPiece);
                    Chessboard.getCurrentPlayer().setKingPosition(kingTemp);
                }
            }
        }

        if (Chessboard.kingSafe()) {
            if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                if (Chessboard.isWhiteCastlingKingSide()) {
                    if (r == 7 && Chessboard.isEmptyPiece(r, c + 1)
                            && Chessboard.isEmptyPiece(r, c + 2)
                            && Chessboard.isInChessboard(r, c + 3)
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c + 3))
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c))) {
                        boolean passesThroughAtackedSquare = checkIfPassesThroughAttackedSquare(r, c,
                                startingPosition, true);
                        if (!passesThroughAtackedSquare) {
                            Move move = new Move(Castling.WHITE_KING_SIDE, this);
                            moves.add(move);
                        }
                    }
                }
                if (Chessboard.isWhiteCastlingQueenSide()) {
                    if (r == 7 && Chessboard.isEmptyPiece(r, c - 1)
                            && Chessboard.isEmptyPiece(r, c - 2)
                            && Chessboard.isEmptyPiece(r, c - 3)
                            && Chessboard.isInChessboard(r, c - 4)
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c - 4))
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c))) {
                        boolean passesThroughAtackedSquare = checkIfPassesThroughAttackedSquare(r, c,
                                startingPosition, false);
                        if (!passesThroughAtackedSquare) {
                            Move move = new Move(Castling.WHITE_QUEEN_SIDE, this);
                            moves.add(move);
                        }
                    }
                }
            } else {
                if (Chessboard.isBlackCastlingKingSide()) {
                    if (r == 0 && Chessboard.isEmptyPiece(r, c + 1)
                            && Chessboard.isEmptyPiece(r, c + 2)
                            && Chessboard.isInChessboard(r, c + 3)
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c + 3))
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c))) {
                        boolean passesThroughAtackedSquare = checkIfPassesThroughAttackedSquare(r, c,
                                startingPosition, true);
                        if (!passesThroughAtackedSquare) {
                            Move move = new Move(Castling.BLACK_KING_SIDE, this);
                            moves.add(move);
                        }
                    }
                }
                if (Chessboard.isBlackCastlingQueenSide()) {
                    if (r == 0 && Chessboard.isEmptyPiece(r, c - 1)
                            && Chessboard.isEmptyPiece(r, c - 2)
                            && Chessboard.isEmptyPiece(r, c - 3)
                            && Chessboard.isInChessboard(r, c - 4)
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c - 4))
                            && !Chessboard.hasMoved(Chessboard.getPiece(r, c))) {
                        boolean passesThroughAtackedSquare = checkIfPassesThroughAttackedSquare(r, c,
                                startingPosition, false);
                        if (!passesThroughAtackedSquare) {
                            Move move = new Move(Castling.BLACK_QUEEN_SIDE, this);
                            moves.add(move);
                        }
                    }
                }

            }
        }

        return moves;
    }

    private boolean checkIfPassesThroughAttackedSquare(int r, int c, AbstractChessPiece startingPosition,
                                                       boolean isKingSide) {
        boolean passesThroughAtackedSquare = false;
        int numberOfField = isKingSide ? 2 : 3;
        for (int k = 1; k <= numberOfField; k++) {
            int m;
            if (!isKingSide) {
                m = 0 - k;
            } else {
                m = k;
            }
            Chessboard.setPiece(r, c + m, Chessboard.getPiece(r, c));
            Chessboard.setPiece(r, c, new EmptySquare());
            int kingTemp = Chessboard.getCurrentPlayer().getKingPosition();
            Chessboard.getCurrentPlayer().setKingPosition(kingTemp + m);
            if (!Chessboard.kingSafe()) {
                passesThroughAtackedSquare = true;
            }
            Chessboard.setPiece(r, c, startingPosition);
            Chessboard.setPiece(r, c + m, new EmptySquare());
            Chessboard.getCurrentPlayer().setKingPosition(kingTemp);
        }
        return passesThroughAtackedSquare;
    }

}
