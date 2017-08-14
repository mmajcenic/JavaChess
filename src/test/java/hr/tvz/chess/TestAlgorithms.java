package hr.tvz.chess;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.*;
import org.junit.Test;

/**
 * Created by Marko on 16.4.2017..
 */
public class TestAlgorithms {

    @Test
    public void minimax() {
        System.out.println("===Minimax===");
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        SearchAlgorithm searchAlgorithm = new Minimax();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.getMoveCounter().forEach((k, v) -> {
            System.out.println("Depth: " + (SearchAlgorithm.getMaximumSearchDepth() - k) + "; positions: " + v);
        });
    }

    @Test
    public void alphabeta() {
        System.out.println("===Alphabeta===");
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        SearchAlgorithm searchAlgorithm = new AlphaBeta();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.getMoveCounter().forEach((k, v) -> {
            System.out.println("Depth: " + (SearchAlgorithm.getMaximumSearchDepth() - k) + "; positions: " + v);
        });
    }

    @Test
    public void transposition() {
        System.out.println("===Transposition===");
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithTransposition();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.getMoveCounter().forEach((k, v) -> {
            System.out.println("Depth: " + (SearchAlgorithm.getMaximumSearchDepth() - k) + "; positions: " + v);
        });
    }

    @Test
    public void iterative() {
        System.out.println("===Iterative===");
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.getMoveCounter().forEach((k, v) -> {
            System.out.println("Depth: " + (SearchAlgorithm.getMaximumSearchDepth() - k) + "; positions: " + v);
        });
    }

}
