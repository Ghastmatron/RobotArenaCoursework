package com.example.robotarenacoursework;

import java.util.ArrayList;

public class Collision extends Arena {
    private ArrayList<MoveableRobot> robots;
    private ArrayList<Obstacle> obstacles;

    public Collision(int xSize, int ySize, ArrayList<MoveableRobot> robots, ArrayList<Obstacle> obstacles) {
        super(xSize, ySize, robots, obstacles);
        this.robots = robots != null ? robots : new ArrayList<>();
        this.obstacles = obstacles != null ? obstacles : new ArrayList<>();
    }

    // Method to check if a robot has collided with an obstacle
    public boolean checkObstacleCollision(MoveableRobot robot, Obstacle obstacle) {
        // Get the bounding box of the robot
        double robotLeft = robot.getXPos();
        double robotRight = robot.getXPos() + robot.getXSize();
        double robotTop = robot.getYPos();
        double robotBottom = robot.getYPos() + robot.getYSize();

        // Get the bounding box of the obstacle
        double obstacleLeft = obstacle.getXPos();
        double obstacleRight = obstacle.getXPos() + obstacle.getXSize();
        double obstacleTop = obstacle.getYPos();
        double obstacleBottom = obstacle.getYPos() + obstacle.getYSize();

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

    // Method to handle collision logic
    public void handleCollision(MoveableRobot robot) {
        // Check if the robot is colliding with the obstacle
        for (Obstacle obstacle : obstacles) {
            if (checkObstacleCollision(robot, obstacle)) {
                // Handle the collision
                // Call robot to start decelerating
                robot.startDeccelerationTask();
                // Make the obstacle handle the collision
                obstacle.handleCollision(robot);
                // Rotate the robot
                robot.setDirection(robot.getDirection() + 180 + Math.random() * 20 - 10);
            }
        }
        // Check if the robot is colliding with other robots
        for (MoveableRobot otherRobot : robots) {
            if (robot != otherRobot) {
                if (checkRobotCollision(robot, otherRobot)) {
                    // Handle the collision
                    // Call robot to start decelerating
                    robot.startDeccelerationTask();
                    // Make robot bounce off target robot by turning 180 degrees, plus or minus 10 randomly
                    robot.setDirection(robot.getDirection() + 180 + Math.random() * 20 - 10);
                }
            }
        }

        // Check if the robot is colliding with the arena boundaries
        if (checkBoundaryCollision(robot)) {
            // Handle the collision
            // Call robot to start decelerating
            robot.startDeccelerationTask();
            // Set the robot edge to equal the boundary it collided with, ensuring it stays within the arena
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
            // Make robot bounce off the boundary by turning 180 degrees, plus or minus 10 randomly
            robot.setDirection(robot.getDirection() + 180 + Math.random() * 20 - 10);
        }
    }
}