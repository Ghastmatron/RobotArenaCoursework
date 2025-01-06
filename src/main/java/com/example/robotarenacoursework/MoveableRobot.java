package com.example.robotarenacoursework;

//subclass of Robot to handle movement
public class MoveableRobot extends Robot {
    private boolean turningLeft = false;
    private boolean turningRight = false;

    //constructor
    public MoveableRobot(String name, int speed, int xPos, int yPos, int sensors, int direction){
        super(name, speed, xPos, yPos, sensors, direction);
    }

    //movement handling
    public void startTurningLeft(){
        turningLeft = true;
    }
    public void stopTurningLeft(){
        turningLeft = false;
    }
    public void startTurningRight(){
        turningRight = true;
    }
    public void stopTurningRight(){
        turningRight = false;
    }

    //update method to handle turning
    public void update(){
        if (turningLeft){
            setDirection((getDirection() +270) % 360);
        }
        if (turningRight){
            setDirection((getDirection() +90) % 360);
        }
    }

    //method to handle moving forward
    //moves dependant on direction
    public void moveForward() {
        setXPos(getXPos() + (int) (getSpeed() * Math.cos(Math.toRadians(getDirection()))));
        setYPos(getYPos() + (int) (getSpeed() * Math.sin(Math.toRadians(getDirection()))));
    }
    //method to handle moving backward
    //moves dependant on direction
    public void moveBackward() {
        setXPos(getXPos() - (int) (getSpeed() * Math.cos(Math.toRadians(getDirection()))));
        setYPos(getYPos() - (int) (getSpeed() * Math.sin(Math.toRadians(getDirection()))));
    }
}
