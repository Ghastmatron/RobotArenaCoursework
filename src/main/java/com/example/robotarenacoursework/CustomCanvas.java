package com.example.robotarenacoursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomCanvas {
    private Arena arena;

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
        gc.fillRect(0, 0, arena.getXSize() * 10, arena.getYSize() * 10);

        // Draw the robots
        for (Robot robot : arena.getRobots()) {
            gc.setFill(Color.BLUE);
            gc.fillOval(robot.getXPos() * 10, robot.getYPos() * 10, 10, 10);
        }

        // Draw the obstacles
        for (Obstacle obstacle : arena.getObstacles()) {
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(obstacle.getXPos() * 10, obstacle.getYPos() * 10, obstacle.getSize() * 10, obstacle.getSize() * 10);
        }
    }
}