package com.example.robotarenacoursework;

import java.util.Random;

//subclass of Robot to handle movement
public class MoveableRobot extends Robot {
    private Arena arena;
    private boolean turningLeft = false;
    private boolean turningRight = false;
    private long turningStartTime = 0;
    private long turningDuration = 0;
    private Random random = new Random();

    //constructor
    public MoveableRobot(String name, int speed, int xPos, int yPos, int sensors, int direction, Arena arena){
        super(name, speed, xPos, yPos, sensors, direction);
        this.arena = arena;
    }

    //movement handling
    public void startTurningLeft(long duration){
        turningLeft = true;
        turningStartTime = System.currentTimeMillis(); //calculates current time that's passed in milliseconds
        turningDuration = duration; //sets the duration for turning
    }
    public void stopTurningLeft(){
        turningLeft = false;
    }
    public void startTurningRight(long duration){
        turningRight = true;
        turningStartTime = System.currentTimeMillis(); //calculates current time that's passed in milliseconds
        turningDuration = duration; //sets the duration for turning
    }
    public void stopTurningRight(){
        turningRight = false;
    }

    //update method to handle turning
    public void update(){
        long currentTime = System.currentTimeMillis(); //gets the current time
        if (turningLeft){
            long elapsedTime = currentTime - turningStartTime; //calculates elapsed time
            if (elapsedTime >= turningDuration) {
                stopTurningLeft(); //stops turning if duration is met
            } else {
                setDirection((getDirection() - (elapsedTime / 1000.0) * 30) % 360); //updates direction based on elapsed time
            }
        }
        if (turningRight){
            long elapsedTime = currentTime - turningStartTime; //calculates elapsed time
            if (elapsedTime >= turningDuration) {
                stopTurningRight(); //stops turning if duration is met
            } else {
                setDirection((getDirection() + (elapsedTime / 1000.0) * 30) % 360); //updates direction based on elapsed time
            }
        }
        arena.checkBounds(this); //checks if the robot is within bounds
    }

    //method to handle moving forward
    //moves dependent on direction
    public void moveForward() {
        setXPos(getXPos() + (int) (getSpeed() * Math.cos(Math.toRadians(getDirection()))));
        setYPos(getYPos() + (int) (getSpeed() * Math.sin(Math.toRadians(getDirection()))));
        arena.checkBounds(this); //checks if the robot is within bounds
    }
    //method to handle moving backward
    //moves dependent on direction
    public void moveBackward() {
        setXPos(getXPos() - (int) (getSpeed() * Math.cos(Math.toRadians(getDirection()))));
        setYPos(getYPos() - (int) (getSpeed() * Math.sin(Math.toRadians(getDirection()))));
        arena.checkBounds(this); //checks if the robot is within bounds
    }

    //method to handle moving randomly
    public void moveRandomly(){
        int action = random.nextInt(4); //generates a random action
        long duration = (random.nextInt(3) + 1) * 1000; //random duration between 1 and 3 seconds
        switch (action){
            case 0:
                moveForward();
                break;
            case 1:
                moveBackward();
                break;
            case 2:
                startTurningLeft(duration);
                break;
            case 3:
                startTurningRight(duration);
                break;
        }
    }
}