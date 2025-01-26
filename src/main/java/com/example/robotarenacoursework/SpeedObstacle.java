// SpeedBoostObstacle.java
package com.example.robotarenacoursework;

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
    }
}