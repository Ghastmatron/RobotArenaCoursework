package com.example.robotarenacoursework;

public class Obstacle {
    private double xPos;
    private double yPos;
    private String type;

    public Obstacle(double xPos, double yPos, String type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
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
}