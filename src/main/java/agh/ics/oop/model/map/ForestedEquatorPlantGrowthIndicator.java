package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Plant;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class ForestedEquatorPlantGrowthIndicator implements PlantGrowthIndicator{
    private final Random random = new Random();

    private final List<Vector2D> desirablePositions;

    private final List<Vector2D> undesirablePositions;

    public ForestedEquatorPlantGrowthIndicator(Boundary boundary) {
        long doubledEquatorYCoordinate = (long)boundary.lowerLeftCorner().y() + boundary.upperRightCorner().y(),
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

    public Collection<Plant> getPlants(Set<Vector2D> occupiedPositions, int plantCount) {
        List<Vector2D> availableDesirablePositions = new ArrayList<>(this.desirablePositions);
        List<Vector2D> availableUndesirablePositions = new ArrayList<>(this.undesirablePositions);

        availableDesirablePositions.removeAll(occupiedPositions);
        availableUndesirablePositions.removeAll(occupiedPositions);

        List<Plant> result = new ArrayList<>();
        for (int i = 0; i < plantCount; i++) {
            if (availableDesirablePositions.isEmpty() && availableUndesirablePositions.isEmpty()) break;

            Vector2D drawnPosition;
            if (availableDesirablePositions.isEmpty()) {
                drawnPosition = availableUndesirablePositions.remove(
                    this.random.nextInt(availableUndesirablePositions.size())
                );
            } else if (availableUndesirablePositions.isEmpty()){
                drawnPosition = availableDesirablePositions.remove(
                    this.random.nextInt(availableDesirablePositions.size())
                );
            } else {
                drawnPosition = this.random.nextBoolean() ? // 80% probability
                    availableDesirablePositions.remove(this.random.nextInt(availableDesirablePositions.size())) :
                    availableUndesirablePositions.remove(this.random.nextInt(availableUndesirablePositions.size()));
            }

            result.add(new Plant(drawnPosition));
        }

        return Collections.unmodifiableCollection(result);
    }

    @Override
    public Collection<Vector2D> getDesirablePositions() {
        return this.desirablePositions;
    }
}
