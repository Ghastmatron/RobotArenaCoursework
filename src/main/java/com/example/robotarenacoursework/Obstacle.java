package com.example.robotarenacoursework;

public abstract class Obstacle {
    private double xPos;
    private double yPos;
    private double xSize;
    private double ySize;

    public Obstacle(double xPos, double yPos, double xSize, double ySize) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public double getXSize() {
        return xSize;
    }

    public double getYSize() {
        return ySize;
    }

    public abstract void handleCollision(MoveableRobot robot);
}