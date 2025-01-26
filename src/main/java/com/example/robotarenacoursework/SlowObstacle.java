// SlowObstacle.java
package com.example.robotarenacoursework;

/*
    * This class is used to create a SlowObstacle object
    * It extends the Obstacle class and overrides the handleCollision method
 */

public class SlowObstacle extends Obstacle {
    public SlowObstacle(double xPos, double yPos, double xSize, double ySize) {
        super(xPos, yPos, xSize, ySize);
    }

    // Return the type of the obstacle
    public String getType() {
        return "SlowObstacle";
    }

    @Override
    public void handleCollision(MoveableRobot robot) {
        robot.setSpeed(robot.getSpeed() * 0.5);
    }
}