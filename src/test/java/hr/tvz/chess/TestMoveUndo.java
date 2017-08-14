package hr.tvz.chess;

import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.AlphaBeta;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithIterativeDeepening;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithTransposition;
import hr.tvz.chess.engine.algorithms.SearchAlgorithm;
import hr.tvz.chess.pieces.impl.EmptySquare;
import org.junit.Test;

import java.util.List;

/**
 * Created by Marko on 16.4.2017..
 */
public class TestMoveUndo {


    @Test
    public void testMoveUndo() {
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        SearchAlgorithm searchAlgorithm = new AlphaBeta();
        Move move = new Move(7, 1, 5, 2, new EmptySquare(), Chessboard.getPiece(7, 1));
        Chessboard.makeMove(move);
        move = new Move(1, 3, 3, 3, new EmptySquare(), Chessboard.getPiece(1, 3));
        Chessboard.makeMove(move);
        move = new Move(6, 4, 4, 4, new EmptySquare(), Chessboard.getPiece(6, 4));
        Chessboard.makeMove(move);
        move = new Move(0, 6, 2, 5, new EmptySquare(), Chessboard.getPiece(0, 6));
        Chessboard.makeMove(move);
        move = new Move(6, 5, 5, 5, new EmptySquare(), Chessboard.getPiece(6, 5));
        Chessboard.makeMove(move);
        move = new Move(3, 3, 4, 4, Chessboard.getPiece(4, 4), Chessboard.getPiece(3, 3));
        Chessboard.makeMove(move);
        move = new Move(5, 5, 4, 4, Chessboard.getPiece(4, 4), Chessboard.getPiece(5, 5));
        Chessboard.makeMove(move);
        move = new Move(0, 2, 4, 6, Chessboard.getPiece(4, 6), Chessboard.getPiece(0, 2));
        Chessboard.makeMove(move);
        move = new Move(7, 6, 5, 5, Chessboard.getPiece(5, 5), Chessboard.getPiece(7, 6));
        Chessboard.makeMove(move);
        Chessboard.changePlayer();
        Chessboard.makeMove(searchAlgorithm.search(move));
        List<Move> history = Chessboard.moveHistory;
    }

}
