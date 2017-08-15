package hr.tvz.chess.engine.algorithms;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Evaluation;
import hr.tvz.chess.engine.Move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Marko on 15.8.2016..
 */
public class AlphaBetaWithIterativeDeepening extends SearchAlgorithm {

    private static int iterativeDeepeningMaxDepth;

    private static long maxTime = 6000;
    private static long startTime;

    private static Move bestMoveFromIteration;

    public AlphaBetaWithIterativeDeepening(int maxtime) {
        AlphaBetaWithIterativeDeepening.maxTime = maxtime;
    }

    private static Move alphaBeta(int depth, int alpha, int beta, Move move, boolean maximizes) {
        List<Move> moves;
        if (depth == iterativeDeepeningMaxDepth && depth != 0) {
            if (sortedRootMoves.isEmpty()) {
                moves = sortMoves(Chessboard.generatePossibleMoves());
            } else {
                moves = sortedRootMoves;
            }
        } else {
            moves = sortMoves(Chessboard.generatePossibleMoves());
        }

        if (depth == 0 || moves.size() == 0) {
            move.setValue(Evaluation.evaluateBoard(moves.size(), depth));
            return move;
        }

        int bestValue = maximizes ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move moveFromList : moves) {
            if (hasSearchExpired()) {
                break;
            }
            Chessboard.makeMove(moveFromList);
            Chessboard.changePlayer();
            Move returnMove;
            long zobristHash = Zobrist.getZobristHash();
            if (transpositionTable.containsKey(zobristHash)) {
                returnMove = transpositionTable.get(zobristHash);
            } else {
                returnMove = alphaBeta(depth - 1, alpha, beta, moveFromList, !maximizes);
                addPositionToTable(returnMove, zobristHash);
            }
            if (depth == iterativeDeepeningMaxDepth) {
                rootMoves.add(returnMove);
            }

            Chessboard.changePlayer();
            Chessboard.undoMove(moveFromList);
            if (maximizes) {
                if (returnMove.getValue() > bestValue) {
                    bestValue = returnMove.getValue();
                    if (depth == iterativeDeepeningMaxDepth) {
                        move = returnMove;
                    }
                }
                alpha = Math.max(alpha, bestValue);
            } else {
                if (returnMove.getValue() < bestValue) {
                    bestValue = returnMove.getValue();
                    if (depth == iterativeDeepeningMaxDepth) {
                        move = returnMove;
                    }
                }
                beta = Math.min(beta, bestValue);
            }
            if (beta <= alpha) {
                break;
            }
        }
        move.setValue(bestValue);
        addToCounter(depth);
        return move;
    }

    private static List<Move> sortForIterativeDeepening() {
        rootMoves.sort(Comparator.comparingInt(Move::getValue).reversed());
        List<Move> sortedRootMove = new ArrayList<>();
        sortedRootMove.addAll(rootMoves);
        return sortedRootMove.subList(0, Math.min(sortedRootMove.size(), 10));

    }

    private static boolean hasSearchExpired() {
        return System.currentTimeMillis() - startTime > maxTime;
    }

    @Override
    public Move search(Move move) {
        startTime = System.currentTimeMillis();
        for (int maxDepth = 0; maxDepth <= maximumSearchDepth; maxDepth+=2) {
            iterativeDeepeningMaxDepth = maxDepth;
            move = alphaBeta(maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, new Move(), true);
            sortedRootMoves = sortForIterativeDeepening();
            if (hasSearchExpired()) {
                move = bestMoveFromIteration;
                break;
            }
            bestMoveFromIteration = move;
            clearTranspositionTable();
            clearRootTable();
        }
        sortedRootMoves.clear();
        return move;
    }

}
