package agh.ics.oop.model.element;

import agh.ics.oop.model.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tunnel implements WorldElement {
    private static final Image IMAGE = new Image("tunnel.png");

    private final Vector2D position;

    private final Vector2D exit;

    private final ImageView imageView;

    public Tunnel(Vector2D position, Vector2D exit) {
        this.position = position;
        this.exit = exit;

        this.imageView = new ImageView(IMAGE);
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

    public Vector2D getExit() {
        return this.exit;
    }
}
