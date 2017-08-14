package hr.tvz.chess.engine;

import hr.tvz.chess.pieces.common.AbstractChessPiece;
import hr.tvz.chess.pieces.common.Color;
import hr.tvz.chess.pieces.common.PieceType;
import hr.tvz.chess.pieces.impl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marko on 14.8.2016..
 */
public class Chessboard {

    public static List<Move> moveHistory = new ArrayList<>();

    private static Player whitePlayer;

    private static Player blackPlayer;

    private static Player currentPlayer;

    private static boolean whiteCastlingKingSide = true;

    private static boolean whiteCastlingQueenSide = true;

    private static boolean blackCastlingKingSide = true;

    private static boolean blackCastlingQueenSide = true;

    private static AbstractChessPiece chessBoard[][];

    private static AbstractChessPiece[][] getInitalChessBoardPosition() {
        AbstractChessPiece chessBoard[][] = {
                {new Rook(Color.BLACK),
                        new Knight(Color.BLACK),
                        new Bishop(Color.BLACK),
                        new Queen(Color.BLACK),
                        new King(Color.BLACK),
                        new Bishop(Color.BLACK),
                        new Knight(Color.BLACK),
                        new Rook(Color.BLACK)},
                {new Pawn(Color.BLACK),
                        new Pawn(Color.BLACK),
                        new Pawn(Color.BLACK),
                        new Pawn(Color.BLACK),
                        new Pawn(Color.BLACK),
                        new Pawn(Color.BLACK),
                        new Pawn(Color.BLACK),
                        new Pawn(Color.BLACK)},
                {new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare()},
                {new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare()},
                {new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare()},
                {new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare(),
                        new EmptySquare()},
                {new Pawn(Color.WHITE),
                        new Pawn(Color.WHITE),
                        new Pawn(Color.WHITE),
                        new Pawn(Color.WHITE),
                        new Pawn(Color.WHITE),
                        new Pawn(Color.WHITE),
                        new Pawn(Color.WHITE),
                        new Pawn(Color.WHITE)},
                {new Rook(Color.WHITE),
                        new Knight(Color.WHITE),
                        new Bishop(Color.WHITE),
                        new Queen(Color.WHITE),
                        new King(Color.WHITE),
                        new Bishop(Color.WHITE),
                        new Knight(Color.WHITE),
                        new Rook(Color.WHITE)},
        };
        return chessBoard;
    }

    static {
        initalizeChessboard();
    }

    public static void initalizeChessboard() {

        chessBoard = getInitalChessBoardPosition();

        int whiteKingPosition = 0;

        int blackKingPosition = 0;

        for (int i = 0; i < 64; i++) {
            AbstractChessPiece chessPiece = chessBoard[i / 8][i % 8];
            if (chessPiece.getType().equals(PieceType.KING)) {
                if (chessPiece.getColor().equals(Color.WHITE)) {
                    whiteKingPosition = i;
                } else {
                    blackKingPosition = i;
                }
            }
        }

        whitePlayer = new Player(Color.WHITE, whiteKingPosition);

        blackPlayer = new Player(Color.BLACK, blackKingPosition);
    }

    public static AbstractChessPiece getPiece(int row, int column) {
        return Chessboard.chessBoard[row][column];
    }

    public static void setPiece(int row, int column, AbstractChessPiece chessPiece) {
        Chessboard.chessBoard[row][column] = chessPiece;
    }

    public static boolean isEmptyPiece(int row, int column) {
        return isInChessboard(row, column) && Chessboard.chessBoard[row][column].isEmpty();
    }

    public static boolean isOppositePlayerColor(int row, int column) {
        return isInChessboard(row, column)
                && Chessboard.chessBoard[row][column].getColor().equals(Chessboard.getOppositePlayerColor());
    }

    public static boolean isBlackColor(int row, int column) {
        return isInChessboard(row, column)
                && !Chessboard.chessBoard[row][column].isEmpty()
                && Chessboard.chessBoard[row][column].getColor().equals(Color.BLACK);
    }

    public static boolean isWhiteColor(int row, int column) {
        return isInChessboard(row, column)
                && !Chessboard.chessBoard[row][column].isEmpty()
                && Chessboard.chessBoard[row][column].getColor().equals(Color.WHITE);
    }

    public static boolean isInChessboard(int row, int column) {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }


