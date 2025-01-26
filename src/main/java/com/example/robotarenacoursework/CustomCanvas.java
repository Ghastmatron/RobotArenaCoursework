// CustomCanvas.java
package com.example.robotarenacoursework;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomCanvas {
    private Arena arena;
    private Canvas canvas = new Canvas();

    public CustomCanvas(Arena arena) {
        this.arena = arena;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setArena(Arena arena) {
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
            } else if (robot instanceof BeamRobot){
                drawBeamRobot(gc, (BeamRobot) robot);
            }else if (robot instanceof FastRobot) {
                drawFastRobot(gc, (FastRobot) robot);
            }else if (robot instanceof HeavyRobot) {
                drawHeavyRobot(gc, (HeavyRobot) robot);
            }else{
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
        double wheelWidth = robot.getXSize() / 2;
        double wheelHeight = robot.getYSize() / 4;
        // Draw the top wheel
        gc.fillRect(robot.getXPos() + (robot.getXSize() - wheelWidth) / 2, robot.getYPos() - wheelHeight, wheelWidth, wheelHeight);
        // Draw the bottom wheel
        gc.fillRect(robot.getXPos() + (robot.getXSize() - wheelWidth) / 2, robot.getYPos() + robot.getYSize(), wheelWidth, wheelHeight);

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

    private void drawBeamRobot(GraphicsContext gc, BeamRobot robot) {
        // Draw the robot body
        drawMoveableRobot(gc, robot);

        // Draw the beam
        robot.drawBeam(gc);
    }

    private void drawFastRobot(GraphicsContext gc, FastRobot robot) {
        // Draw the robot body
        drawMoveableRobot(gc, robot);

        // Draw the flame
        gc.setFill(Color.RED);
        gc.fillOval(robot.getXPos() + robot.getXSize() / 2 - robot.getFlameRadius(), robot.getYPos() + robot.getYSize() / 2 - robot.getFlameRadius(), robot.getFlameRadius() * 2, robot.getFlameRadius() * 2);
    }

    private void drawHeavyRobot(GraphicsContext gc, HeavyRobot robot) {
        // Draw the robot body
        drawMoveableRobot(gc, robot);

        // Draw the weight
        gc.setFill(Color.GRAY);
        gc.fillRect(robot.getXPos() + robot.getXSize() / 2 - robot.getWeight() / 2, robot.getYPos() + robot.getYSize() / 2 - robot.getWeight() / 2, robot.getWeight(), robot.getWeight());
    }
}