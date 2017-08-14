package hr.tvz.chess.engine.algorithms;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Evaluation;
import hr.tvz.chess.engine.Move;

import java.util.*;

/**
 * Created by marko on 10/02/2017.
 */
public abstract class SearchAlgorithm {

    static Map<Long, Move> transpositionTable = new HashMap<>();

    static List<Move> rootMoves = new ArrayList<>();

    static List<Move> sortedRootMoves = new ArrayList<>();

    static int maximumSearchDepth = 4;

    private static Map<Integer, Integer> moveCounter = new HashMap<>();

    public abstract Move search(Move move);

    static void addPositionToTable(Move move, long zobristHash) {
        transpositionTable.put(zobristHash, move);
    }

    public static void clearTranspositionTable() {
        transpositionTable.clear();
    }

    public static void clearRootTable() {
        rootMoves.clear();
    }

    public static void clearSortedRootMoves() {
        sortedRootMoves.clear();
    }

    public static List<Move> getRootMoves() {
        return rootMoves;
    }

    public static int enPassantCount = 0;

    public static List<Move> sortMoves(List<Move> moves) {
        moves.forEach(move -> {
            Chessboard.makeMove(move);
            move.setValue(Evaluation.evaluateBoard(-1, 0));
            Chessboard.undoMove(move);
        });
        moves.sort(Comparator.comparingInt(Move::getValue).reversed());
        return moves;
    }

    static void addToCounter(int depth) {
        if(moveCounter.get(depth) != null) {
            int counter = moveCounter.get(depth);
            counter++;
            moveCounter.put(depth, counter);
        } else {
            moveCounter.put(depth, 1);
        }
    }

    public static Map<Integer, Integer> getMoveCounter() {
        return moveCounter;
    }

    public static int getMaximumSearchDepth() {
        return maximumSearchDepth;
    }
}
