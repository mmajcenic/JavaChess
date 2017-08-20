package hr.tvz.chess;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithIterativeDeepening;
import hr.tvz.chess.engine.algorithms.SearchAlgorithm;
import hr.tvz.chess.gui.ChessboardGUI;
import hr.tvz.chess.gui.UserControls;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static boolean whiteIsHuman = true;
    private static boolean pvp = false;
    private static SearchAlgorithm searchAlgorithm = new AlphaBetaWithIterativeDeepening(6000);
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("JavaFX Chess");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> Platform.exit());
        closeButton.getStyleClass().add("lightSquare");
        Label label = new Label("JavaFX Chess");
        label.setTextFill(Color.web("#FFC864"));
        label.setStyle("-fx-font-weight: bold; -fx-padding: 4 0 0 0");
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 16, 10, 16));
        hBox.setSpacing(900);
        hBox.getChildren().add(label);
        hBox.getChildren().add(closeButton);
        hBox.getStyleClass().add("toolbar");
        BorderPane root = new BorderPane();
        root.setTop(hBox);
        root.getStyleClass().add("main");
        ChessboardGUI chessboardGUI = new ChessboardGUI();
        root.setLeft(chessboardGUI);
        UserControls userControls = new UserControls();
        root.setRight(userControls);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("application.css");

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.show();
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());

    }

    public static boolean isWhiteIsHuman() {
        return whiteIsHuman;
    }

    public static SearchAlgorithm getSearchAlgorithm() {
        return searchAlgorithm;
    }

    public static void setSearchAlgorithm(SearchAlgorithm searchAlgorithm) {
        Main.searchAlgorithm = searchAlgorithm;
    }

    public static void setWhiteIsHuman(boolean whiteIsHuman) {
        Main.whiteIsHuman = whiteIsHuman;
    }

    public static boolean isPvp() {
        return pvp;
    }

    public static void setPvp(boolean pvp) {
        Main.pvp = pvp;
    }

    public static void main(String[] args) {
        SvgImageLoaderFactory.install();
        launch(args);
    }

}