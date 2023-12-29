package agh.ics.oop.model.element;

import agh.ics.oop.model.Vector2D;
import javafx.scene.image.ImageView;

public interface WorldElement {
    boolean isAt(Vector2D position);

    Vector2D getPosition();

    ImageView getImageView();
}
