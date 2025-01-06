package com.example.robotarenacoursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomCanvas {
    private Arena arena;
    private static final int SCALE = 10; //Scaling factor

    // Constructor to initialize the CustomCanvas with an Arena
    public CustomCanvas(Arena arena) {
        this.arena = arena;
    }

    // Method to draw the arena, robots, and obstacles
    public void draw(GraphicsContext gc) {
        // Clear the canvas
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Draw the arena
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, arena.getXSize() * SCALE, arena.getYSize() * SCALE);

        // Draw the robots
        for (Robot robot : arena.getRobots()) {
            gc.setFill(Color.BLUE);
            gc.fillOval(robot.getXPos() * SCALE, robot.getYPos() * SCALE, SCALE, SCALE);
        }

        // Draw the obstacles
        for (Obstacle obstacle : arena.getObstacles()) {
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(obstacle.getXPos() * SCALE, obstacle.getYPos() * SCALE, obstacle.getSize() * SCALE, obstacle.getSize() * SCALE);
        }
    }
}