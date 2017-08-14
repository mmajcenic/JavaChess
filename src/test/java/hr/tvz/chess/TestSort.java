package hr.tvz.chess;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.algorithms.AlphaBeta;
import hr.tvz.chess.engine.algorithms.SearchAlgorithm;
import org.junit.Test;

public class TestSort {

    @Test
    public void testSort() {
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        SearchAlgorithm searchAlgorithm = new AlphaBeta();
        SearchAlgorithm.sortMoves(Chessboard.generatePossibleMoves());
    }

}
