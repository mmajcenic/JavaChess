package hr.tvz.chess.engine.algorithms;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.pieces.common.AbstractChessPiece;
import hr.tvz.chess.pieces.common.Color;

import java.security.SecureRandom;

/**
 * Created by Marko on 24.2.2017..
 */
public class Zobrist {

    private static long zobristChessboardArray[][][] = new long[2][6][64];
    private static long zobristEnPassant[] = new long[8];
    private static long zobristCastling[] = new long[4];
    private static long zobristBlackMove;

    static {

        for (int color = 0; color < 2; color++) {
            for (int pieceType = 0; pieceType < 6; pieceType++) {
                for (int square = 0; square < 64; square++) {
                    zobristChessboardArray[color][pieceType][square] = random64();
                }
            }
        }

        for (int column = 0; column < 8; column++) {
            zobristEnPassant[column] = random64();
        }

        for (int i = 0; i < 4; i++) {
            zobristCastling[i] = random64();
        }

        zobristBlackMove = random64();
    }

    public static long getZobristHash() {
        long zobristHash = 0;
        for (int square = 0; square < 64; square++) {
            int r = square / 8;
            int c = square % 8;
            AbstractChessPiece chessPiece = Chessboard.getPiece(r, c);
            switch (chessPiece.getType()) {
                case PAWN:
                    if (chessPiece.getColor().equals(Color.WHITE)) {
                        zobristHash ^= zobristChessboardArray[0][0][square];
                    } else {
                        zobristHash ^= zobristChessboardArray[1][0][square];
                    }
                    break;
                case ROOK:
                    if (chessPiece.getColor().equals(Color.WHITE)) {
                        zobristHash ^= zobristChessboardArray[0][1][square];
                    } else {
                        zobristHash ^= zobristChessboardArray[1][1][square];
                    }
                    break;
                case BISHOP:
                    if (chessPiece.getColor().equals(Color.WHITE)) {
                        zobristHash ^= zobristChessboardArray[0][2][square];
                    } else {
                        zobristHash ^= zobristChessboardArray[1][2][square];
                    }
                    break;
                case KNIGHT:
                    if (chessPiece.getColor().equals(Color.WHITE)) {
                        zobristHash ^= zobristChessboardArray[0][3][square];
                    } else {
                        zobristHash ^= zobristChessboardArray[1][3][square];
                    }
                    break;
                case QUEEN:
                    if (chessPiece.getColor().equals(Color.WHITE)) {
                        zobristHash ^= zobristChessboardArray[0][4][square];
                    } else {
                        zobristHash ^= zobristChessboardArray[1][4][square];
                    }
                    break;
                case KING:
                    if (chessPiece.getColor().equals(Color.WHITE)) {
                        zobristHash ^= zobristChessboardArray[0][5][square];
                    } else {
                        zobristHash ^= zobristChessboardArray[1][5][square];
                    }
                    break;
            }
        }

        for (int column = 0; column < 8; column++) {
            if (Chessboard.canEnPassant(column)) {
                zobristHash ^= zobristEnPassant[column];
            }
        }

        if (Chessboard.isWhiteCastlingKingSide())
            zobristHash ^= zobristCastling[0];
        if (Chessboard.isWhiteCastlingQueenSide())
            zobristHash ^= zobristCastling[1];
        if (Chessboard.isBlackCastlingKingSide())
            zobristHash ^= zobristCastling[2];
        if (Chessboard.isBlackCastlingQueenSide())
            zobristHash ^= zobristCastling[3];
        if (Chessboard.getCurrentPlayer().getColor().equals(Color.BLACK))
            zobristHash ^= zobristBlackMove;
        return zobristHash;
    }


    private static long random64() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }

}
