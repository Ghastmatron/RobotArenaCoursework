// TeleportObstacle.java
package com.example.robotarenacoursework;

import java.util.Random;

public class TeleportObstacle extends Obstacle {
    private Arena arena;

    public TeleportObstacle(double xPos, double yPos, double xSize, double ySize, Arena arena) {
        super(xPos, yPos, xSize, ySize);
        this.arena = arena;
    }

    // Return the type of the obstacle
    public String getType() {
        return "TeleportObstacle";
    }

    @Override
    public void handleCollision(MoveableRobot robot) {
        Random random = new Random();
        robot.setXPos(random.nextInt((int) arena.getXSize()));
        robot.setYPos(random.nextInt((int) arena.getYSize()));
    }
}