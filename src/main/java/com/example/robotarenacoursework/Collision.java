package com.example.robotarenacoursework;

/*
    * This class is used to handle collisions between the robot and the obstacles
    * It extends the Arena class
    * It contains methods to check if the robot has collided with an obstacle, another robot or the arena boundaries
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

// Class to handle collision logic
public class Collision extends Arena {
    private ArrayList<MoveableRobot> robots;
    private ArrayList<Obstacle> obstacles;
    // Constructor
    public Collision(int xSize, int ySize, ArrayList<MoveableRobot> robots, ArrayList<Obstacle> obstacles) {
        super(xSize, ySize, robots, obstacles);
        this.robots = robots != null ? robots : new ArrayList<>(); // If robots is not null, set it to the value of robots, otherwise set it to an empty ArrayList
        this.obstacles = obstacles != null ? obstacles : new ArrayList<>(); // If obstacles is not null, set it to the value of obstacles, otherwise set it to an empty ArrayList
    }

    // Method to check if a robot has collided with an obstacle
    public boolean checkObstacleCollision(MoveableRobot robot, Obstacle obstacle) {
        // Get the bounding box of the robot
        double robotLeft = robot.getXPos(); // Get the x position of the robot
        double robotRight = robot.getXPos() + robot.getXSize(); // Get the x position of the robot plus the width of the robot
        double robotTop = robot.getYPos();// Get the y position of the robot
        double robotBottom = robot.getYPos() + robot.getYSize(); // Get the y position of the robot plus the height of the robot

        // Get the bounding box of the obstacle
        double obstacleLeft = obstacle.getXPos(); // Get the x position of the obstacle
        double obstacleRight = obstacle.getXPos() + obstacle.getXSize(); // Get the x position of the obstacle plus the width of the obstacle
        double obstacleTop = obstacle.getYPos();// Get the y position of the obstacle
        double obstacleBottom = obstacle.getYPos() + obstacle.getYSize(); //  Get the y position of the obstacle plus the height of the obstacle

        // Check if the robot is colliding with the obstacle
        return robotRight > obstacleLeft && robotLeft < obstacleRight &&
                robotBottom > obstacleTop && robotTop < obstacleBottom;
    }

    // Method to check if a robot has collided with another robot
    public boolean checkRobotCollision(MoveableRobot robot, MoveableRobot otherRobot) {
        // Get the bounding box of the robot
        double robotLeft = robot.getXPos();
        double robotRight = robot.getXPos() + robot.getXSize();
        double robotTop = robot.getYPos();
        double robotBottom = robot.getYPos() + robot.getYSize();

        // Get the bounding box of the other robot
        double otherRobotLeft = otherRobot.getXPos();
        double otherRobotRight = otherRobot.getXPos() + otherRobot.getXSize();
        double otherRobotTop = otherRobot.getYPos();
        double otherRobotBottom = otherRobot.getYPos() + otherRobot.getYSize();

        // Check if the robot is colliding with the other robot
        return robotRight > otherRobotLeft && robotLeft < otherRobotRight &&
                robotBottom > otherRobotTop && robotTop < otherRobotBottom;
    }

    public boolean checkBoundaryCollision(MoveableRobot robot) {
        // Check if any edge of the robot is colliding with the arena boundaries
        return robot.getXPos() < 0 || robot.getXPos() + robot.getXSize() > this.getXSize() ||
                robot.getYPos() < 0 || robot.getYPos() + robot.getYSize() > this.getYSize();
    }

    public void handleCollision(MoveableRobot robot) {
        // Create a timeline for the delay
        Timeline collisionDelay = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            // Check if the robot is colliding with the obstacle
            for (Obstacle obstacle : obstacles) {
                if (checkObstacleCollision(robot, obstacle)) {
                    // Handle the collision
                    obstacle.handleCollision(robot);
                }
            }
            // Check if the robot is colliding with other robots
            for (MoveableRobot otherRobot : robots) {
                if (robot != otherRobot) {
                    if (checkRobotCollision(robot, otherRobot)) {
                        // Handle the collision
                        robot.startDeccelerationTask();
                        robot.setDirection(robot.getDirection() + 180 + Math.random() * 20 - 10);
                    }
                }
            }
            // Check if the robot is colliding with the arena boundaries
            if (checkBoundaryCollision(robot)) {
                // Handle the collision
                robot.startDeccelerationTask();
                if (robot.getXPos() < 0) {
                    robot.setXPos(0);
                } else if (robot.getXPos() + robot.getXSize() > this.getXSize()) {
                    robot.setXPos(this.getXSize() - robot.getXSize());
                }
                if (robot.getYPos() < 0) {
                    robot.setYPos(0);
                } else if (robot.getYPos() + robot.getYSize() > this.getYSize()) {
                    robot.setYPos(this.getYSize() - robot.getYSize());
                }
                if (robot.getXPos() == 0 || robot.getXPos() + robot.getXSize() == this.getXSize()) {
                    robot.setDirection(180 - robot.getDirection());
                }
                if (robot.getYPos() == 0 || robot.getYPos() + robot.getYSize() == this.getYSize()) {
                    robot.setDirection(-robot.getDirection());
                }
                robot.stopDeccelerationTask();
                robot.startAccelerationTask();
            }
        }));
        collisionDelay.setCycleCount(1); // Execute only once
        collisionDelay.play(); // Start the timeline
    }
}