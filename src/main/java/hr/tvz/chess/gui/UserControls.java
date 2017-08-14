package hr.tvz.chess.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class UserControls extends Pane {
    private TableView table = new TableView();

    public UserControls() {
        this.setMinWidth(400);

        Label label = new Label("Algorithm");
        label.setStyle("-fx-font-weight: bold");
        label.setLayoutX(15);
        label.setLayoutY(15);
        this.getChildren().add(label);

        final ToggleGroup algorithmToggle = new ToggleGroup();

        RadioButton minimax = new RadioButton("Minimax");
        minimax.setLayoutY(40);
        minimax.setLayoutX(15);
        minimax.setToggleGroup(algorithmToggle);

        RadioButton alphabeta = new RadioButton("Alpha-beta pruning");
        alphabeta.setLayoutY(60);
        alphabeta.setLayoutX(15);
        alphabeta.setToggleGroup(algorithmToggle);

        RadioButton transposition = new RadioButton("Transposition table");
        transposition.setLayoutY(80);
        transposition.setLayoutX(15);
        transposition.setToggleGroup(algorithmToggle);

        RadioButton iterativeDeepening = new RadioButton("Iterative deepening");
        iterativeDeepening.setLayoutY(100);
        iterativeDeepening.setLayoutX(15);
        iterativeDeepening.setToggleGroup(algorithmToggle);
        iterativeDeepening.setSelected(true);

        this.getChildren().add(minimax);
        this.getChildren().add(alphabeta);
        this.getChildren().add(transposition);
        this.getChildren().add(iterativeDeepening);

        final ToggleGroup playerToggle = new ToggleGroup();

        Label playerLabel = new Label("Players");
        playerLabel.setStyle("-fx-font-weight: bold");
        playerLabel.setLayoutX(15);
        playerLabel.setLayoutY(125);
        this.getChildren().add(playerLabel);

        RadioButton white = new RadioButton("Play as white player");
        white.setLayoutY(150);
        white.setLayoutX(15);
        white.setSelected(true);
        white.setToggleGroup(playerToggle);

        RadioButton black = new RadioButton("Play as black player");
        black.setLayoutY(170);
        black.setLayoutX(15);
        black.setToggleGroup(playerToggle);

        RadioButton pvp = new RadioButton("Player versus player");
        pvp.setLayoutY(190);
        pvp.setLayoutX(15);
        pvp.setToggleGroup(playerToggle);

        this.getChildren().add(white);
        this.getChildren().add(black);
        this.getChildren().add(pvp);

        Label historyLabel = new Label("Move history");
        historyLabel.setStyle("-fx-font-weight: bold");
        historyLabel.setLayoutX(150);
        historyLabel.setLayoutY(220);
        this.getChildren().add(historyLabel);

        TableView table = new TableView();
        table.setLayoutY(240);
        table.setLayoutX(75);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn notation = new TableColumn("Notation");
        notation.setCellValueFactory(
                new PropertyValueFactory<MoveHistoryEntry, String>("notation"));
        TableColumn algorithm = new TableColumn("Algorithm");
        algorithm.setCellValueFactory(
                new PropertyValueFactory<MoveHistoryEntry, String>("algorithm"));
        TableColumn duration = new TableColumn("Duration");
        duration.setCellValueFactory(
                new PropertyValueFactory<MoveHistoryEntry, Long>("duration"));
        table.setSelectionModel(null);

        ObservableList<MoveHistoryEntry> data =
                FXCollections.observableArrayList(
                        new MoveHistoryEntry("r1c1", "Alphabeta", 1000)
                );
        table.setItems(data);

        table.getColumns().addAll(notation, algorithm, duration);

        this.getChildren().add(table);

        Button newGame = new Button("New game");
        newGame.setLayoutX(250);
        newGame.setLayoutY(20);
        newGame.setOnAction(e -> Platform.exit());
        newGame.getStyleClass().add("lightSquare");
        this.getChildren().add(newGame);

    }
}
