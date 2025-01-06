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

        // Draw the obstacles with different colours based on their type
        for (Obstacle obstacle : arena.getObstacles()) {
            switch (obstacle.getType()) {
                case "rock":
                    gc.setFill(Color.DARKGRAY);
                    break;
                case "sand":
                    gc.setFill(Color.SANDYBROWN);
                    break;
                case "water":
                    gc.setFill(Color.BLUE);
                    break;
                default:
                    gc.setFill(Color.BLACK);
                    break;
            }
            gc.fillRect(obstacle.getXPos() * SCALE, obstacle.getYPos() * SCALE, obstacle.getSize() * SCALE, obstacle.getSize() * SCALE);
        }
    }
}