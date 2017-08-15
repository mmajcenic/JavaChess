package hr.tvz.chess.gui;

import hr.tvz.chess.Main;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class UserControls extends Pane {
    private TableView table = new TableView();

    private ObservableList<MoveHistoryEntry> data =
            FXCollections.observableArrayList();

    private String algorithm;
    private String player;
    private int depth;
    private TextField timeLimit = new TextField();
    private Label timelimitLabel = new Label("Time limit (ms) ");

    public UserControls() {
        this.setMinWidth(400);
        this.setId("controls");
        Label label = new Label("Algorithm");
        label.setStyle("-fx-font-weight: bold");
        label.setLayoutX(15);
        label.setLayoutY(15);
        this.getChildren().add(label);

        final ToggleGroup algorithmToggle = new ToggleGroup();
        algorithmToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            this.algorithm = (String) newValue.getUserData();
            setTimeLimitVisibility();
        });
        RadioButton minimax = new RadioButton("Minimax");
        minimax.setUserData("minimax");
        minimax.setLayoutY(40);
        minimax.setLayoutX(15);
        minimax.setToggleGroup(algorithmToggle);

        RadioButton alphabeta = new RadioButton("Alpha-beta pruning");
        alphabeta.setUserData("alphabeta");
        alphabeta.setLayoutY(60);
        alphabeta.setLayoutX(15);
        alphabeta.setToggleGroup(algorithmToggle);

        RadioButton transposition = new RadioButton("Transposition table");
        transposition.setUserData("transposition");
        transposition.setLayoutY(80);
        transposition.setLayoutX(15);
        transposition.setToggleGroup(algorithmToggle);

        RadioButton iterativeDeepening = new RadioButton("Iterative deepening");
        iterativeDeepening.setUserData("iterativeDeepening");
        iterativeDeepening.setLayoutY(100);
        iterativeDeepening.setLayoutX(15);
        iterativeDeepening.setToggleGroup(algorithmToggle);
        iterativeDeepening.setSelected(true);

        this.getChildren().add(minimax);
        this.getChildren().add(alphabeta);
        this.getChildren().add(transposition);
        this.getChildren().add(iterativeDeepening);

        final ToggleGroup playerToggle = new ToggleGroup();

        playerToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                this.player = (String) newValue.getUserData());

        Label playerLabel = new Label("Players");
        playerLabel.setStyle("-fx-font-weight: bold");
        playerLabel.setLayoutX(15);
        playerLabel.setLayoutY(125);
        this.getChildren().add(playerLabel);

        RadioButton white = new RadioButton("Play as white player");
        white.setUserData("white");
        white.setLayoutY(150);
        white.setLayoutX(15);
        white.setSelected(true);
        white.setToggleGroup(playerToggle);

        RadioButton black = new RadioButton("Play as black player");
        black.setUserData("black");
        black.setLayoutY(170);
        black.setLayoutX(15);
        black.setToggleGroup(playerToggle);

        RadioButton pvp = new RadioButton("Player versus player");
        pvp.setUserData("pvp");
        pvp.setLayoutY(190);
        pvp.setLayoutX(15);
        pvp.setToggleGroup(playerToggle);

        this.getChildren().add(white);
        this.getChildren().add(black);
        this.getChildren().add(pvp);

        Label historyLabel = new Label("Move history");
        historyLabel.setStyle("-fx-font-weight: bold");
        historyLabel.setLayoutX(150);
        historyLabel.setLayoutY(230);
        this.getChildren().add(historyLabel);

        TableView table = new TableView();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setLayoutY(250);
        TableColumn notation = new TableColumn("Notation");
        notation.setCellValueFactory(
                new PropertyValueFactory<MoveHistoryEntry, String>("notation"));
        notation.setMinWidth(90);

        TableColumn algorithm = new TableColumn("Algorithm");
        algorithm.setMinWidth(160);
        algorithm.setCellValueFactory(
                new PropertyValueFactory<MoveHistoryEntry, String>("algorithm"));

        TableColumn duration = new TableColumn("Duration (ms)");

        duration.setMinWidth(150);
        duration.setCellValueFactory(
                new PropertyValueFactory<MoveHistoryEntry, Long>("duration"));
        table.setSelectionModel(null);

        table.setItems(data);

        table.getColumns().addAll(notation, algorithm, duration);

        this.getChildren().add(table);

        Button newGame = new Button("New game");
        newGame.setLayoutX(250);
        newGame.setLayoutY(155);
        newGame.setOnAction(e -> initGame());
        newGame.getStyleClass().add("lightSquare");
        this.getChildren().add(newGame);

        Button clearHistory = new Button("Clear history");
        clearHistory.setLayoutX(250);
        clearHistory.setLayoutY(185);
        clearHistory.setOnAction(e -> {
            data.clear();
            table.setItems(data);
        });
        clearHistory.getStyleClass().add("lightSquare");
        this.getChildren().add(clearHistory);


        final ToggleGroup depthToggle = new ToggleGroup();
        depthToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            this.depth = (Integer) newValue.getUserData();
        });
        RadioButton zero = new RadioButton("0");
        zero.setUserData(0);
        zero.setLayoutY(40);
        zero.setLayoutX(300);
        zero.setToggleGroup(depthToggle);

        RadioButton two = new RadioButton("2");
        two.setUserData(2);
        two.setLayoutY(60);
        two.setLayoutX(300);
        two.setToggleGroup(depthToggle);

        RadioButton four = new RadioButton("4");
        four.setUserData(4);
        four.setLayoutY(80);
        four.setLayoutX(300);
        four.setToggleGroup(depthToggle);
        four.setSelected(true);

        RadioButton six = new RadioButton("6");
        six.setUserData(6);
        six.setLayoutY(100);
        six.setLayoutX(300);
        six.setToggleGroup(depthToggle);

        this.getChildren().add(zero);
        this.getChildren().add(two);
        this.getChildren().add(four);
        this.getChildren().add(six);

        Label depthLabel = new Label("Depth");
        depthLabel.setStyle("-fx-font-weight: bold");
        depthLabel.setLayoutX(300);
        depthLabel.setLayoutY(15);
        this.getChildren().add(depthLabel);

        timelimitLabel.setStyle("-fx-font-weight: bold");
        timelimitLabel.setLayoutX(160);
        timelimitLabel.setLayoutY(15);
        this.getChildren().add(timelimitLabel);
        timeLimit.setLayoutX(160);
        timeLimit.setLayoutY(40);
        timeLimit.setText("6000");
        timeLimit.setMaxWidth(100);
        this.getChildren().add(timeLimit);
        setTimeLimitVisibility();
    }

    private void setTimeLimitVisibility() {
        if (algorithm.equals("iterativeDeepening")) {
            timeLimit.setVisible(true);
            timelimitLabel.setVisible(true);
        } else {
            timeLimit.setVisible(false);
            timelimitLabel.setVisible(false);
        }
    }

    public void addMoveToHistory(MoveHistoryEntry moveHistoryEntry) {
        data.add(moveHistoryEntry);
        table.setItems(data);
    }

    private void initGame() {
        switch (algorithm) {
            case "minimax":
                Main.setSearchAlgorithm(new Minimax());
                break;
            case "alphabeta":
                Main.setSearchAlgorithm(new AlphaBeta());
                break;
            case "transposition":
                Main.setSearchAlgorithm(new AlphaBetaWithTransposition());
                break;
            case "iterativeDeepening":
                if (timeLimit.getText().matches("\\d+")) {
                    Main.setSearchAlgorithm(new AlphaBetaWithIterativeDeepening(Integer.valueOf(timeLimit.getText())));
                } else {
                    Main.setSearchAlgorithm(new AlphaBetaWithIterativeDeepening(6000));
                }
                break;
        }
        switch (player) {
            case "white":
                Main.setWhiteIsHuman(true);
                Main.setPvp(false);
                break;
            case "black":
                Main.setWhiteIsHuman(false);
                Main.setPvp(false);
                break;
            case "pvp":
                Main.setWhiteIsHuman(true);
                Main.setPvp(true);
                break;
        }
        SearchAlgorithm.setMaximumSearchDepth(depth);
        Chessboard.initalizeChessboard();
        ChessboardGUI chessboardGUI = (ChessboardGUI) this.getScene().lookup("#chessboard");
        Chessboard.setCurrentPlayer(Chessboard.getWhitePlayer());
        if (Main.isPvp()) {
            chessboardGUI.drawChesspieces();
            chessboardGUI.setHumanTime(System.currentTimeMillis());
        } else if (Main.isWhiteIsHuman()) {
            chessboardGUI.setHumanTime(System.currentTimeMillis());
            chessboardGUI.drawChesspieces();
        } else {
            chessboardGUI.makeAiMove(new Move());
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
