package agh.ics.oop.model.element;

import agh.ics.oop.model.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plant implements WorldElement {
    private static final Image IMAGE = new Image("grass.png");

    private final Vector2D position;

    private final ImageView imageView = new ImageView(IMAGE);

    public Plant(Vector2D position) {
        this.position = position;
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

    @Override
    public void setImageViewSize(double cellSize) {
        this.imageView.setFitWidth(cellSize);
        this.imageView.setFitHeight(cellSize);
    }
}
