package com.example.robotarenacoursework;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.awt.geom.Line2D;
import java.util.concurrent.ThreadLocalRandom;

public class MoveableRobot extends Robot {
    private Arena arena; // Reference to the arena
    private boolean turningLeft = false; // Flag to indicate if the robot is turning left
    private boolean turningRight = false; // Flag to indicate if the robot is turning right
    private long turningStartTime = 0; // Start time of the turning action
    private long turningDuration = 0; // Duration of the turning action
    private static final double TURNING_SPEED = 120; // Speed of turning
    private boolean resumeMovement = true;  // Flag to indicate if the robot should resume moving forward
    private ScheduledExecutorService scheduler; // Scheduler to update the robot's state
    private long collisionTimer = 100; // Timer to prevent multiple collisions
    private long currentTime; // Timer for current time
    private static final long COLLISION_DELAY = 150; // Timer to prevent multiple collisions
    private int consecutiveTurns = 0; // Counter for consecutive turns
    private static final int MAX_CONSECUTIVE_TURNS = 5; // Threshold for consecutive turns
    private boolean stuck = false; // Flag to indicate if the robot is stuck

    // Constructor to initialize the robot
    public MoveableRobot(String name, double speed, double MaxSpeed, double MinSpeed, double Accelleration, double xPos, double yPos, int sensors, double direction, Arena arena, double xSize, double ySize) {
        super(name, speed, MaxSpeed, MinSpeed, Accelleration, xPos, yPos, sensors, direction, xSize, ySize);
        this.arena = arena;
        startAccelerationTask(); // Start the acceleration task
    }

    // Method to start turning left for a specified duration
    public void startTurningLeft(long duration) {
        turningLeft = true;
        turningStartTime = System.currentTimeMillis();
        turningDuration = duration;
    }

    // Method to stop turning left
    public void stopTurningLeft() {
        turningLeft = false;
    }

    // Method to start turning right for a specified duration
    public void startTurningRight(long duration) {
        turningRight = true;
        turningStartTime = System.currentTimeMillis();
        turningDuration = duration;
    }

    // Method to stop turning right
    public void stopTurningRight() {
        turningRight = false;
    }

    protected Arena getArena(){
        return this.arena;
    }

    // Method to update the robots state
    public void update(){
        // The robot needs to move forward
        if(resumeMovement){
            // Check for collisions
            Collision collision = new Collision(arena.getXSize(), arena.getYSize(), arena.getRobots(), arena.getObstacles());
            collision.handleCollision(this);
            // Move the robot forward
            moveForward();
            // Start the acceleration task
            startAccelerationTask();
            // Stop the deceleration task
            stopDeccelerationTask();
        }
    }

    // Method to turn the robot left
    public void turnLeft() {
        this.setDirection(getDirection() - 10);
    }

    // Method to turn the robot right
    public void turnRight() {
        this.setDirection(getDirection() + 10);
    }

    // Method to move the robot forward
    public void moveForward() {
        double directionInRadians = Math.toRadians(getDirection()); // Convert direction to radians
        setXPos(getXPos() + (getSpeed() * Math.cos(directionInRadians))); // Update x position
        setYPos(getYPos() + (getSpeed() * Math.sin(directionInRadians))); // Update y position
    }

    // Method to handle if the robot is out of bounds
    public void handleOutofBounds(){
        //check the edge of the robot
        double x1 = this.getXPos() - this.getXSize() / 2;
        double x2 = this.getXPos() + this.getXSize() / 2;
        double y1 = this.getYPos() - this.getYSize() / 2;
        double y2 = this.getYPos() + this.getYSize() / 2;
        //check if the robot is out of bounds
        if (x1 < 0) {
            this.setXPos(this.getXSize() / 2);
        } else if (x2 > arena.getXSize()) {
            this.setXPos(arena.getXSize() - this.getXSize() / 2);
        }
        if (y1 < 0) {
            this.setYPos(this.getYSize() / 2);
        } else if (y2 > arena.getYSize()) {
            this.setYPos(arena.getYSize() - this.getYSize() / 2);
        }

    }

    // Method to start the acceleration task
    public void startAccelerationTask() {
        scheduler = Executors.newScheduledThreadPool(1); // Create a new scheduled executor service
        scheduler.scheduleAtFixedRate(this::accelerate, 0, 1, TimeUnit.SECONDS); // Schedule the acceleration task
    }

    private void accelerate() {
        if (getSpeed() < getMaxSpeed()) { // If the speed is less than the maximum speed
            this.setSpeed(getSpeed() + getAcceleration()); // Increase the speed
        }
    }

    public void stopAccelerationTask() {
        scheduler.shutdown(); // Shutdown the scheduler
    }

    public void startDeccelerationTask() {
        scheduler = Executors.newScheduledThreadPool(1); // Create a new scheduled executor service
        scheduler.scheduleAtFixedRate(this::deccelerate, 0, 1, TimeUnit.SECONDS); // Schedule the deceleration task
    }

    public void deccelerate() {
        if (getSpeed() > getMinSpeed()) { // If the speed is greater than the minimum speed
            this.setSpeed(getSpeed() - getAcceleration()); // Decrease the speed
        }
    }

    public void stopDeccelerationTask() {
        scheduler.shutdown(); // Shutdown the scheduler
    }
}