package com.example.robotarenacoursework;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int xSize, ySize;
    private List<MoveableRobot> robots;
    private Obstacle[] obstacles;

    public Arena(int xSize, int ySize, List<MoveableRobot> robots, Obstacle[] obstacles) {
        this.xSize = xSize;
        this.ySize = xSize;
        this.robots = robots;
        this.obstacles = obstacles;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public List<MoveableRobot> getRobots() {
        return robots;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public void setRobots(List<MoveableRobot> robots) {
        this.robots = robots;
    }

    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void addRobot(MoveableRobot robot) {
        this.robots.add(robot);
    }

    public void checkBounds(MoveableRobot robot) {
        double xPos = robot.getXPos();
        double yPos = robot.getYPos();
        double direction = robot.getDirection();
        double whiskerLength = Math.max(robot.getXSize(), robot.getYSize()) * 2;
        double originalSpeed = robot.getSpeed();

        // Calculate whisker positions
        double leftWhiskerX = xPos + whiskerLength * Math.cos(Math.toRadians(direction - 45));
        double leftWhiskerY = yPos + whiskerLength * Math.sin(Math.toRadians(direction - 45));
        double rightWhiskerX = xPos + whiskerLength * Math.cos(Math.toRadians(direction + 45));
        double rightWhiskerY = yPos + whiskerLength * Math.sin(Math.toRadians(direction + 45));

        boolean leftWhiskerDetected = false;
        boolean rightWhiskerDetected = false;

        // Check if left whisker is out of bounds
        if (leftWhiskerX < 0 || leftWhiskerX > xSize || leftWhiskerY < 0 || leftWhiskerY > ySize) {
            leftWhiskerDetected = true;
        }

        // Check if right whisker is out of bounds
        if (rightWhiskerX < 0 || rightWhiskerX > xSize || rightWhiskerY < 0 || rightWhiskerY > ySize) {
            rightWhiskerDetected = true;
        }

        // If out of bounds, stop and correct movement
        if (leftWhiskerDetected || rightWhiskerDetected) {
            robot.correctMovement(leftWhiskerDetected, rightWhiskerDetected);
        }
    }

    public boolean detectObstacleSensorDetection(MoveableRobot robot) {
        if (obstacles == null) {
            return false;
        } else {
            for (Obstacle obstacle : obstacles) {
                if (robot.detectSensorDetection(obstacle.getXPos(), obstacle.getYPos())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean detectBoundarySensorDetection(MoveableRobot robot) {
        double xPos = robot.getXPos();
        double yPos = robot.getYPos();
        return xPos < 0 || xPos > xSize || yPos < 0 || yPos > ySize;
    }

    // Method to check bounds and collisions with robots
    public void checkBoundsAndSensorDetections(MoveableRobot robot) {
        checkBounds(robot);
        if (detectRobotSensorDetection(robot) || detectObstacleSensorDetection(robot)) {
            robot.correctMovement(detectRobotSensorDetection(robot), detectObstacleSensorDetection(robot));
        }
    }

    public void checkCollisions() {
        for (MoveableRobot robot : robots) {
            for (Obstacle obstacle : obstacles) {
                if (robot.detectSensorDetection(obstacle.getXPos(), obstacle.getYPos())) {
                    handleCollision(robot, obstacle);
                }
            }
        }
    }

    private void handleCollision(MoveableRobot robot, Obstacle obstacle) {
        robot.turnLeft();
    }

    // Method to check if robots have collided with another robot
    public boolean detectRobotSensorDetection(MoveableRobot robot) {
        for (MoveableRobot otherRobot : robots) {
            if (robot != otherRobot && robot.detectSensorDetection(otherRobot.getXPos(), otherRobot.getYPos())) {
                return true;
            }
        }
        return false;
    }

    public void checkCollision() {
        for (int i = 0; i < robots.size(); i++) {
            for (int j = i + 1; j < robots.size(); j++) {
                MoveableRobot robot1 = robots.get(i);
                MoveableRobot robot2 = robots.get(j);
                if (robot1.detectCollision(robot2)) {
                    robot1.handleCollision(robot2);
                    robot2.handleCollision(robot1);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<MoveableRobot> robots = new ArrayList<>();
        robots.add(new MoveableRobot("Robot1", 5, 4, 4, 3, 0, null, 3, 3));
        robots.add(new MoveableRobot("Robot2", 5, 6, 6, 3, 0, null, 3, 3));

        Obstacle[] obstacles = new Obstacle[2];
        obstacles[0] = new Obstacle(0.0, 0.0, "rock");
        obstacles[1] = new Obstacle(5.0, 5.0, "rock");

        Arena arena = new Arena(10, 10, robots, obstacles);
        System.out.println(arena.getXSize());
        System.out.println(arena.getYSize());
        System.out.println(arena.getRobots().get(0).getName());
        System.out.println(arena.getRobots().get(1).getName());
        System.out.println(arena.getObstacles()[0]);
        System.out.println(arena.getObstacles()[1]);
    }
}