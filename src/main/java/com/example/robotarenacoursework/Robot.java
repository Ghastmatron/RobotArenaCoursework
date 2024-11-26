package com.example.robotarenacoursework;

public class Robot {
    //keeping basic for now, will add more as needed
    //robots will need
    //a name, speed, position, sensors, and a direction(in degrees)

    private String name;
    private int speed;
    private int XPosition;
    private int YPosition;
    private int sensors;//unsure what data type it should be, perhaps its own class?
    private int direction;

    public Robot(String name, int speed, int XPosition, int YPosition, int sensors, int direction){
        this.name = name;
        this.speed = speed;
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.sensors = sensors;
        this.direction = direction;
    }
    //creating a list of getters for other classes to access the robot's data
    public String getName(){
        return name;
    }
    public int getSpeed(){
        return speed;
    }
    public int getXPos(){
        return XPosition;
    }
    public int getYPos(){
        return YPosition;
    }
    public int getDirection(){
        return direction;
    }

    //main method for testing
    public static void main(String[] args){
        Robot robot = new Robot("Robot1", 5, 0, 0, 3, 0);
        System.out.println(robot.getName());
        System.out.println(robot.getSpeed());
        System.out.println(robot.getXPos());
        System.out.println(robot.getYPos());
        System.out.println(robot.getDirection());
    }
}
