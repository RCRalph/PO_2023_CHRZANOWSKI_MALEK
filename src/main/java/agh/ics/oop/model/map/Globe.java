package agh.ics.oop.model.map;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Pose;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.gene.Gene;

public class Globe extends AbstractWorldMap{

    public Globe(
            int mapWidth,
            int mapHeight,
            PlantGrowthIndicator plantGrowthIndicator
    ) {
        super(mapWidth, mapHeight, plantGrowthIndicator);
    }


    @Override
    public Pose indicatePose(Pose currentPose) {
        Vector2D positionAfterMove = currentPose.position().add(currentPose.orientation().toSquareVector());

        Vector2D lowerLeft = super.boundary.lowerLeftCorner();
        Vector2D lowerRight = new Vector2D(super.boundary.upperRightCorner().x(), super.boundary.lowerLeftCorner().y());
        Vector2D upperRight = super.boundary.upperRightCorner();
        Vector2D upperLeft = new Vector2D(super.boundary.lowerLeftCorner().x(), super.boundary.upperRightCorner().y());

        Pose resultPose;

        if (positionAfterMove.equals(lowerLeft.add(MapDirection.SOUTH_WEST.toSquareVector()))){
            resultPose = new Pose(
                    super.boundary.upperRightCorner(),
                    currentPose.orientation()
            );
        } else if (positionAfterMove.equals(upperRight.add(MapDirection.NORTH_EAST.toSquareVector()))){
            resultPose = new Pose(
                    super.boundary.upperRightCorner(),
                    currentPose.orientation()
            );
        } else if (positionAfterMove.equals(upperLeft.add(MapDirection.NORTH_WEST.toSquareVector()))){
            resultPose = new Pose(
                    lowerRight,
                    currentPose.orientation()
            );
        } else if (positionAfterMove.equals(lowerRight.add(MapDirection.SOUTH_EAST.toSquareVector()))) {
            resultPose = new Pose(
                    upperLeft,
                    currentPose.orientation()
            );
        } else if (positionAfterMove.x() > upperRight.x()) {
            resultPose = new Pose(
                    new Vector2D(lowerLeft.x(), positionAfterMove.y()),
                    currentPose.orientation()
            );
        } else if (positionAfterMove.x() < lowerLeft.x()){
            resultPose = new Pose(
                    new Vector2D(upperRight.x(), positionAfterMove.y()),
                    currentPose.orientation()
            );
        } else if (positionAfterMove.y() > upperRight.y() || positionAfterMove.y() < lowerLeft.y()) {
            resultPose = new Pose(
                    currentPose.position(),
                    currentPose.orientation().rotateByGene(Gene.BACKWARD)
            );
        } else {
            resultPose = new Pose(
                    positionAfterMove,
                    currentPose.orientation()
            );
        }

        return  resultPose;
    }
}

