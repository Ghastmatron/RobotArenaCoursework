package com.example.robotarenacoursework;

public class Robot {
    //keeping basic for now, will add more as needed
    //robots will need
    //a name, speed, position, sensors, and a direction(in degrees)

    private String name;
    private int speed;
    private double XPosition;
    private double YPosition;
    private int sensors;//unsure what data type it should be, perhaps its own class?
    private int direction;

    public Robot(String name, int speed, double XPosition, double YPosition, int sensors, int direction) {
        this.name = name;
        this.speed = speed;
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.sensors = sensors;
        this.direction = direction;
    }

    //creating a list of getters for other classes to access the robot's data
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public double getXPos() {
        return XPosition;
    }

    public double getYPos() {
        return YPosition;
    }

    public int getDirection() {
        return direction;
    }

    public int getSensors() {
        return sensors;
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

    //main method for testing
    public static void main(String[] args) {
        Robot robot = new Robot("Robot1", 5, 0, 0, 3, 0);
        System.out.println(robot.getName());
        System.out.println(robot.getSpeed());
        System.out.println(robot.getXPos());
        System.out.println(robot.getYPos());
        System.out.println(robot.getDirection());
    }
}
