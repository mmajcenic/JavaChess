package hr.tvz.chess.gui;

import hr.tvz.chess.Main;
import hr.tvz.chess.engine.algorithms.AlphaBeta;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithIterativeDeepening;
import hr.tvz.chess.engine.algorithms.AlphaBetaWithTransposition;
import hr.tvz.chess.engine.algorithms.Minimax;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by marko on 10/02/2017.
 */
public class DialogHandler {

    public static void showChooseSideDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Chess setup");
        alert.setHeaderText("Choose side");

        ButtonType white = new ButtonType("White");
        ButtonType black = new ButtonType("Black");

        alert.getButtonTypes().setAll(white, black);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == black) {
            Main.whiteIsHuman = false;
        }
    }

    public static void showPickAlgorithmDialog() {
        List<String> choices = new ArrayList<>();
        choices.add("Minimax");
        choices.add("Alpha-beta pruning");
        choices.add("Alpha-beta and transposition");
        choices.add("Alpha-beta and iterative deepening");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Alpha-beta pruning", choices);
        dialog.setTitle("Chess setup");
        dialog.setHeaderText("Choose algorithm for AI");
        dialog.setContentText("Algorithms:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(algorithm -> {
            if (algorithm.equals("Minimax")) {
                Main.setSearchAlgorithm(new Minimax());
            } else if (algorithm.equals("Alpha-beta pruning")) {
                Main.setSearchAlgorithm(new AlphaBeta());
            } else if (algorithm.equals("Alpha-beta and transposition")) {
                Main.setSearchAlgorithm(new AlphaBetaWithTransposition());
            }  else if (algorithm.equals("Alpha-beta and iterative deepening")) {
                Main.setSearchAlgorithm(new AlphaBetaWithIterativeDeepening());
            }
        });
    }

}
