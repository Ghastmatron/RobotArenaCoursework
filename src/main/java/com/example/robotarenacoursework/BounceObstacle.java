// BounceObstacle.java
package com.example.robotarenacoursework;

/*
 * This class is used to create a BounceObstacle object
 * It extends the Obstacle class and overrides the handleCollision method
 * It is used to handle collisions between the robot and the BounceObstacle
 */


public class BounceObstacle extends Obstacle {
    public BounceObstacle(double xPos, double yPos, double xSize, double ySize) {
        super(xPos, yPos, xSize, ySize);
    }

    // Return the type of the obstacle
    public String getType() {
        return "BounceObstacle";
    }

    @Override // Handle collision with the robot
    public void handleCollision(MoveableRobot robot) { // Bounce the robot off the obstacle
        robot.setDirection(robot.getDirection() + 180 + Math.random() * 20 - 10); // Rotate the robot
        robot.setSpeed(robot.getMaxSpeed()); // Set the speed to the maximum speed
    }
}