package hr.tvz.chess.engine.algorithms;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Evaluation;
import hr.tvz.chess.engine.Move;

import java.util.List;

/**
 * Created by Marko on 28.7.2017..
 */
public class AlphaBetaWithTransposition extends SearchAlgorithm {

    private static Move alphaBeta(int depth, int alpha, int beta, Move move, boolean maximizes) {

        List<Move> moves = sortMoves(Chessboard.generatePossibleMoves());

        if (depth == 0 || moves.size() == 0) {
            move.setValue(Evaluation.evaluateBoard(moves.size(), depth));
            return move;
        }

        int bestValue = maximizes ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move moveFromList : moves) {
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

            Chessboard.changePlayer();
            Chessboard.undoMove(moveFromList);
            if (maximizes) {
                if (returnMove.getValue() > bestValue) {
                    bestValue = returnMove.getValue();
                    if (depth == maximumSearchDepth) {
                        move = returnMove;
                    }
                }
                alpha = Math.max(alpha, bestValue);
            } else {
                if (returnMove.getValue() < bestValue) {
                    bestValue = returnMove.getValue();
                    if (depth == maximumSearchDepth) {
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

    @Override
    public Move search(Move move) {
        return alphaBeta(maximumSearchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, move, true);
    }
}
