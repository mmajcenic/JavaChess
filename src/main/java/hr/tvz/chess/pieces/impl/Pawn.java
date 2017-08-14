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
public class Pawn extends AbstractChessPiece {

    public Pawn(Color color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public List<Move> getPossibleMoves(int i) {
        List<Move> moves = new ArrayList<>();

        int r = i / 8;
        int c = i % 8;

        AbstractChessPiece oldPiece;
        AbstractChessPiece startingPosition = Chessboard.getPiece(r, c);

        if (getColor().equals(Color.WHITE)) {

            for (int j = -1; j <= 1; j += 2) {
                //napad
                if (Chessboard.isBlackColor(r - 1, c + j) && i >= 16) {
                    oldPiece = Chessboard.getPiece(r - 1, c + j);
                    Chessboard.setPiece(r - 1, c + j, Chessboard.getPiece(r, c));
                    Chessboard.setPiece(r, c, new EmptySquare());
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(r, c, r - 1, c + j, oldPiece, this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r - 1, c + j, oldPiece);
                }

                //promocija i napad
                if (Chessboard.isBlackColor(r - 1, c + j) && i < 16) {
                    AbstractChessPiece[] viablePromotions = {new Queen(Chessboard.getCurrentPlayer().getColor()),
                            new Rook(Chessboard.getCurrentPlayer().getColor()),
                            new Bishop(Chessboard.getCurrentPlayer().getColor()),
                            new Knight(Chessboard.getCurrentPlayer().getColor())};
                    for (int k = 0; k < 4; k++) {
                        oldPiece = Chessboard.getPiece(r - 1, c + j);
                        Chessboard.setPiece(r - 1, c + j, Chessboard.getPiece(r, c));
                        Chessboard.setPiece(r, c, new EmptySquare());
                        if (Chessboard.kingSafe()) {
                            Move move = new Move(c, c + j, oldPiece, viablePromotions[k], this);
                            moves.add(move);
                        }
                        Chessboard.setPiece(r, c, startingPosition);
                        Chessboard.setPiece(r - 1, c + j, oldPiece);
                    }
                }

            }
            //jedan gore
            if (Chessboard.isEmptyPiece(r - 1, c) && i >= 16) {
                oldPiece = Chessboard.getPiece(r - 1, c);
                Chessboard.setPiece(r - 1, c, Chessboard.getPiece(r, c));
                Chessboard.setPiece(r, c, new EmptySquare());
                if (Chessboard.kingSafe()) {
                    Move move = new Move(r, c, r - 1, c, oldPiece, this);
                    moves.add(move);
                }
                Chessboard.setPiece(r, c, startingPosition);
                Chessboard.setPiece(r - 1, c, oldPiece);
            }

            //promocija bez napada
            if (Chessboard.isEmptyPiece(r - 1, c) && i < 16) {
                AbstractChessPiece[] viablePromotions = {new Queen(Chessboard.getCurrentPlayer().getColor()),
                        new Rook(Chessboard.getCurrentPlayer().getColor()),
                        new Bishop(Chessboard.getCurrentPlayer().getColor()),
                        new Knight(Chessboard.getCurrentPlayer().getColor())};
                for (int k = 0; k < 4; k++) {
                    oldPiece = Chessboard.getPiece(r - 1, c);
                    Chessboard.setPiece(r - 1, c, viablePromotions[k]);
                    Chessboard.setPiece(r, c, new EmptySquare());
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(c, c, oldPiece, viablePromotions[k], this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r - 1, c, oldPiece);
                }
            }

            //dva gore
            if (Chessboard.isEmptyPiece(r - 1, c)
                    && Chessboard.isEmptyPiece(r - 2, c) && i >= 48) {
                oldPiece = Chessboard.getPiece(r - 2, c);
                Chessboard.setPiece(r - 2, c, Chessboard.getPiece(r, c));
                Chessboard.setPiece(r, c, new EmptySquare());
                if (Chessboard.kingSafe()) {
                    Move move = new Move(r, c, r - 2, c, oldPiece, this);
                    moves.add(move);
                }
                Chessboard.setPiece(r, c, startingPosition);
                Chessboard.setPiece(r - 2, c, oldPiece);
            }

        } else {
            for (int j = -1; j <= 1; j += 2) {
                //napad
                if (Chessboard.isWhiteColor(r + 1, c + j) && i <= 48) {
                    oldPiece = Chessboard.getPiece(r + 1, c + j);
                    Chessboard.setPiece(r + 1, c + j, Chessboard.getPiece(r, c));
                    Chessboard.setPiece(r, c, new EmptySquare());
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(r, c, r + 1, c + j, oldPiece, this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r + 1, c + j, oldPiece);
                }

                //promocija i napad
                if (Chessboard.isWhiteColor(r + 1, c + j) && i > 48) {
                    AbstractChessPiece[] viablePromotions = {new Queen(Chessboard.getCurrentPlayer().getColor()),
                            new Rook(Chessboard.getCurrentPlayer().getColor()),
                            new Bishop(Chessboard.getCurrentPlayer().getColor()),
                            new Knight(Chessboard.getCurrentPlayer().getColor())};
                    for (int k = 0; k < 4; k++) {
                        oldPiece = Chessboard.getPiece(r + 1, c + j);
                        Chessboard.setPiece(r + 1, c + j, Chessboard.getPiece(r, c));
                        Chessboard.setPiece(r, c, new EmptySquare());
                        if (Chessboard.kingSafe()) {
                            Move move = new Move(c, c + j, oldPiece, viablePromotions[k], this);
                            moves.add(move);
                        }
                        Chessboard.setPiece(r, c, startingPosition);
                        Chessboard.setPiece(r + 1, c + j, oldPiece);
                    }
                }

            }
            //jedan gore
            if (Chessboard.isEmptyPiece(r + 1, c) && i <= 48) {
                oldPiece = Chessboard.getPiece(r + 1, c);
                Chessboard.setPiece(r + 1, c, Chessboard.getPiece(r, c));
                Chessboard.setPiece(r, c, new EmptySquare());
                if (Chessboard.kingSafe()) {
                    Move move = new Move(r, c, r + 1, c, oldPiece, this);
                    moves.add(move);
                }
                Chessboard.setPiece(r, c, startingPosition);
                Chessboard.setPiece(r + 1, c, oldPiece);
            }

            //promocija bez napada
            if (Chessboard.isEmptyPiece(r + 1, c) && i > 48) {
                AbstractChessPiece[] viablePromotions = {new Queen(Chessboard.getCurrentPlayer().getColor()),
                        new Rook(Chessboard.getCurrentPlayer().getColor()),
                        new Bishop(Chessboard.getCurrentPlayer().getColor()),
                        new Knight(Chessboard.getCurrentPlayer().getColor())};
                for (int k = 0; k < 4; k++) {
                    oldPiece = Chessboard.getPiece(r + 1, c);
                    Chessboard.setPiece(r + 1, c, viablePromotions[k]);
                    Chessboard.setPiece(r, c, new EmptySquare());
                    if (Chessboard.kingSafe()) {
                        Move move = new Move(c, c, oldPiece, viablePromotions[k], this);
                        moves.add(move);
                    }
                    Chessboard.setPiece(r, c, startingPosition);
                    Chessboard.setPiece(r + 1, c, oldPiece);
                }
            }

            //dva gore
            if (Chessboard.isEmptyPiece(r + 1, c)
                    && Chessboard.isEmptyPiece(r + 2, c) && i < 16) {
                oldPiece = Chessboard.getPiece(r + 2, c);
                Chessboard.setPiece(r + 2, c, Chessboard.getPiece(r, c));
                Chessboard.setPiece(r, c, new EmptySquare());
                if (Chessboard.kingSafe()) {
                    Move move = new Move(r, c, r + 2, c, oldPiece, this);
                    moves.add(move);
                }
                Chessboard.setPiece(r, c, startingPosition);
                Chessboard.setPiece(r + 2, c, oldPiece);
            }

        }

        //en passant
        Move lastMove = Chessboard.getLastMove();
        if (lastMove != null && lastMove.getPieceThatMoves().getType().equals(PieceType.PAWN) &&
                Math.abs(lastMove.getNewPositionRow()
                        - lastMove.getOldPositionRow()) == 2 &&
                Math.abs(c - lastMove.getNewPositionColumn()) == 1 &&
                r == lastMove.getNewPositionRow()) {
            oldPiece = Chessboard.getPiece(lastMove.getNewPositionRow(), lastMove.getNewPositionColumn());
            int sideMoveValue = Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE) ? -1 : 1;
            Chessboard.setPiece(lastMove.getNewPositionRow() + sideMoveValue, lastMove.getOldPositionColumn(),
                    Chessboard.getPiece(r, c));
            Chessboard.setPiece(lastMove.getNewPositionRow(), lastMove.getNewPositionColumn(), new EmptySquare());
            Chessboard.setPiece(r, c, new EmptySquare());
            if (Chessboard.kingSafe()) {
                Move move = new Move(oldPiece, r, c, lastMove.getNewPositionRow() + sideMoveValue, lastMove.getNewPositionColumn(), this);
                moves.add(move);
            }
            Chessboard.setPiece(lastMove.getNewPositionRow(), lastMove.getNewPositionColumn(),
                    oldPiece);
            Chessboard.setPiece(r, c, startingPosition);
            Chessboard.setPiece(lastMove.getNewPositionRow() + sideMoveValue, lastMove.getOldPositionColumn(),
                    new EmptySquare());
        }

        return moves;
    }
}