    public static Color getOppositePlayerColor() {
        if (currentPlayer.getColor().equals(Color.BLACK)) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public static void changePlayer() {

        if (currentPlayer.getColor().equals(Color.WHITE)) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }

    public static List<Move> generatePossibleMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (!chessBoard[i / 8][i % 8].isEmpty() && chessBoard[i / 8][i % 8].getColor().equals(currentPlayer.getColor())) {
                moves.addAll(chessBoard[i / 8][i % 8].getPossibleMoves(i));
            }
        }
        return moves;
    }

    public static boolean canEnPassant(int c) {
        Move lastMove = getLastMove();
        return lastMove != null && c == lastMove.getNewPositionColumn() && lastMove.getPieceThatMoves().getType().equals(PieceType.PAWN) &&
                Math.abs(lastMove.getNewPositionRow()
                        - lastMove.getOldPositionRow()) == 2 &&
                ((Chessboard.isInChessboard(lastMove.getNewPositionRow() - 1, lastMove.getNewPositionColumn())
                        && Chessboard.getPiece(lastMove.getNewPositionRow() - 1, lastMove.getNewPositionColumn()).isOppositePlayerPieceType(PieceType.PAWN))
                        || (Chessboard.isInChessboard(lastMove.getNewPositionRow() + 1, lastMove.getNewPositionColumn()) &&
                                Chessboard.getPiece(lastMove.getNewPositionRow() + 1, lastMove.getNewPositionColumn()).isOppositePlayerPieceType(PieceType.PAWN)));
    }

    public static boolean kingSafe() {
        int temp = 1;
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {

                while (isEmptyPiece(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8 + temp * j)) {
                    temp++;
                }
                if (isInChessboard(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8 + temp * j) && (
                        getPiece(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8 + temp * j).isOppositePlayerPieceType(PieceType.BISHOP) ||
                                getPiece(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8 + temp * j).isOppositePlayerPieceType(PieceType.QUEEN))) {
                    return false;
                }

                temp = 1;
            }
        }

        for (int i = -1; i <= 1; i += 2) {

            while (isEmptyPiece(currentPlayer.getKingPosition() / 8, currentPlayer.getKingPosition() % 8 + temp * i)) {
                temp++;
            }
            if (isInChessboard(currentPlayer.getKingPosition() / 8, currentPlayer.getKingPosition() % 8 + temp * i) && (
                    getPiece(currentPlayer.getKingPosition() / 8, currentPlayer.getKingPosition() % 8 + temp * i).isOppositePlayerPieceType(PieceType.ROOK) ||
                            getPiece(currentPlayer.getKingPosition() / 8, currentPlayer.getKingPosition() % 8 + temp * i).isOppositePlayerPieceType(PieceType.QUEEN))) {
                return false;
            }

            temp = 1;

            while (isEmptyPiece(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8)) {
                temp++;
            }
            if (isInChessboard(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8) && (
                    getPiece(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8).isOppositePlayerPieceType(PieceType.ROOK) ||
                            getPiece(currentPlayer.getKingPosition() / 8 + temp * i, currentPlayer.getKingPosition() % 8).isOppositePlayerPieceType(PieceType.QUEEN))) {
                return false;
            }

            temp = 1;
        }

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                if (isInChessboard(currentPlayer.getKingPosition() / 8 + i, currentPlayer.getKingPosition() % 8 + j * 2)
                        && getPiece(currentPlayer.getKingPosition() / 8 + i, currentPlayer.getKingPosition() % 8 + j * 2).isOppositePlayerPieceType(PieceType.KNIGHT)) {
                    return false;
                }

                if (isInChessboard(currentPlayer.getKingPosition() / 8 + i * 2, currentPlayer.getKingPosition() % 8 + j)
                        && getPiece(currentPlayer.getKingPosition() / 8 + i * 2, currentPlayer.getKingPosition() % 8 + j).isOppositePlayerPieceType(PieceType.KNIGHT)) {
                    return false;
                }
            }
        }

        if ((currentPlayer.getColor().equals(Color.WHITE) && currentPlayer.getKingPosition() >= 16)) {

            if (isInChessboard(currentPlayer.getKingPosition() / 8 - 1, currentPlayer.getKingPosition() % 8 - 1)
                    && getPiece(currentPlayer.getKingPosition() / 8 - 1, currentPlayer.getKingPosition() % 8 - 1).isOppositePlayerPieceType(PieceType.PAWN)) {
                return false;
            }


            if (isInChessboard(currentPlayer.getKingPosition() / 8 - 1, currentPlayer.getKingPosition() % 8 + 1)
                    && getPiece(currentPlayer.getKingPosition() / 8 - 1, currentPlayer.getKingPosition() % 8 + 1).isOppositePlayerPieceType(PieceType.PAWN)) {
                return false;
            }

            if (!checkForEnemyKing()) {
                return false;
            }

        }

        if ((currentPlayer.getColor().equals(Color.BLACK) && currentPlayer.getKingPosition() <= 48)) {

            if (isInChessboard(currentPlayer.getKingPosition() / 8 + 1, currentPlayer.getKingPosition() % 8 - 1)
                    && getPiece(currentPlayer.getKingPosition() / 8 + 1, currentPlayer.getKingPosition() % 8 - 1).isOppositePlayerPieceType(PieceType.PAWN)) {
                return false;
            }

            if (isInChessboard(currentPlayer.getKingPosition() / 8 + 1, currentPlayer.getKingPosition() % 8 + 1)
                    && getPiece(currentPlayer.getKingPosition() / 8 + 1, currentPlayer.getKingPosition() % 8 + 1).isOppositePlayerPieceType(PieceType.PAWN)) {
                return false;
            }

            if (!checkForEnemyKing()) {
                return false;
            }

        }

        return true;
    }

    private static boolean checkForEnemyKing() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (isInChessboard(currentPlayer.getKingPosition() / 8 + i, currentPlayer.getKingPosition() % 8 + j)
                            && getPiece(currentPlayer.getKingPosition() / 8 + i, currentPlayer.getKingPosition() % 8 + j).isOppositePlayerPieceType(PieceType.KING)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void makeMove(Move move) {
        if (move.isPawnPromotion()) {
            if (currentPlayer.equals(getWhitePlayer())) {
                setPiece(1, move.getOldPositionColumn(), new EmptySquare());
                setPiece(0, move.getNewPositionColumn(), move.getPromotionPiece());
            } else {
                setPiece(6, move.getOldPositionColumn(), new EmptySquare());
                setPiece(7, move.getNewPositionColumn(), move.getPromotionPiece());
            }
        } else if (move.isEnPassant()) {
            int sideMoveValue = Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE) ? 1 : -1;
            setPiece(move.getNewPositionRow(), move.getNewPositionColumn(), getPiece(move.getOldPositionRow(), move.getOldPositionColumn()));
            setPiece(move.getOldPositionRow(), move.getOldPositionColumn(), new EmptySquare());
            setPiece(move.getNewPositionRow() + sideMoveValue, move.getNewPositionColumn(), new EmptySquare());
        } else if (move.isCastling()) {
            Castling castling = move.getCastling();

            if (castling.equals(Castling.WHITE_KING_SIDE) || castling.equals(Castling.WHITE_QUEEN_SIDE)) {
                setWhiteCastlingKingSide(false);
                setWhiteCastlingQueenSide(false);
            } else if (castling.equals(Castling.BLACK_KING_SIDE) || castling.equals(Castling.BLACK_QUEEN_SIDE)) {
                setBlackCastlingKingSide(false);
                setBlackCastlingQueenSide(false);
            }

            setPiece(castling.getKingEndingRow(), castling.getKingEndingColumn(),
                    getPiece(castling.getKingStartingRow(), castling.getKingStartingColumn()));
            setPiece(castling.getKingStartingRow(), castling.getKingStartingColumn(), new EmptySquare());
            setPiece(castling.getRookEndingRow(), castling.getRookEndingColumn(),
                    getPiece(castling.getRookStartingRow(), castling.getRookStartingColumn()));
            setPiece(castling.getRookStartingRow(), castling.getRookStartingColumn(), new EmptySquare());
        } else {
            setPiece(move.getNewPositionRow(), move.getNewPositionColumn(), getPiece(move.getOldPositionRow(), move.getOldPositionColumn()));
            setPiece(move.getOldPositionRow(), move.getOldPositionColumn(), new EmptySquare());
            if (getPiece(move.getNewPositionRow(), move.getNewPositionColumn()).getType().equals(PieceType.KING)) {
                currentPlayer.setKingPosition(8 * move.getNewPositionRow() + move.getNewPositionColumn());
            }
        }
        moveHistory.add(move);
    }

    public static void undoMove(Move move) {
        if (move.isPawnPromotion()) {
            if (currentPlayer.equals(getWhitePlayer())) {
                setPiece(1, move.getOldPositionColumn(), new Pawn(currentPlayer.getColor()));
                setPiece(0, move.getNewPositionColumn(), move.getCapture());
            } else {
                setPiece(6, move.getOldPositionColumn(), new Pawn(currentPlayer.getColor()));
                setPiece(7, move.getNewPositionColumn(), move.getCapture());
            }
        } else if (move.isEnPassant()) {
            int sideMoveValue = Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE) ? 1 : -1;
            setPiece(move.getOldPositionRow(), move.getOldPositionColumn(), move.getPieceThatMoves());
            setPiece(move.getNewPositionRow() + sideMoveValue, move.getNewPositionColumn(), move.getCapture());
            setPiece(move.getNewPositionRow(), move.getNewPositionColumn(), new EmptySquare());
        } else if (move.isCastling()) {
            Castling castling = move.getCastling();

            if (castling.equals(Castling.WHITE_KING_SIDE) || castling.equals(Castling.WHITE_QUEEN_SIDE)) {
                setWhiteCastlingKingSide(true);
                setWhiteCastlingQueenSide(true);
            } else if (castling.equals(Castling.BLACK_KING_SIDE) || castling.equals(Castling.BLACK_QUEEN_SIDE)) {
                setBlackCastlingKingSide(true);
                setBlackCastlingQueenSide(true);
            }

            setPiece(castling.getKingStartingRow(), castling.getKingStartingColumn(),
                    getPiece(castling.getKingEndingRow(), castling.getKingEndingColumn()));
            setPiece(castling.getKingEndingRow(), castling.getKingEndingColumn(), new EmptySquare());
            setPiece(castling.getRookStartingRow(), castling.getRookStartingColumn(),
                    getPiece(castling.getRookEndingRow(), castling.getRookEndingColumn()));
            setPiece(castling.getRookEndingRow(), castling.getRookEndingColumn(), new EmptySquare());
        } else {
            setPiece(move.getOldPositionRow(), move.getOldPositionColumn(), getPiece(move.getNewPositionRow(), move.getNewPositionColumn()));
            setPiece(move.getNewPositionRow(), move.getNewPositionColumn(), move.getCapture());
            if (getPiece(move.getOldPositionRow(), move.getOldPositionColumn()).getType().equals(PieceType.KING)) {
                currentPlayer.setKingPosition(8 * move.getOldPositionRow() + move.getOldPositionColumn());
            }
        }
        moveHistory.remove(move);
    }

    public static Move getLastMove() {
        if (!moveHistory.isEmpty()) {
            return moveHistory.get(moveHistory.size() - 1);
        }
        return null;
    }

    public static boolean hasMoved(AbstractChessPiece chessPiece) {
        if (moveHistory.isEmpty()) {
            return false;
        } else {
            for (Move move : moveHistory) {
                if (move.getPieceThatMoves().equals(chessPiece)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Chessboard.currentPlayer = currentPlayer;
    }

    public static Player getWhitePlayer() {
        return whitePlayer;
    }

    public static Player getBlackPlayer() {
        return blackPlayer;
    }

    public static boolean isWhiteCastlingKingSide() {
        return whiteCastlingKingSide;
    }

    public static void setWhiteCastlingKingSide(boolean whiteCastlingKingSide) {
        Chessboard.whiteCastlingKingSide = whiteCastlingKingSide;
    }

    public static boolean isWhiteCastlingQueenSide() {
        return whiteCastlingQueenSide;
    }

    public static void setWhiteCastlingQueenSide(boolean whiteCastlingQueenSide) {
        Chessboard.whiteCastlingQueenSide = whiteCastlingQueenSide;
    }

    public static boolean isBlackCastlingKingSide() {
        return blackCastlingKingSide;
    }

    public static void setBlackCastlingKingSide(boolean blackCastlingKingSide) {
        Chessboard.blackCastlingKingSide = blackCastlingKingSide;
    }

    public static boolean isBlackCastlingQueenSide() {
        return blackCastlingQueenSide;
    }

    public static void setBlackCastlingQueenSide(boolean blackCastlingQueenSide) {
        Chessboard.blackCastlingQueenSide = blackCastlingQueenSide;
    }
}
