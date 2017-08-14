package hr.tvz.chess.engine;

import hr.tvz.chess.pieces.common.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Evaluation {
    private static int whitePawnBoard[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {5, 5, 10, 25, 25, 10, 5, 5},
            {0, 0, 0, 20, 20, 0, 0, 0},
            {5, -5, -10, 0, 0, -10, -5, 5},
            {5, 10, 10, -20, -20, 10, 10, 5},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    private static int whiteRookBoard[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {5, 10, 10, 10, 10, 10, 10, 5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {0, 0, 0, 5, 5, 0, 0, 0}};
    private static int whiteKnightBoard[][] = {
            {-50, -40, -30, -30, -30, -30, -40, -50},
            {-40, -20, 0, 0, 0, 0, -20, -40},
            {-30, 0, 10, 15, 15, 10, 0, -30},
            {-30, 5, 15, 20, 20, 15, 5, -30},
            {-30, 0, 15, 20, 20, 15, 0, -30},
            {-30, 5, 10, 15, 15, 10, 5, -30},
            {-40, -20, 0, 5, 5, 0, -20, -40},
            {-50, -40, -30, -30, -30, -30, -40, -50}};
    private static int whiteBishopBoard[][] = {
            {-20, -10, -10, -10, -10, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 10, 10, 5, 0, -10},
            {-10, 5, 5, 10, 10, 5, 5, -10},
            {-10, 0, 10, 10, 10, 10, 0, -10},
            {-10, 10, 10, 10, 10, 10, 10, -10},
            {-10, 5, 0, 0, 0, 0, 5, -10},
            {-20, -10, -10, -10, -10, -10, -10, -20}};
    private static int whiteQueenBoard[][] = {
            {-20, -10, -10, -5, -5, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 5, 5, 5, 0, -10},
            {-5, 0, 5, 5, 5, 5, 0, -5},
            {0, 0, 5, 5, 5, 5, 0, -5},
            {-10, 5, 5, 5, 5, 5, 0, -10},
            {-10, 0, 5, 0, 0, 0, 0, -10},
            {-20, -10, -10, -5, -5, -10, -10, -20}};
    private static int whiteKingMidBoard[][] = {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            {20, 20, 0, 0, 0, 0, 20, 20},
            {20, 30, 10, 0, 0, 10, 30, 20}};
    private static int whiteKingEndBoard[][] = {
            {-50, -40, -30, -20, -20, -30, -40, -50},
            {-30, -20, -10, 0, 0, -10, -20, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -30, 0, 0, 0, 0, -30, -30},
            {-50, -30, -30, -30, -30, -30, -30, -50}};


    private static int blackPawnBoard[][];

    private static int blackRookBoard[][];

    private static int blackKnightBoard[][];

    private static int blackBishopBoard[][];

    private static int blackQueenBoard[][];

    private static int blackKingMidBoard[][];

    private static int blackKingEndBoard[][];

    static {

        blackPawnBoard = reverseBoard(whitePawnBoard);

        blackRookBoard = reverseBoard(whiteRookBoard);

        blackKnightBoard = reverseBoard(whiteKnightBoard);

        blackBishopBoard = reverseBoard(whiteBishopBoard);

        blackQueenBoard = reverseBoard(whiteQueenBoard);

        blackKingMidBoard = reverseBoard(whiteKingMidBoard);

        blackKingEndBoard = reverseBoard(whiteKingEndBoard);

    }

    private static int[][] reverseBoard(int[][] board) {
        int[][] reverseBoard = board.clone();
        List<int[]> reverseList = Arrays.asList(reverseBoard);
        Collections.reverse(reverseList);
        reverseList.forEach(row -> {
            for (int i = 0; i < row.length / 2; i++) {
                int temp = row[i];
                row[i] = row[row.length - i - 1];
                row[row.length - i - 1] = temp;
            }
        });
        return (int[][]) reverseList.toArray();
    }

    public static int evaluateBoard(int numberOfMoves, int depth) {
        int value = 0;
        int materialValue = materialValue();
        value += attackedValue();
        value += materialValue;
        value += mobilityValue(numberOfMoves, depth);
        value += positionalValue(materialValue);
        Chessboard.changePlayer();
        materialValue = materialValue();
        value -= attackedValue();
        value -= materialValue;
        value -= mobilityValue(numberOfMoves, depth);
        value -= positionalValue(materialValue);
        Chessboard.changePlayer();
        return value + depth * 50;
    }

    private static int attackedValue() {
        int value = 0;
        int tempKingPosition = Chessboard.getCurrentPlayer().getKingPosition();
        for (int i = 0; i < 64; i++) {
            if (!Chessboard.isEmptyPiece(i / 8, i % 8) && !Chessboard.isOppositePlayerColor(i / 8, i % 8)) {
                switch (Chessboard.getPiece(i / 8, i % 8).getType()) {
                    case PAWN:
                        Chessboard.getCurrentPlayer().setKingPosition(i);
                        if (!Chessboard.kingSafe()) {
                            value -= 50;
                        }

                    break;
                    case ROOK:
                        Chessboard.getCurrentPlayer().setKingPosition(i);
                        if (!Chessboard.kingSafe()) {
                            value -= 250;
                        }

                    break;
                    case KNIGHT:
                        Chessboard.getCurrentPlayer().setKingPosition(i);
                        if (!Chessboard.kingSafe()) {
                            value -= 150;
                        }

                    break;
                    case BISHOP:
                        Chessboard.getCurrentPlayer().setKingPosition(i);
                        if (!Chessboard.kingSafe()) {
                            value -= 150;
                        }

                    break;
                    case QUEEN: {
                        Chessboard.getCurrentPlayer().setKingPosition(i);
                        if (!Chessboard.kingSafe()) {
                            value -= 450;
                        }
                    }
                    break;
                }
            }
        }
        Chessboard.getCurrentPlayer().setKingPosition(tempKingPosition);
        if (!Chessboard.kingSafe()) {
            value -= 100;
        }
        return value;
    }

    private static int materialValue() {
        int value = 0;
        int bishopCounter = 0;

        for (int i = 0; i < 64; i++) {
            if (!Chessboard.isEmptyPiece(i / 8, i % 8) && !Chessboard.isOppositePlayerColor(i / 8, i % 8)) {
                switch (Chessboard.getPiece(i / 8, i % 8).getType()) {
                    case PAWN:
                        value += 100;
                        break;
                    case ROOK:
                        value += 500;
                        break;
                    case KNIGHT:
                        value += 300;
                        break;
                    case BISHOP:
                        bishopCounter += 1;
                        break;
                    case QUEEN:
                        value += 900;
                        break;
                }
            }
        }

        if (bishopCounter == 2) {
            value += 300 * bishopCounter;
        } else if (bishopCounter == 1) {
            value += 250;
        }

        return value;
    }

    private static int mobilityValue(int numberOfMoves, int depth) {
        int value = 0;
        value += numberOfMoves * 5;
        if (numberOfMoves == 0) {
            if (!Chessboard.kingSafe()) {
                value += -200000 * depth;
            } else {
                value += -150000 * depth;
            }
        }
        return value;
    }

    private static int positionalValue(int material) {
        int counter = 0;
        for (int i = 0; i < 64; i++) {
            if (!Chessboard.isEmptyPiece(i / 8, i % 8) && !Chessboard.isOppositePlayerColor(i / 8, i % 8)) {
                switch (Chessboard.getPiece(i / 8, i % 8).getType()) {
                    case PAWN:
                        if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                            counter += whitePawnBoard[i / 8][i % 8];
                        } else {
                            counter += blackPawnBoard[i / 8][i % 8];
                        }
                        break;
                    case ROOK:
                        if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                            counter += whiteRookBoard[i / 8][i % 8];
                        } else {
                            counter += blackRookBoard[i / 8][i % 8];
                        }
                        break;
                    case KNIGHT:
                        if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                            counter += whiteKnightBoard[i / 8][i % 8];
                        } else {
                            counter += blackKnightBoard[i / 8][i % 8];
                        }
                        break;
                    case BISHOP:
                        if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                            counter += whiteBishopBoard[i / 8][i % 8];
                        } else {
                            counter += blackBishopBoard[i / 8][i % 8];
                        }
                        break;
                    case QUEEN:
                        if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                            counter += whiteQueenBoard[i / 8][i % 8];
                        } else {
                            counter += blackQueenBoard[i / 8][i % 8];
                        }
                        break;
                    case KING:
                        if (material >= 1750) {
                            if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                                counter += whiteKingMidBoard[i / 8][i % 8];
                                counter += Chessboard.getPiece(i / 8, i % 8).getPossibleMoves(Chessboard.getCurrentPlayer().getKingPosition()).size() * 50;
                            } else {
                                counter += blackKingMidBoard[i / 8][i % 8];
                                counter += Chessboard.getPiece(i / 8, i % 8).getPossibleMoves(Chessboard.getCurrentPlayer().getKingPosition()).size() * 50;
                            }
                        } else {
                            if (Chessboard.getCurrentPlayer().getColor().equals(Color.WHITE)) {
                                counter += whiteKingEndBoard[i / 8][i % 8];
                                counter += Chessboard.getPiece(i / 8, i % 8).getPossibleMoves(Chessboard.getCurrentPlayer().getKingPosition()).size() * 150;
                            } else {
                                counter += blackKingEndBoard[i / 8][i % 8];
                                counter += Chessboard.getPiece(i / 8, i % 8).getPossibleMoves(Chessboard.getCurrentPlayer().getKingPosition()).size() * 150;
                            }
                        }
                        break;
                }
            }
        }
        return counter;
    }
}