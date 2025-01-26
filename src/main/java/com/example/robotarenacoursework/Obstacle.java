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

    public abstract String getType();

    public void setPosition(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }

    // Method to check if a point is within the obstacle
    public boolean contains(double x, double y) {
        return x >= xPos && x <= xPos + xSize && y >= yPos && y <= yPos + ySize;
    }

    // Method to handle collision with a robot
    public abstract void handleCollision(MoveableRobot robot);
}