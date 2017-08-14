package hr.tvz.chess;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithIterativeDeepening;
import hr.tvz.chess.engine.algorithms.SearchAlgorithm;
import hr.tvz.chess.gui.ChessboardInterface;
import hr.tvz.chess.gui.DialogHandler;
import hr.tvz.chess.pieces.impl.EmptySquare;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static boolean whiteIsHuman = true;
    public static boolean pvp = false;
    private static SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening();

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        ChessboardInterface chessboardInterface = new ChessboardInterface();

        root.setLeft(chessboardInterface);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("application.css");
        primaryStage.setTitle("JavaFX Chess");
        primaryStage.show();
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());

        DialogHandler.showChooseSideDialog();
        DialogHandler.showPickAlgorithmDialog();

        if (whiteIsHuman) {
            chessboardInterface.drawChesspieces();
        } else {
            chessboardInterface.makeAiMove(new Move());
        }

    }

    public static boolean isWhiteIsHuman() {
        return whiteIsHuman;
    }

    public static void setWhiteIsHuman(boolean whiteIsHuman) {
        Main.whiteIsHuman = whiteIsHuman;
    }

    public static SearchAlgorithm getSearchAlgorithm() {
        return searchAlgorithm;
    }

    public static void setSearchAlgorithm(SearchAlgorithm searchAlgorithm) {
        Main.searchAlgorithm = searchAlgorithm;
    }

    public static void main(String[] args) {
        SvgImageLoaderFactory.install();
        launch(args);
    }

}