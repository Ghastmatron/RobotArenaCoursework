// HeavyRobot.java
package com.example.robotarenacoursework;

public class HeavyRobot extends MoveableRobot {
    public HeavyRobot(String name, double speed, double maxSpeed, double minSpeed, double acceleration, double xPos, double yPos, int sensors, double direction, Arena arena, double xSize, double ySize) {
        super(name, speed, maxSpeed, minSpeed, acceleration, xPos, yPos, sensors, direction, arena, xSize, ySize);
        this.setSpeed(this.getSpeed() * 0.5); // Decrease speed by 50%
        this.setMaxSpeed(this.getMaxSpeed() * 0.5); // Decrease max speed by 50%
        this.setMinSpeed(this.getMinSpeed() * 0.5); // Decrease min speed by 50%
        this.setXSize(this.getXSize() * 1.5); // Increase size by 50%
        this.setYSize(this.getYSize() * 1.5); // Increase size by 50%
    }

    // Method to get the weight of the robot
    public double getWeight(){
        return this.getXSize() * this.getYSize(); // Weight is proportional to the area of the robot
    }
}