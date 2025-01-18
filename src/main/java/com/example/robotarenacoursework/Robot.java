package com.example.robotarenacoursework;

public class Robot {
    private double MaxSpeed;
    private String name;
    private double speed;
    private double MinSpeed;
    private double Accelleration;
    private double XPosition;
    private double YPosition;
    private int sensors;
    private double direction;
    private double xSize, ySize;

    public Robot(String name, double speed, double MaxSpeed, double MinSpeed, double Acceleration, double XPosition, double YPosition, int sensors, double direction, double xSize, double ySize) {
        this.name = name;
        this.speed = speed;
        this.MaxSpeed = MaxSpeed;
        this.MinSpeed = MinSpeed;
        this.Accelleration = Acceleration;
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

    public double getMinSpeed() {
        return MinSpeed;
    }

    public double getAcceleration() {
        return Accelleration;
    }

    public double getMaxSpeed() {
        return MaxSpeed;
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

    public void setMaxSpeed(double MaxSpeed) {
        this.MaxSpeed = MaxSpeed;
    }

    public void setMinSpeed(double MinSpeed) {
        this.MinSpeed = MinSpeed;
    }

    public void setAcceleration(double Acceleration) {
        this.Accelleration = Acceleration;
    }
}