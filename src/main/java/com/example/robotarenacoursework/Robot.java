package com.example.robotarenacoursework;

public class Robot {
    private String name;
    private double speed;
    private double XPosition;
    private double YPosition;
    private int sensors;
    private double direction;
    private double xSize, ySize;

    public Robot(String name, double speed, double XPosition, double YPosition, int sensors, double direction, double xSize, double ySize) {
        this.name = name;
        this.speed = speed;
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.sensors = sensors;
        this.direction = direction;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getXPos() {
        return XPosition;
    }

    public double getYPos() {
        return YPosition;
    }

    public double getDirection() {
        return direction;
    }

    public int getSensors() {
        return sensors;
    }

    public double getXSize() {
        return xSize;
    }

    public double getYSize() {
        return ySize;
    }

    public void setDirection(double direction) {
        this.direction = (int) direction;
    }

    public double setXPos(double XPosition) {
        return this.XPosition = XPosition;
    }

    public double setYPos(double YPosition) {
        return this.YPosition = YPosition;
    }

    public double setSpeed(double speed) {
        return this.speed = speed;
    }
}