package com.example.robotarenacoursework;

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
            arena.checkCollision();
            // Check for boundary collisions
            arena.checkBoundaryCollision();
            // Move the robot forward
            moveForward();
        }
    }


    /*
    // Method to update the robot's state
    public void update() {
        long currentTime = System.currentTimeMillis(); // Get the current time
        if (turningLeft) { // If the robot is turning left
            long elapsedTime = currentTime - turningStartTime; // Calculate elapsed time
            if (elapsedTime >= turningDuration) { // If the turning duration is over
                stopTurningLeft(); // Stop turning left
            } else {
                setDirection((getDirection() - (elapsedTime / 1000.0) * TURNING_SPEED) % 360); // Update direction
                consecutiveTurns++;
            }
        }
        if (turningRight) { // If the robot is turning right
            long elapsedTime = currentTime - turningStartTime; // Calculate elapsed time
            if (elapsedTime >= turningDuration) { // If the turning duration is over
                stopTurningRight(); // Stop turning right
            } else {
                setDirection((getDirection() + (elapsedTime / 1000.0) * TURNING_SPEED) % 360); // Update direction
                consecutiveTurns++;
            }
        }
        // Check for excessive spinning
        if (consecutiveTurns > MAX_CONSECUTIVE_TURNS) {
            stuck = true;
            stopAccelerationTask();
            stopDeccelerationTask();
            setSpeed(0);
        }
        // If stuck, slowly rotate until no obstacles are detected
        if (stuck) {
            if (!detectSensorDetectionWithObstacles() && !arena.detectRobotSensorDetection(this) && !arena.detectBoundarySensorDetection(this)) {
                stuck = false;
                consecutiveTurns = 0;
                startAccelerationTask();
            } else {
                setDirection((getDirection() + 1) % 360); // Slowly rotate
            }
        } else {
            // Check for collisions
            arena.checkCollision();
            // Check for boundary collisions
            arena.checkBoundaryCollision();
            moveForward(); // Move the robot forward
            arena.checkBoundsAndSensorDetections(this); // Check bounds and collisions
        }
    }


     */
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
        arena.checkBoundsAndSensorDetections(this); // Check bounds and collisions
    }

    // Method to detect sensor with a point
    public boolean detectSensorDetection(double x, double y) {
        double direction = getDirection(); // Get the direction of the robot
        double xPos = getXPos(); // Get the x position of the robot
        double yPos = getYPos(); // Get the y position of the robot
        double whiskerLength = Math.max(getXSize(), getYSize()) * 2; // Calculate whisker length

        // Calculate left whisker position
        double leftWhiskerX = xPos + whiskerLength * Math.cos(Math.toRadians(direction - 45));
        double leftWhiskerY = yPos + whiskerLength * Math.sin(Math.toRadians(direction - 45));
        // Calculate right whisker position
        double rightWhiskerX = xPos + whiskerLength * Math.cos(Math.toRadians(direction + 45));
        double rightWhiskerY = yPos + whiskerLength * Math.sin(Math.toRadians(direction + 45));

        // Define whisker lines
        Line2D leftWhisker = new Line2D.Double(xPos, yPos, leftWhiskerX, leftWhiskerY);
        Line2D rightWhisker = new Line2D.Double(xPos, yPos, rightWhiskerX, rightWhiskerY);

        // Check if the point intersects with either whisker
        return leftWhisker.intersects(x - 2.5, y - 2.5, 5, 5) || rightWhisker.intersects(x - 2.5, y - 2.5, 5, 5);
    }

    // Method to check if a point is near a line
    private boolean isPointNearLine(double x1, double y1, double x2, double y2, double px, double py) {
        double distance = Math.abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1) /
                Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
        return distance < 5; // Return true if the distance is less than 5
    }

    public boolean isSensorDetection(double x, double y) {
        return detectSensorDetection(x, y);
    }

    // Method to correct the movement of the robot
    public void correctMovement(boolean leftWhiskerDetected, boolean rightWhiskerDetected) {
        // If both whiskers detect an obstacle, turn 180 degrees
        if (leftWhiskerDetected && rightWhiskerDetected) {
            // stop accelleration task
            stopAccelerationTask();
            // start deceleration task
            startDeccelerationTask();
            // turn left until no longer needed to turn left
            turnLeft();
            // set the left whisker detected to false
            // set the right whisker detected to false
            leftWhiskerDetected = false;
            rightWhiskerDetected = false;
            // print statement
            System.out.println("Both Whiskers Detected");

        } else if (leftWhiskerDetected) { // If only the left whisker detects an obstacle, turn right
            // stop accelleration task
            stopAccelerationTask();
            // start deceleration task
            startDeccelerationTask();
            //turn right until no longer needed to turn right
            turnRight();
            // set the left whisker detected to false
            leftWhiskerDetected = false;
            // print statement
            System.out.println("Left Whisker Detected");

        } else if (rightWhiskerDetected) { // If only the right whisker detects an obstacle, turn left
            // stop accelleration task
            stopAccelerationTask();
            // start deceleration task
            startDeccelerationTask();
            //turn left until no longer needed to turn left
            turnLeft();
            // set the right whisker detected to false
            rightWhiskerDetected = false;
            // print statement
            System.out.println("Right Whisker Detected");
        }

        // Check if the whiskers are no longer detecting any obstacles
        if (!detectSensorDetectionWithObstacles() && !arena.detectRobotSensorDetection(this) && !arena.detectBoundarySensorDetection(this)) {
            // Resume movement
            resumeMovement = true;
            //stop deceleration task
            stopDeccelerationTask();
            //start acceleration task
            startAccelerationTask();
        }
    }

    // Method to detect collision with obstacles
    private boolean detectSensorDetectionWithObstacles() {
        if (arena.getObstacles() == null) { // If there are no obstacles
            return false; // Return false
        } else {
            for (Obstacle obstacle : arena.getObstacles()) { // Iterate through obstacles
                if (detectSensorDetection(obstacle.getXPos(), obstacle.getYPos())) { // If a collision is detected
                    return true; // Return true
                }
            }
            return false; // Return false if no collision is detected
        }
    }

    // Method to detect collision with other robots
    public boolean detectCollision(MoveableRobot otherRobot) {
        double distance = Math.sqrt(Math.pow(otherRobot.getXPos() - this.getXPos(), 2) + Math.pow(otherRobot.getYPos() - this.getYPos(), 2 ));
        return distance < (this.getXSize() + otherRobot.getXSize()) / 2;
    }

    // Method to handle collision with other robots
    public void handleCollision(MoveableRobot otherRobot) {
        currentTime = System.currentTimeMillis();
        if (currentTime < collisionTimer) {
            return;
        } else {
            collisionTimer = currentTime + COLLISION_DELAY;
            double angle = Math.atan2(otherRobot.getYPos() - this.getYPos(), otherRobot.getXPos() - this.getXPos());
            double newDirection = Math.toDegrees(angle) + 180;

            // Add a small random factor to the direction to prevent robots getting stuck
            newDirection += ThreadLocalRandom.current().nextDouble(-10, 10);

            this.setDirection(newDirection);
            this.setSpeed(this.getSpeed() * 0.5); // Reduce speed after collision
        }
    }

    public void handleBoundaryCollision() {
        handleOutofBounds();
        // Timer for current time
        long currentTime = System.currentTimeMillis();
        // Check if the collision timer is less than the current time
        if(currentTime < collisionTimer) {
            return;
        }else{
            // Handle Collision
            //stop accelleration task
            stopAccelerationTask();
            // start deceleration task
            startDeccelerationTask();
            double newDirection = this.getDirection() + 180;
            this.setDirection(newDirection);
            this.setSpeed(this.getSpeed() * 0.5); // Reduce speed after collision
            //check if robot is still in bounds
            if (!arena.detectBoundarySensorDetection(this)) {
                // Resume movement
                resumeMovement = true;
                //stop deceleration task
                stopDeccelerationTask();
                //start acceleration task
                startAccelerationTask();
            }
        }
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