package com.example.robotarenacoursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomCanvas {
    private Arena arena;

    public CustomCanvas(Arena arena) {
        this.arena = arena;
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawBorder(gc);
        drawRobots(gc);
    }

    private void drawBorder(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, arena.getXSize() * 10, arena.getYSize() * 10);
    }

    private void drawRobots(GraphicsContext gc) {
        for (MoveableRobot robot : arena.getRobots()) {
            drawRobot(gc, robot);
        }
    }

    private void drawRobot(GraphicsContext gc, MoveableRobot robot) {
        double x = robot.getXPos() * 10;
        double y = robot.getYPos() * 10;
        double xSize = robot.getXSize() * 10;
        double ySize = robot.getYSize() * 10;
        double direction = robot.getDirection();

        gc.setFill(Color.BLUE);
        gc.fillOval(x - xSize / 2, y - ySize / 2, xSize, ySize);

        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        double whiskerLength = Math.max(xSize, ySize) * 2;
        double leftWhiskerX = x + whiskerLength * Math.cos(Math.toRadians(direction - 45));
        double leftWhiskerY = y + whiskerLength * Math.sin(Math.toRadians(direction - 45));
        double rightWhiskerX = x + whiskerLength * Math.cos(Math.toRadians(direction + 45));
        double rightWhiskerY = y + whiskerLength * Math.sin(Math.toRadians(direction + 45));
        gc.strokeLine(x, y, leftWhiskerX, leftWhiskerY);
        gc.strokeLine(x, y, rightWhiskerX, rightWhiskerY);
    }
}