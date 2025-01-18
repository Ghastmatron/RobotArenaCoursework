// CustomCanvas.java
package com.example.robotarenacoursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomCanvas {
    private Arena arena;

    public CustomCanvas(Arena arena) {
        this.arena = arena;
    }

    public void draw(GraphicsContext gc) {
        // Draw arena, obstacles, and robots
        drawArena(gc);
        drawObstacles(gc);
        drawRobots(gc);
    }

    private void drawArena(GraphicsContext gc) {
        // Draw the arena boundaries
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, arena.getXSize(), arena.getYSize());
    }

    private void drawObstacles(GraphicsContext gc) {
        // Draw each obstacle
        for (Obstacle obstacle : arena.getObstacles()) {
            gc.setFill(Color.GRAY);
            gc.fillRect(obstacle.getXPos(), obstacle.getYPos(), obstacle.getXSize(), obstacle.getYSize());
        }
    }

    private void drawRobots(GraphicsContext gc) {
        // Draw each robot
        for (MoveableRobot robot : arena.getRobots()) {
            if (robot instanceof SensorRobot) {
                drawSensorRobot(gc, (SensorRobot) robot);
            } else {
                drawMoveableRobot(gc, robot);
            }
        }
    }

    private void drawMoveableRobot(GraphicsContext gc, MoveableRobot robot) {
        // Draw the robot body
        gc.setFill(Color.BLUE);
        gc.fillRect(robot.getXPos(), robot.getYPos(), robot.getXSize(), robot.getYSize());
    }


    private void drawSensorRobot(GraphicsContext gc, SensorRobot robot) {
        // Draw the robot body
        drawMoveableRobot(gc, robot);

        // Draw the whiskers
        gc.setStroke(Color.RED);
        gc.strokeLine(robot.getXPos() + robot.getXSize() / 2, robot.getYPos() + robot.getYSize() / 2, robot.getLeftWhiskerX(), robot.getLeftWhiskerY());
        gc.strokeLine(robot.getXPos() + robot.getXSize() / 2, robot.getYPos() + robot.getYSize() / 2, robot.getRightWhiskerX(), robot.getRightWhiskerY());
    }
}