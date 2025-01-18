// BounceObstacle.java
package com.example.robotarenacoursework;

public class BounceObstacle extends Obstacle {
    public BounceObstacle(double xPos, double yPos, double xSize, double ySize) {
        super(xPos, yPos, xSize, ySize);
    }

    @Override
    public void handleCollision(MoveableRobot robot) {
        robot.setDirection(robot.getDirection() + 180 + Math.random() * 20 - 10);
    }
}