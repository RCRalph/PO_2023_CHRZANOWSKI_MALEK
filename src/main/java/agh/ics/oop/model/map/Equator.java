package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Plant;
import java.util.*;
import static java.lang.Math.abs;

public class Equator implements PlantGrowthIndicator{

    @Override
    public Collection<Plant> indicatePlantPositions(Boundary mapBoundary, Set<Vector2D> occupiedPositions, int plantCount) {

        List<Vector2D> preferredPositions = getPreferredPositions(mapBoundary);
        List<Vector2D> notPreferredPositions = getNotPreferredPositions(preferredPositions);

        ArrayList<Plant> plants = new ArrayList<>();

        Random random = new Random();
        for(int i=0; i<plantCount;i++){

            int draw = random.nextInt(100);
            Vector2D drawnPosition;

            if (draw < 80){
                drawnPosition = preferredPositions.remove(0);
            } else {
                drawnPosition = notPreferredPositions.remove(0);
            }

            if (!occupiedPositions.contains(drawnPosition)){
                plants.add(new Plant(drawnPosition));
            }
        }

        return  Collections.unmodifiableList(plants);
    }

    private List<Vector2D> getPreferredPositions(Boundary mapBoundary){
        float equatorLine  = (float) (mapBoundary.lowerLeftCorner().y() + mapBoundary.lowerLeftCorner().y()) /2;

        return mapBoundary.allPossiblePositions().stream()
                .sorted((pos1,pos2)->Float.compare(abs(pos1.y()-equatorLine),abs(pos2.y()-equatorLine)))
                .limit((long) (0.2*mapBoundary.allPossiblePositions().size()))
                .toList();
    }

    private List<Vector2D> getNotPreferredPositions(List<Vector2D> preferredPositions){
        return preferredPositions.stream()
                .filter(position -> !preferredPositions.contains(position))
                .toList();
    }
}
