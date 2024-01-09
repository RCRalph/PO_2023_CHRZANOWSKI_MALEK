package agh.ics.oop.model.map;

import agh.ics.oop.model.Pose;
import agh.ics.oop.model.RandomPositionGenerator;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Tunnel;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.element.gene.Gene;

import java.util.*;

public class UndergroundTunnelsWorldMap extends AbstractWorldMap {
    private final Map<Vector2D, Tunnel> tunnels = new HashMap<>();

    public UndergroundTunnelsWorldMap(Boundary boundary, int tunnelCount, PlantGrowthIndicator plantGrowthIndicator) {
        super(boundary, plantGrowthIndicator);
        this.generateTunnels(tunnelCount);
    }

    private void generateTunnels(int tunnelCount) {
        List<Vector2D> tunnelEntrances = RandomPositionGenerator.getPositions(
            this.getCurrentBounds(),
            tunnelCount * 2
        );

        for (int i = 0; i < tunnelCount; i++) {
            this.tunnels.put(
                tunnelEntrances.get(i * 2),
                new Tunnel(tunnelEntrances.get(i * 2), tunnelEntrances.get(i * 2 + 1))
            );

            this.tunnels.put(
                tunnelEntrances.get(i * 2 + 1),
                new Tunnel(tunnelEntrances.get(i * 2 + 1), tunnelEntrances.get(i * 2))
            );
        }
    }

    @Override
    protected Set<Vector2D> invalidPlantPositions() {
        Set<Vector2D> result = new HashSet<>(super.invalidPlantPositions());
        result.addAll(this.tunnels.keySet());

        return result;
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return this.tunnels.containsKey(position) || super.isOccupied(position);
    }

    @Override
    public List<WorldElement> objectsAt(Vector2D position) {
        List<WorldElement> result = new ArrayList<>();

        if (this.tunnels.containsKey(position)) {
            result.add(this.tunnels.get(position));
        }

        result.addAll(super.objectsAt(position));

        return result;
    }

    @Override
    public Pose indicatePose(Pose currentPose) {
        Vector2D positionAfterMove = currentPose.position().add(currentPose.orientation().toSquareVector());

        Pose resultPose;
        if (this.tunnels.containsKey(positionAfterMove)) {
            resultPose = new Pose(
                this.tunnels.get(positionAfterMove).getExit(),
                currentPose.orientation()
            );
        } else if (!this.boundary.isInside(positionAfterMove)) {
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

        return resultPose;
    }
}
