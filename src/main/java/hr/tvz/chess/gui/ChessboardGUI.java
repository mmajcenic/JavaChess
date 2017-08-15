package hr.tvz.chess.gui;

import hr.tvz.chess.Main;
import hr.tvz.chess.engine.Castling;
import hr.tvz.chess.engine.Chessboard;
import hr.tvz.chess.engine.Move;
import hr.tvz.chess.engine.algorithms.SearchAlgorithm;
import hr.tvz.chess.pieces.common.AbstractChessPiece;
import hr.tvz.chess.pieces.common.Color;
import hr.tvz.chess.pieces.common.PieceType;
import hr.tvz.chess.pieces.impl.Queen;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marko on 12.8.2016..
 */
public class ChessboardGUI extends Pane {

    public int squareSize = 70;

    private static ChessboardGUI instance;

    private long humanTime = System.currentTimeMillis();

    public ChessboardGUI() {

        instance = this;
        instance.setId("chessboard");
        instance.setMinHeight(630);
        instance.setMinWidth(660);
        instance.getStyleClass().add("mainChessboard");
        List<String> labels = new ArrayList<>();
        labels.add("a\nc0");
        labels.add("b\nc1");
        labels.add("c\nc2");
        labels.add("d\nc3");
        labels.add("e\nc4");
        labels.add("f\nc5");
        labels.add("g\nc6");
        labels.add("h\nc7");
        addLabel(75, 10, labels, true);
        addLabel(75, 620, labels, true);
        labels.clear();
        labels.add("8\nr0");
        labels.add("7\nr1");
        labels.add("6\nr2");
        labels.add("5\nr3");
        labels.add("4\nr4");
        labels.add("3\nr5");
        labels.add("2\nr6");
        labels.add("1\nr7");
        addLabel(20, 65, labels, false);
        addLabel(630, 65, labels, false);

        final ToggleGroup toggleGroup = new ToggleGroup();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ToggleButton square = new ToggleButton();
                square.setLayoutX(j * squareSize + 50);
                square.setLayoutY(i * squareSize + 50);
                square.setMinSize(squareSize, squareSize);
                square.setMaxSize(squareSize, squareSize);
                if ((i + j) % 2 == 0) {
                    square.getStyleClass().add("lightSquare");
                } else {
                    square.getStyleClass().add("darkSquare");
                }
                square.setId(String.valueOf(8 * i + j));
                square.setToggleGroup(toggleGroup);
                this.getChildren().add(square);
            }
        }
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue != null && oldValue.getUserData() != null && !((AbstractChessPiece) oldValue.getUserData()).isEmpty()) {

                AbstractChessPiece oldMoveData = (AbstractChessPiece) oldValue.getUserData();
                AbstractChessPiece newMoveData = (AbstractChessPiece) newValue.getUserData();

                int oldMovePosition = oldMoveData.getPosition();
                int newMovePosition = newMoveData.getPosition();

                newValue.setSelected(false);
                Move lastMove = Chessboard.getLastMove();
                Move move = null;
                if (oldMovePosition / 8 == 1 && newMovePosition / 8 == 0 && oldMoveData.isCurrentPlayerPieceType(PieceType.PAWN)) {
                    move = new Move(oldMovePosition % 8, newMovePosition % 8, newMoveData, getPromotion(oldMoveData), oldMoveData);
                } else if (lastMove != null && lastMove.getPieceThatMoves().getType().equals(PieceType.PAWN) &&
                        Math.abs(lastMove.getNewPositionRow()
                                - lastMove.getOldPositionRow()) == 2 &&
                        Math.abs(oldMovePosition % 8 - lastMove.getNewPositionColumn()) == 1 &&
                        oldMovePosition / 8 == lastMove.getNewPositionRow()) {
                    move = new Move(hr.tvz.chess.engine.Chessboard.getPiece(oldMovePosition / 8, newMovePosition % 8),
                            oldMovePosition / 8, oldMovePosition % 8, newMovePosition / 8, newMovePosition % 8, oldMoveData);
                } else if (oldMoveData.isCurrentPlayerPieceType(PieceType.KING) && Math.abs(oldMovePosition % 8 - newMovePosition % 8) == 2) {
                    if (oldMoveData.getColor().equals(Color.WHITE) && (oldMovePosition % 8 - newMovePosition % 8) == -2) {
                        move = new Move(Castling.WHITE_KING_SIDE, oldMoveData);
                    } else if (oldMoveData.getColor().equals(Color.WHITE) && (oldMovePosition % 8 - newMovePosition % 8) == 2) {
                        move = new Move(Castling.WHITE_QUEEN_SIDE, oldMoveData);
                    }
                    if (oldMoveData.getColor().equals(Color.BLACK) && (oldMovePosition % 8 - newMovePosition % 8) == -2) {
                        move = new Move(Castling.BLACK_KING_SIDE, oldMoveData);
                    } else if (oldMoveData.getColor().equals(Color.BLACK) && (oldMovePosition % 8 - newMovePosition % 8) == 2) {
                        move = new Move(Castling.BLACK_QUEEN_SIDE, oldMoveData);
                    }
                } else {
                    move = new Move(oldMovePosition / 8, oldMovePosition % 8, newMovePosition / 8, newMovePosition % 8, newMoveData, oldMoveData);
                }

                if (move != null && hr.tvz.chess.engine.Chessboard.generatePossibleMoves().contains(move)) {

                    Chessboard.makeMove(move);

                    drawChesspieces();

                    UserControls userControls = (UserControls) this.getScene().lookup("#controls");
                    String notation = String.format("r%dc%d -> r%dc%d",
                            move.getOldPositionRow(),
                            move.getOldPositionColumn(),
                            move.getNewPositionRow(),
                            move.getNewPositionColumn());
                    userControls.addMoveToHistory(new MoveHistoryEntry(notation, userControls.getAlgorithm(), System.currentTimeMillis() - humanTime));
                    Chessboard.changePlayer();

                    if (!Main.isPvp()) {
                        makeAiMove(move);
                    } else {
                        setHumanTime(System.currentTimeMillis());
                    }
                }


            } else if (newValue != null && newValue.getUserData() != null && ((AbstractChessPiece) newValue.getUserData()).isEmpty()) {
                newValue.setSelected(false);
            }

        });

    }

    public void makeAiMove(Move move) {
        Platform.runLater(() -> {
            long now = System.currentTimeMillis();
            Move move1 = Main.getSearchAlgorithm().search(move);
            long elapsed = System.currentTimeMillis() - now;
            UserControls userControls = (UserControls) this.getScene().lookup("#controls");
            List<Move> moves = Chessboard.generatePossibleMoves();
            if(!moves.contains(move1)) {
                move1 = SearchAlgorithm.sortMoves(moves).get(0);
            }
            String notation = String.format("r%dc%d -> r%dc%d",
                    move1.getOldPositionRow(),
                    move1.getOldPositionColumn(),
                    move1.getNewPositionRow(),
                    move1.getNewPositionColumn());
            userControls.addMoveToHistory(new MoveHistoryEntry(notation, userControls.getAlgorithm(), elapsed));
            SearchAlgorithm.clearTranspositionTable();
            SearchAlgorithm.clearRootTable();
            SearchAlgorithm.clearSortedRootMoves();
            Chessboard.makeMove(move1);
            Chessboard.changePlayer();
            drawChesspieces();
            humanTime = System.currentTimeMillis();
        });
    }

    private AbstractChessPiece getPromotion(AbstractChessPiece chessPiece) {
        Queen queen = new Queen(hr.tvz.chess.engine.Chessboard.getCurrentPlayer().getColor());
        if (chessPiece.getImage().equals(ImageFile.BLACK_PAWN)) {
            queen.setImage(ImageFile.BLACK_QUEEN);
        } else {
            queen.setImage(ImageFile.WHITE_QUEEN);
        }
        return queen;
    }

    public void drawChesspieces() {

        for (int i = 0; i < 64; i++) {
            drawSquare(i);
        }
        if (!Main.isWhiteIsHuman()) {
            instance.setRotate(180);
            instance.getChildren().forEach(node -> {
                if (node instanceof Label) {
                    node.setRotate(180);
                }
            });
        } else {
            instance.setRotate(0);
            instance.getChildren().forEach(node -> {
                if (node instanceof Label) {
                    node.setRotate(0);
                }
            });
        }
    }

    private void addLabel(long xLabel, long yLabel, List<String> labels, boolean movesHorizontally) {
        Label label;
        for (String labelString : labels) {
            label = new Label(labelString);
            label.setStyle("-fx-font-weight: bold");
            label.setLayoutX(xLabel);
            label.setLayoutY(yLabel);
            this.getChildren().add(label);
            if (movesHorizontally) {
                xLabel += 70;
            } else {
                yLabel += 70;
            }
        }
    }


    private void drawSquare(int i) {
        ToggleButton toggleButton = (ToggleButton) instance.getScene().lookup(String.format("#%d", i));
        if (!hr.tvz.chess.engine.Chessboard.isEmptyPiece(i / 8, i % 8)) {
            toggleButton.setGraphic(new ChessPieceImage(hr.tvz.chess.engine.Chessboard.getPiece(i / 8, i % 8).getImage()).getImageView());
        } else {
            toggleButton.setGraphic(null);
        }
        hr.tvz.chess.engine.Chessboard.getPiece(i / 8, i % 8).setPosition(i);
        toggleButton.setUserData(hr.tvz.chess.engine.Chessboard.getPiece(i / 8, i % 8));
        if (!Main.isWhiteIsHuman()) {
            toggleButton.setRotate(180);
        } else {
            toggleButton.setRotate(0);
        }
    }

    public long getHumanTime() {
        return humanTime;
    }

    public void setHumanTime(long humanTime) {
        this.humanTime = humanTime;
    }
}
