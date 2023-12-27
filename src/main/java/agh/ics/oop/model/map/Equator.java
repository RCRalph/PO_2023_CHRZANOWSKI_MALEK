package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Plant;
import java.util.*;
import static java.lang.Math.abs;

public class Equator implements PlantGrowthIndicator{

    private final List<Vector2D> preferredPositions;

    private final List<Vector2D> notPreferredPositions;

    public Equator(Boundary mapBoundary){
        this.preferredPositions = getPreferredPositions(mapBoundary);
        this.notPreferredPositions = mapBoundary.allPossiblePositions();
        this.notPreferredPositions.removeAll(preferredPositions);
    }


    @Override
    public Collection<Plant> indicatePlantPositions(Boundary mapBoundary, Set<Vector2D> occupiedPositions, int plantCount) {
        // could use removeAll method, but its return type is uncomfortable and could lead into more code
        List<Vector2D> availablePreferredPositions = getNotOccupiedPositions(this.preferredPositions, occupiedPositions);
        List<Vector2D> availableNotPreferredPositions = getNotOccupiedPositions(this.notPreferredPositions, occupiedPositions);

        ArrayList<Plant> plants = new ArrayList<>();

        Random r = new Random();
        int plantsGrown = 0;
        Vector2D drawnPosition;

        while (
                plantsGrown < plantCount && !availablePreferredPositions.isEmpty() && !availableNotPreferredPositions.isEmpty()
        ) {

            if (preferredPositions.isEmpty()){
                drawnPosition =  availableNotPreferredPositions.remove(0);

            } else if (notPreferredPositions.isEmpty()){
                drawnPosition =  availablePreferredPositions.remove(0);

            } else {
                drawnPosition = r.nextInt(100) < 80 ? availablePreferredPositions.remove(0) : availableNotPreferredPositions.remove(0);
            }

            plantsGrown++;
            plants.add(new Plant(drawnPosition));

        }

        return  Collections.unmodifiableList(plants);
    }

    private List<Vector2D> getPreferredPositions(Boundary mapBoundary){
        float equatorLine  = (float) (mapBoundary.lowerLeftCorner().y() + mapBoundary.upperRightCorner().y()) / 2;

        return mapBoundary.allPossiblePositions()
                .stream()
                .sorted((pos1,pos2)->Float.compare(abs(pos1.y() - equatorLine),abs(pos2.y() - equatorLine)))
                .limit( (long) (0.2 * mapBoundary.allPossiblePositions().size()))
                .toList();
    }

    private List<Vector2D> getNotOccupiedPositions(List<Vector2D> positions, Set<Vector2D> occupiedPositions){
        return positions
                .stream()
                .filter(position -> !occupiedPositions.contains(position))
                .toList();
    }
}
