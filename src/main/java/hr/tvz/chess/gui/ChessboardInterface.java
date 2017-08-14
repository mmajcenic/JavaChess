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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * Created by Marko on 12.8.2016..
 */
public class ChessboardInterface extends Pane {

    public int squareSize = 70;

    private static ChessboardInterface instance;

    public ChessboardInterface() {

        instance = this;

        final ToggleGroup toggleGroup = new ToggleGroup();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ToggleButton square = new ToggleButton();
                square.setLayoutX(j * squareSize);
                square.setLayoutY(i * squareSize);
                square.setMinSize(squareSize, squareSize);
                square.setMaxSize(squareSize, squareSize);
                if ((i + j) % 2 == 0) {
                    square.getStyleClass().add("lightSquare");
                } else {
                    square.getStyleClass().add("darkSquare");
                }
                square.setId(String.valueOf(8 * i + j));
                square.setToggleGroup(toggleGroup);
                getChildren().add(square);
            }
        }
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue != null && !((AbstractChessPiece) oldValue.getUserData()).isEmpty()) {

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
                    move = new Move(Chessboard.getPiece(oldMovePosition / 8, newMovePosition % 8),
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

                if (move != null && Chessboard.generatePossibleMoves().contains(move)) {

                    Chessboard.makeMove(move);

                    drawChesspieces();

                    Chessboard.changePlayer();

                    if (!Main.pvp) {
                        makeAiMove(move);
                    }
                }


            } else if (newValue != null && ((AbstractChessPiece) newValue.getUserData()).isEmpty()) {
                newValue.setSelected(false);
            }

        });

    }

    public void makeAiMove(Move move) {
        Platform.runLater(() -> {
            Move move1 = Main.getSearchAlgorithm().search(move);
            SearchAlgorithm.clearTranspositionTable();
            SearchAlgorithm.clearRootTable();
            SearchAlgorithm.clearSortedRootMoves();
            Chessboard.makeMove(move1);
            Chessboard.changePlayer();
            drawChesspieces();
        });
    }

    private AbstractChessPiece getPromotion(AbstractChessPiece chessPiece) {
        Queen queen = new Queen(Chessboard.getCurrentPlayer().getColor());
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
        }
    }

    private void drawSquare(int i) {
        ToggleButton toggleButton = (ToggleButton) instance.getScene().lookup(String.format("#%d", i));
        if (!Chessboard.isEmptyPiece(i / 8, i % 8)) {
            toggleButton.setGraphic(new ChessPieceImage(Chessboard.getPiece(i / 8, i % 8).getImage()).getImageView());
        } else {
            toggleButton.setGraphic(null);
        }
        Chessboard.getPiece(i / 8, i % 8).setPosition(i);
        toggleButton.setUserData(Chessboard.getPiece(i / 8, i % 8));
        if (!Main.isWhiteIsHuman()) {
            toggleButton.setRotate(180);
        }
    }


}
