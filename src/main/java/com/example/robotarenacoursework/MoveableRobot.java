package com.example.robotarenacoursework;

import java.util.Random;

public class MoveableRobot extends Robot {
    private Arena arena; // Reference to the arena
    private boolean turningLeft = false; // Flag to indicate if the robot is turning left
    private boolean turningRight = false; // Flag to indicate if the robot is turning right
    private long turningStartTime = 0; // Start time of the turning action
    private long turningDuration = 0; // Duration of the turning action
    private Random random = new Random(); // Random number generator
    private static final double TURNING_SPEED = 120; // Speed of turning
    private boolean collisionDetected = false; // Flag to indicate if a collision is detected
    private boolean resumeMovement = true; // Flag to indicate if the robot should resume moving forward

    // Constructor to initialize the robot
    public MoveableRobot(String name, double speed, double xPos, double yPos, int sensors, double direction, Arena arena, double xSize, double ySize) {
        super(name, speed, xPos, yPos, sensors, direction, xSize, ySize);
        this.arena = arena;
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

    // Method to update the robot's state
    public void update() {
        long currentTime = System.currentTimeMillis(); // Get the current time
        if (turningLeft) { // If the robot is turning left
            long elapsedTime = currentTime - turningStartTime; // Calculate elapsed time
            if (elapsedTime >= turningDuration) { // If the turning duration is over
                stopTurningLeft(); // Stop turning left
            } else {
                setDirection((getDirection() - (elapsedTime / 1000.0) * TURNING_SPEED) % 360); // Update direction
            }
        }
        if (turningRight) { // If the robot is turning right
            long elapsedTime = currentTime - turningStartTime; // Calculate elapsed time
            if (elapsedTime >= turningDuration) { // If the turning duration is over
                stopTurningRight(); // Stop turning right
            } else {
                setDirection((getDirection() + (elapsedTime / 1000.0) * TURNING_SPEED) % 360); // Update direction
            }
        }
        if (!collisionDetected && resumeMovement) { // If no collision is detected and movement should resume
            moveForward(); // Move forward
        }
        arena.checkBoundsAndSensorDetections(this); // Check bounds and collisions
    }

    // Method to turn the robot left
    public void turnLeft() {
        setDirection(getDirection() - 10);
    }

    // Method to turn the robot right
    public void turnRight() {
        setDirection(getDirection() + 10);
    }

    // Method to move the robot forward
    public void moveForward() {
        setXPos(getXPos() + (getSpeed() * Math.cos(Math.toRadians(getDirection())))); // Update x position
        setYPos(getYPos() + (getSpeed() * Math.sin(Math.toRadians(getDirection())))); // Update y position
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

        // Check if the point is near either whisker
        return isPointNearLine(xPos, yPos, leftWhiskerX, leftWhiskerY, x, y) ||
                isPointNearLine(xPos, yPos, rightWhiskerX, rightWhiskerY, x, y);
    }

    // Method to check if a point is near a line
    private boolean isPointNearLine(double x1, double y1, double x2, double y2, double px, double py) {
        double distance = Math.abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1) /
                Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
        return distance < 5; // Return true if the distance is less than 5
    }

    // Method to correct the movement of the robot
    public void correctMovement(boolean leftWhiskerDetected, boolean rightWhiskerDetected) {
        double currentSpeed = getSpeed(); // Store the current speed
        double zeroSpeed = 0; // Set the speed to zero

        // If both whiskers detect an obstacle, turn 180 degrees
        if (leftWhiskerDetected && rightWhiskerDetected) {
            setSpeed(zeroSpeed); // Set the speed to zero
            // turn left until no longer needed to turn left
            turnLeft();
            setSpeed(currentSpeed); // Set the speed to the current speed
        } else if (leftWhiskerDetected) { // If only the left whisker detects an obstacle, turn right
            setSpeed(zeroSpeed); // Set the speed to zero
            turnRight();
            while (detectSensorDetectionWithObstacles() || arena.detectRobotSensorDetection(this) || arena.detectBoundarySensorDetection(this)) {
                turnRight();
            }
            setSpeed(currentSpeed); // Set the speed to the current speed
        } else if (rightWhiskerDetected) { // If only the right whisker detects an obstacle, turn left
            setSpeed(zeroSpeed); // Set the speed to zero
            turnLeft();
            while (detectSensorDetectionWithObstacles() || arena.detectRobotSensorDetection(this) || arena.detectBoundarySensorDetection(this)) {
                turnLeft();
            }
            setSpeed(currentSpeed); // Set the speed to the current speed
        }

        // Check if the whiskers are no longer detecting any obstacles
        if (!detectSensorDetectionWithObstacles() && !arena.detectRobotSensorDetection(this) && !arena.detectBoundarySensorDetection(this)) {
            setSpeed(currentSpeed); // Resume movement
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

    public void handleCollision(MoveableRobot otherRobot) {
        double angle = Math.atan2(otherRobot.getYPos() - this.getYPos(), otherRobot.getXPos() - this.getXPos());
        double newDirection = Math.toDegrees(angle) + 180;
        this.setDirection(newDirection);
        this.setSpeed(this.getSpeed() * 0.5); // Reduce speed after collision
    }
}