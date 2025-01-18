// SensorRobot.java
package com.example.robotarenacoursework;

class SensorRobot extends MoveableRobot {
    private double whiskerLength;
    private double leftWhiskerX;
    private double leftWhiskerY;
    private double rightWhiskerX;
    private double rightWhiskerY;
    private boolean leftWhiskerDetected;
    private boolean rightWhiskerDetected;

    public SensorRobot(String name, double speed, double maxSpeed, double minSpeed, double acceleration, double xPos, double yPos, int sensors, double direction, Arena arena, double xSize, double ySize){
        super(name, speed, maxSpeed, minSpeed, acceleration, xPos, yPos, sensors, direction, arena, xSize, ySize);
        this.whiskerLength = Math.max(xSize, ySize) * 2;
    }

    public double getWhiskerLength(){
        return this.whiskerLength;
    }

    public double getLeftWhiskerX(){
        return this.leftWhiskerX;
    }

    public double getLeftWhiskerY(){
        return this.leftWhiskerY;
    }

    public double getRightWhiskerX(){
        return this.rightWhiskerX;
    }

    public double getRightWhiskerY(){
        return this.rightWhiskerY;
    }

    // Function that handle all whisker logic
    // These can be pulled from the MoveableRobot class
    public void checkSensors(){
        // Calculate the front center position of the robot
        double frontX = this.getXPos() + this.getXSize() / 2 + this.getXSize() / 2 * Math.cos(Math.toRadians(this.getDirection()));
        double frontY = this.getYPos() + this.getYSize() / 2 + this.getYSize() / 2 * Math.sin(Math.toRadians(this.getDirection()));

        // Calculate whisker positions
        this.leftWhiskerX = frontX + this.whiskerLength * Math.cos(Math.toRadians(this.getDirection() - 45));
        this.leftWhiskerY = frontY + this.whiskerLength * Math.sin(Math.toRadians(this.getDirection() - 45));
        this.rightWhiskerX = frontX + this.whiskerLength * Math.cos(Math.toRadians(this.getDirection() + 45));
        this.rightWhiskerY = frontY + this.whiskerLength * Math.sin(Math.toRadians(this.getDirection() + 45));

        // Check if the whiskers are colliding with the boundaries
        // Boundaries are the edges of the arena
        if(this.leftWhiskerX < 0 || this.leftWhiskerX > this.getArena().getXSize() || this.leftWhiskerY < 0 || this.leftWhiskerY > this.getArena().getYSize()){
            this.leftWhiskerDetected = true;
        } else {
            this.leftWhiskerDetected = false;
        }
        if(this.rightWhiskerX < 0 || this.rightWhiskerX > this.getArena().getXSize() || this.rightWhiskerY < 0 || this.rightWhiskerY > this.getArena().getYSize()){
            this.rightWhiskerDetected = true;
        } else {
            this.rightWhiskerDetected = false;
        }

        // Check if the whiskers are colliding with obstacles
        for(Obstacle obstacle : this.getArena().getObstacles()){
            if(this.leftWhiskerX > obstacle.getXPos() && this.leftWhiskerX < obstacle.getXPos() + obstacle.getXSize() && this.leftWhiskerY > obstacle.getYPos() && this.leftWhiskerY < obstacle.getYPos() + obstacle.getYSize()){
                this.leftWhiskerDetected = true;
            }
            if(this.rightWhiskerX > obstacle.getXPos() && this.rightWhiskerX < obstacle.getXPos() + obstacle.getXSize() && this.rightWhiskerY > obstacle.getYPos() && this.rightWhiskerY < obstacle.getYPos() + obstacle.getYSize()){
                this.rightWhiskerDetected = true;
            }
        }

        // Check if the whiskers are colliding with other robots
        for(MoveableRobot robot : this.getArena().getRobots()){
            if(robot != this){
                if(this.leftWhiskerX > robot.getXPos() && this.leftWhiskerX < robot.getXPos() + robot.getXSize() && this.leftWhiskerY > robot.getYPos() && this.leftWhiskerY < robot.getYPos() + robot.getYSize()){
                    this.leftWhiskerDetected = true;
                }
                if(this.rightWhiskerX > robot.getXPos() && this.rightWhiskerX < robot.getXPos() + robot.getXSize() && this.rightWhiskerY > robot.getYPos() && this.rightWhiskerY < robot.getYPos() + robot.getYSize()){
                    this.rightWhiskerDetected = true;
                }
            }
        }

        // Correct the robot's direction if a whisker is detected
        this.correctMovement();
    }

    public void correctMovement(){
        if(this.leftWhiskerDetected && this.rightWhiskerDetected){
            // Start decelerating
            this.startDeccelerationTask();
            // Start turning
            this.startTurningLeft(100);
        } else if(this.leftWhiskerDetected){
            // Start decelerating
            this.startDeccelerationTask();
            // Start turning
            this.startTurningRight(100);
        } else if(this.rightWhiskerDetected){
            // Start decelerating
            this.startDeccelerationTask();
            // Start turning
            this.startTurningLeft(100);
        }
        // Start accelerating
        this.startAccelerationTask();
        // Set whiskers to false
        this.leftWhiskerDetected = false;
        this.rightWhiskerDetected = false;
    }

    public void update(){
        // Check the sensors
        this.checkSensors();
        // Update the robot
        super.update();
    }
}