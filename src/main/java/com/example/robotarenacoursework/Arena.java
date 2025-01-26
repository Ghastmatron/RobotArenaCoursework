package com.example.robotarenacoursework;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import java.util.Random;

public class Arena {
    private int xSize, ySize;
    private ArrayList<MoveableRobot> robots;
    private ArrayList<Obstacle> obstacles;
    private MoveableRobot selectedRobot;
    private Obstacle selectedObstacle;
    private boolean isDragging;

    public Arena(int xSize, int ySize, ArrayList<MoveableRobot> robots, ArrayList<Obstacle> obstacles) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.robots = robots == null ? new ArrayList<>() : robots;
        this.obstacles = obstacles == null ? new ArrayList<>() : obstacles;
    }

    public Arena(ArrayList<MoveableRobot> robots, ArrayList<Obstacle> obstacles) {
    }

    // Getters and setters
    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public ArrayList<MoveableRobot> getRobots() {
        return robots;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setRobots(ArrayList<MoveableRobot> robots) {
        this.robots = robots;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void addRobot(MoveableRobot robot) {
        this.robots.add(robot);
    }

    public void removeRobot(MoveableRobot robot) {
        this.robots.remove(robot);
    }

    // Method to handle mouse pressed
    public void handleMousePressed(MouseEvent event, UserInterfaceController controller) {
        if (controller.isRunning()) return; // If the controller is running, return

        double x = event.getX(); // Get the x position of the mouse
        double y = event.getY(); // Get the y position of the mouse

        //Set Selected robot or obstacle to false
        selectedRobot = null;
        selectedObstacle = null;

        for (MoveableRobot robot : robots) {
            if (robot.contains(x, y)) { // If the robot contains the mouse position
                selectedRobot = robot; // Set the selected robot to the robot
                return;
            }
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle.contains(x, y)) { // If the obstacle contains the mouse position
                selectedObstacle = obstacle; // Set the selected obstacle to the obstacle
                return;
            }
        }
    }


    public void handleMouseDragged(MouseEvent event, UserInterfaceController controller) {
        if (controller.isRunning()) return; // If the controller is running, return

        double x = event.getX(); // Get the x position of the mouse
        double y = event.getY(); // Get the y position of the mouse

        if (selectedRobot != null) { // If the selected robot is not null
            selectedRobot.setPosition(x, y); // Set the position of the selected robot to the mouse position
        } else if (selectedObstacle != null) { // If the selected obstacle is not null
            selectedObstacle.setPosition(x, y); // Set the position of the selected obstacle to the mouse position
        }

        // Redraw the canvas
        controller.redrawCanvas();
    }

    public void handleMouseReleased(MouseEvent event, UserInterfaceController controller) {
        if (controller.isRunning()) return; // If the controller is running, return

        isDragging = false; // Set isDragging to false
    }

    // Method delete selected robot or obstacle
    public void deleteSelected() {
        if (selectedRobot != null) { // If the selected robot is not null
            robots.remove(selectedRobot); // Remove the selected robot from the list of robots
            selectedRobot = null; // Set the selected robot to null
        } else if (selectedObstacle != null) { // If the selected obstacle is not null
            obstacles.remove(selectedObstacle); // Remove the selected obstacle from the list of obstacles
            selectedObstacle = null; // Set the selected obstacle to null
        }
    }

    // Method to initialize the default arena
    public void initializeDefaultArena() {
        Random random = new Random();

        // Add 8 robots with random positions
        for (int i = 0; i < 8; i++) {
            double xPos = random.nextDouble() * xSize;
            double yPos = random.nextDouble() * ySize;
            robots.add(new MoveableRobot("Robot" + (i + 1), 1.0, 5.0, 0.5, 0.1, xPos, yPos, 4, 0, this, 20, 20));
        }

        // Add 6 obstacles with random positions
        for (int i = 0; i < 6; i++) {
            double xPos = random.nextDouble() * xSize;
            double yPos = random.nextDouble() * ySize;
            if (i % 4 == 0) {
                obstacles.add(new BounceObstacle(xPos, yPos, 50, 50));
            } else if (i % 4 == 1) {
                obstacles.add(new SlowObstacle(xPos, yPos, 50, 50));
            } else if (i % 4 == 2) {
                obstacles.add(new SpeedObstacle(xPos, yPos, 50, 50));
            } else {
                obstacles.add(new TeleportObstacle(xPos, yPos, 50, 50, this));
            }
        }
    }

}