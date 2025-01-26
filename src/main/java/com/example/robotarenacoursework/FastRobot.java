package com.example.robotarenacoursework;

public class FastRobot extends MoveableRobot {
    public FastRobot(String name, double speed, double maxSpeed, double minSpeed, double acceleration, double xPos, double yPos, int sensors, double direction, Arena arena, double xSize, double ySize) {
        super(name, speed, maxSpeed, minSpeed, acceleration, xPos, yPos, sensors, direction, arena, xSize, ySize);
        this.setSpeed(this.getSpeed() * 1.5); // Increase speed by 50%
        this.setMaxSpeed(this.getMaxSpeed() * 1.5); // Increase max speed by 50%
    }
    // Method to get the flame radius of the robot
    public double getFlameRadius(){
        return this.getXSize() * 0.5;// Flame radius is proportional to half the width of the robot
    }
}