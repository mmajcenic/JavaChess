package hr.tvz.chess;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.*;

@VmOptions("-XX:-TieredCompilation")
public class ChessBenchmarkAllocation {

    public static void main(String[] args) {
        CaliperMain.main(ChessBenchmarkAllocation.class, args);
    }

    @BeforeExperiment
    void setUp() throws Exception {
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
    }

    @Benchmark
    void minimax() {
        SearchAlgorithm searchAlgorithm = new Minimax();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.clearTranspositionTable();
    }

    @Benchmark
    void alphabeta() {
        SearchAlgorithm searchAlgorithm = new AlphaBeta();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.clearTranspositionTable();

    }

    @Benchmark
    void transposition() {
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithTransposition();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.clearTranspositionTable();
    }

    @Benchmark
    void iterativeDeepening() {
        SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening();
        searchAlgorithm.search(new Move());
        SearchAlgorithm.clearTranspositionTable();
    }

}
