package hr.tvz.chess.engine.algorithms;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Evaluation;
import hr.tvz.chess.engine.Move;

import java.util.List;

/**
 * Created by Marko on 15.8.2016..
 */
public class Minimax extends SearchAlgorithm {

    private static Move minimax(int depth, Move move, boolean maximizes) {
        List<Move> moves = Chessboard.generatePossibleMoves();
        if (depth == 0 || moves.size() == 0) {
            move.setValue(Evaluation.evaluateBoard(moves.size(), depth));
            return move;
        }

        int bestValue = maximizes ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move moveFromList : moves) {
            addToCounter(depth);
            Chessboard.makeMove(moveFromList);
            Chessboard.changePlayer();
            Move returnMove = minimax(depth - 1, moveFromList, !maximizes);
            Chessboard.changePlayer();
            Chessboard.undoMove(moveFromList);
            if (maximizes && returnMove.getValue() > bestValue) {
                bestValue = returnMove.getValue();
                if (depth == maximumSearchDepth) {
                    move = returnMove;
                }
            } else if (!maximizes && returnMove.getValue() < bestValue) {
                bestValue = returnMove.getValue();
                if (depth == maximumSearchDepth) {
                    move = returnMove;
                }
            }
        }
        move.setValue(bestValue);
        return move;
    }


    @Override
    public Move search(Move move) {
        return minimax(maximumSearchDepth, move, true);
    }

}
