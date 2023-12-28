package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Plant;
import java.util.*;
import static java.lang.Math.abs;
import static java.lang.Math.round;

public class ForestedEquatorPlantGrowthIndicator implements PlantGrowthIndicator{
    private final int dailyPlantCount;

    private final Random random = new Random();

    private final List<Vector2D> desirablePositions;

    private final List<Vector2D> undesirablePositions;

    public ForestedEquatorPlantGrowthIndicator(Boundary boundary, int dailyPlantCount) {
        this.dailyPlantCount = dailyPlantCount;

        long doubledEquatorYCoordinate = boundary.lowerLeftCorner().y() + boundary.upperRightCorner().y(),
             desirablePositionCount = round(0.2 * boundary.allPossiblePositions().size());

        List<Vector2D> allPositions = boundary.allPossiblePositions();
        Collections.shuffle(allPositions);
        allPositions.sort(Comparator.comparingLong(item -> abs(doubledEquatorYCoordinate - 2L * item.y())));

        // Get best 20%
        this.desirablePositions = allPositions.stream()
            .limit(desirablePositionCount)
            .toList();

        // Get worst 80%
        this.undesirablePositions = allPositions.stream()
            .skip(desirablePositionCount)
            .toList();
    }

    @Override
    public Collection<Plant> getPlants(Set<Vector2D> occupiedPositions) {
        List<Vector2D> availableDesirablePositions = new LinkedList<>(this.desirablePositions);
        availableDesirablePositions.removeAll(occupiedPositions);

        List<Vector2D> availableUndesirablePositions = new LinkedList<>(this.undesirablePositions);
        availableUndesirablePositions.removeAll(occupiedPositions);

        List<Plant> result = new ArrayList<>();
        for (int i = 0; i < this.dailyPlantCount; i++) {
            if (availableDesirablePositions.isEmpty() && availableUndesirablePositions.isEmpty()) break;

            Vector2D drawnPosition;
            if (availableDesirablePositions.isEmpty()) {
                drawnPosition = availableUndesirablePositions.remove(0);
            } else if (availableUndesirablePositions.isEmpty()){
                drawnPosition = availableDesirablePositions.remove(0);
            } else {
                drawnPosition = this.random.nextInt(5) < 4 ? // 80% probability
                    availableDesirablePositions.remove(0) :
                    availableUndesirablePositions.remove(0);
            }

            result.add(new Plant(drawnPosition));
        }

        return Collections.unmodifiableCollection(result);
    }
}
