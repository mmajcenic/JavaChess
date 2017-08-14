package hr.tvz.chess.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Marko on 13.8.2016..
 */
public class ChessPieceImage {

    private ImageView imageView;

    public ChessPieceImage(ImageFile file) {
        int squareSize = 70;
        Image image = new Image(file.getFile());
        imageView = new ImageView();
        imageView.setFitHeight(squareSize - 5);
        imageView.setFitWidth(squareSize - 5);
        imageView.setImage(image);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
