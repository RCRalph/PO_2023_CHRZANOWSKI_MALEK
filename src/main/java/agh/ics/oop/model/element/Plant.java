package agh.ics.oop.model.element;

import agh.ics.oop.model.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plant implements WorldElement {
    private final Vector2D position;

    private final ImageView imageView;

    public Plant(Vector2D position) {
        this.position = position;

        this.imageView = new ImageView(new Image("images/grass.jpg"));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
    }

    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }
}
