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
        drawBoundary(gc);
        drawObstacles(gc);
        drawRobots(gc);
    }

    private void drawArena(GraphicsContext gc) {
        // Draw the arena boundaries
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, arena.getXSize(), arena.getYSize());
    }

    public void drawBoundary(GraphicsContext gc) {
        int maxWidth = 1000;
        int maxHeight = 800;

        clearBoundary(gc);

        int xOffset = (maxWidth - arena.getXSize()) / 2;
        int yOffset = (maxHeight - arena.getYSize()) / 2;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(xOffset, yOffset, arena.getXSize(), arena.getYSize());
    }

    public void clearBoundary(GraphicsContext gc) {
        int maxWidth = 1000;
        int maxHeight = 800;
        gc.clearRect(0, 0, maxWidth, maxHeight);
    }

    private void drawObstacles(GraphicsContext gc) {
        // Draw each obstacle
        for (Obstacle obstacle : arena.getObstacles()) {
            if (obstacle instanceof SlowObstacle) {
                drawSlowObstacle(gc, (SlowObstacle) obstacle);
            } else if (obstacle instanceof TeleportObstacle) {
                drawTeleportObstacle(gc, (TeleportObstacle) obstacle);
            } else if (obstacle instanceof SpeedObstacle) {
                drawSpeedObstacle(gc, (SpeedObstacle) obstacle);
            } else {
                drawBounceObstacle(gc, (BounceObstacle) obstacle);
            }
        }
    }

    private void drawSlowObstacle(GraphicsContext gc, SlowObstacle obstacle) {
        // Draw the obstacle
        gc.setFill(Color.ORANGE);
        gc.fillRect(obstacle.getXPos(), obstacle.getYPos(), obstacle.getXSize(), obstacle.getYSize());
    }

    private void drawTeleportObstacle(GraphicsContext gc, TeleportObstacle obstacle) {
        // Draw the obstacle
        gc.setFill(Color.PURPLE);
        gc.fillRect(obstacle.getXPos(), obstacle.getYPos(), obstacle.getXSize(), obstacle.getYSize());
    }

    private void drawSpeedObstacle(GraphicsContext gc, SpeedObstacle obstacle) {
        // Draw the obstacle
        gc.setFill(Color.LAWNGREEN);
        gc.fillRect(obstacle.getXPos(), obstacle.getYPos(), obstacle.getXSize(), obstacle.getYSize());
    }

    private void drawBounceObstacle(GraphicsContext gc, BounceObstacle obstacle) {
        // Draw the obstacle
        gc.setFill(Color.LIGHTGOLDENRODYELLOW);
        gc.fillRect(obstacle.getXPos(), obstacle.getYPos(), obstacle.getXSize(), obstacle.getYSize());
    }

    private void drawRobots(GraphicsContext gc) {
        // Draw each robot
        for (MoveableRobot robot : arena.getRobots()) {
            if (robot instanceof SensorRobot) {
                drawSensorRobot(gc, (SensorRobot) robot);
            } else if (robot instanceof BumperRobot) {
                drawBumperRobot(gc, (BumperRobot) robot);
            } else {
                drawMoveableRobot(gc, robot);
            }
        }
    }
    private void drawMoveableRobot(GraphicsContext gc, MoveableRobot robot) {
        // Save the current state of the GraphicsContext
        gc.save();

        // Translate to the center of the robot
        double centerX = robot.getXPos() + robot.getXSize() / 2;
        double centerY = robot.getYPos() + robot.getYSize() / 2;
        gc.translate(centerX, centerY);

        // Rotate the GraphicsContext
        gc.rotate(robot.getDirection());

        // Translate back and draw the robot
        gc.translate(-centerX, -centerY);
        gc.setFill(Color.BLUE);
        gc.fillRect(robot.getXPos(), robot.getYPos(), robot.getXSize(), robot.getYSize());

        // Draw the wheels as rectangles
        gc.setFill(Color.DARKGRAY);
        double wheelWidth = robot.getXSize() / 4;
        double wheelHeight = robot.getYSize() / 2;
        // Draw the left wheel
        gc.fillRect(robot.getXPos() - wheelWidth, robot.getYPos() + (robot.getYSize() - wheelHeight) / 2, wheelWidth, wheelHeight);
        // Draw the right wheel
        gc.fillRect(robot.getXPos() + robot.getXSize(), robot.getYPos() + (robot.getYSize() - wheelHeight) / 2, wheelWidth, wheelHeight);

        // Restore the GraphicsContext state
        gc.restore();
    }


    private void drawSensorRobot(GraphicsContext gc, SensorRobot robot) {
        // Draw the robot body
        drawMoveableRobot(gc, robot);

        // Draw the whiskers
        gc.setStroke(Color.RED);
        gc.strokeLine(robot.getXPos() + robot.getXSize() / 2, robot.getYPos() + robot.getYSize() / 2, robot.getLeftWhiskerX(), robot.getLeftWhiskerY());
        gc.strokeLine(robot.getXPos() + robot.getXSize() / 2, robot.getYPos() + robot.getYSize() / 2, robot.getRightWhiskerX(), robot.getRightWhiskerY());
    }

    private void drawBumperRobot(GraphicsContext gc, BumperRobot robot) {
        // Draw the robot body
        drawMoveableRobot(gc, robot);

        // Draw the ring
        gc.setStroke(Color.GREEN);
        gc.strokeOval(robot.getXPos() + robot.getXSize() / 2 - robot.getRingRadius(), robot.getYPos() + robot.getYSize() / 2 - robot.getRingRadius(), robot.getRingRadius() * 2, robot.getRingRadius() * 2);
    }
}