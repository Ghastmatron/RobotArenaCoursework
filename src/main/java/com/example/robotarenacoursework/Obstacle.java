package com.example.robotarenacoursework;

public class Obstacle {
    private double xPos;
    private double yPos;
    private String type;
    private double xSize;
    private double ySize;

    public Obstacle(double xPos, double yPos, String type, double xSize, double ySize) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public String getType() {
        return type;
    }

    public double getXSize() {
        return xSize;
    }

    public double getYSize() {
        return ySize;
    }

}