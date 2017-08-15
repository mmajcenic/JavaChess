package hr.tvz.chess;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.*;

@VmOptions("-XX:-TieredCompilation")
public class ChessBenchmarkRuntime {

    public static void main(String[] args) {
        CaliperMain.main(ChessBenchmarkRuntime.class, args);
    }

    @BeforeExperiment
    void setUp() throws Exception {
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
    }

    @Benchmark
    void minimax(long reps) {
        SearchAlgorithm searchAlgorithm = new Minimax();
        for (int i = 0; i < reps; i++) {
            searchAlgorithm.search(new Move());
            SearchAlgorithm.clearTranspositionTable();
        }
    }

    @Benchmark
    void alphabeta(long reps) {
        SearchAlgorithm searchAlgorithm = new AlphaBeta();
        for (int i = 0; i < reps; i++) {
            searchAlgorithm.search(new Move());
            SearchAlgorithm.clearTranspositionTable();
        }
    }

    @Benchmark
    void transposition(long reps) {
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithTransposition();
        for (int i = 0; i < reps; i++) {
            searchAlgorithm.search(new Move());
            SearchAlgorithm.clearTranspositionTable();
        }
    }

    @Benchmark
    void iterativeDeepening(long reps) {
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening(6000);
        for (int i = 0; i < reps; i++) {
            searchAlgorithm.search(new Move());
            SearchAlgorithm.clearTranspositionTable();
        }
    }

}
