package agh.ics.oop.model;

public interface PoseIndicator {
    /**
     * Indicate the new position of an object after it moves in the specified MapDirection
     *
     * @param currentPose The current pose of the given object
     * @return New pose of the object
     */
    Pose indicatePose(Pose currentPose);
}