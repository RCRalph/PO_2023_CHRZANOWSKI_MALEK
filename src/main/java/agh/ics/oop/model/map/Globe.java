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
        Vector2D upperRight = super.boundary.upperRightCorner();

        int resultCordX;
        int resultCordY = positionAfterMove.y();
        MapDirection resultOrientation = currentPose.orientation();

        if (positionAfterMove.x() > upperRight.x()) {
            resultCordX = lowerLeft.x();
        } else if (positionAfterMove.x() < lowerLeft.x()) {
            resultCordX = upperRight.x();
        } else {
            resultCordX = positionAfterMove.x();
        }

        if (positionAfterMove.y() > upperRight.y() || positionAfterMove.y() < lowerLeft.y()) {

            resultCordY = currentPose.position().y();
            resultOrientation = currentPose.orientation().rotateByGene(Gene.BACKWARD);

            // it is equivalent to: resultCordX == positionAfterMove.x(), but more straightforward
            if (resultCordX <= upperRight.x() && resultCordX >= lowerLeft.x()){
                resultCordX = currentPose.position().x();
            }

        }

        return  new Pose(new Vector2D(resultCordX, resultCordY), resultOrientation);
    }
}

