// SpeedBoostObstacle.java
package com.example.robotarenacoursework;

/*
 * This class is used to create a SpeedBoostObstacle object
 * It extends the Obstacle class and overrides the handleCollision method
 * It is used to handle collisions between the robot and the SpeedBoostObstacle
 */

public class SpeedObstacle extends Obstacle {
    public SpeedObstacle(double xPos, double yPos, double xSize, double ySize) {
        super(xPos, yPos, xSize, ySize);
    }

    // Return the type of the obstacle
    public String getType() {
        return "SpeedObstacle";
    }

    @Override
    public void handleCollision(MoveableRobot robot) {
        robot.setSpeed(robot.getSpeed() * 1.5);
        if (robot.getSpeed() > robot.getMaxSpeed()) { // If the speed is greater than the maximum speed
            robot.setSpeed(robot.getMaxSpeed()); // Set the speed to the maximum speed
        }
    }
}