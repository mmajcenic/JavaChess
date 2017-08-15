package hr.tvz.chess;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithIterativeDeepening;
import hr.tvz.chess.engine.algorithms.SearchAlgorithm;
import hr.tvz.chess.engine.algorithms.Zobrist;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Marko on 26.2.2017..
 */
public class ZobristHashingTest {

    @Test
    public void testHashing() {
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening(6000);
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        long firstZobrist = Zobrist.getZobristHash();
        Move move = searchAlgorithm.search(new Move());
        Chessboard.makeMove(move);
        long secondZobrist = Zobrist.getZobristHash();
        Assert.assertNotEquals(firstZobrist, secondZobrist);
        Chessboard.undoMove(move);
        secondZobrist = Zobrist.getZobristHash();
        Assert.assertEquals(firstZobrist, secondZobrist);
    }

}
