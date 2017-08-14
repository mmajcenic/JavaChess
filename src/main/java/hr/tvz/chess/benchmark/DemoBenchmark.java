package hr.tvz.chess.benchmark;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithIterativeDeepening;
import hr.tvz.chess.engine.algorithms.SearchAlgorithm;

/**
 * Created by Marko on 25.4.2017..
 */
@VmOptions("-XX:-TieredCompilation")
public class DemoBenchmark {

    public static void main(String[] args) {
        CaliperMain.main(DemoBenchmark.class, args);
    }

    @BeforeExperiment
    void setUp() throws Exception {
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        Move move = new Move(7, 1, 5, 2, Chessboard.getPiece(5, 2), Chessboard.getPiece(7, 1));
        Chessboard.makeMove(move);
        Chessboard.changePlayer();
    }

    @Benchmark
    void timeSearch1(int reps) {
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening();
        for (int i = 0; i < reps; i++) {
            searchAlgorithm.search(new Move());
        }
    }

    @Benchmark
    void timeSearch2(int reps) {
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening();
        for (int i = 0; i < reps; i++) {

        }
    }

}
