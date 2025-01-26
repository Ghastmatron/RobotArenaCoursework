package com.example.robotarenacoursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.ArrayList;

class BeamRobot extends MoveableRobot {
    private double detectionRange;
    private MoveableRobot target;
    private Color beamColor = Color.RED;

    // Constructor to initialize the BeamRobot
    public BeamRobot(String name, double speed, double maxSpeed, double minSpeed, double acceleration, double xPos, double yPos, int sensors, double direction, Arena arena, double xSize, double ySize, double detectionRange) {
        super(name, speed, maxSpeed, minSpeed, acceleration, xPos, yPos, sensors, direction, arena, xSize, ySize);
        this.detectionRange = detectionRange;
    }

    // Getter for detectionRange
    public double getDetectionRange() {
        return detectionRange;
    }

    // Setter for detectionRange
    public void setDetectionRange(double detectionRange) {
        this.detectionRange = detectionRange;
    }

    // Method to detect other robots within the detection range
    public MoveableRobot detectRobot(ArrayList<MoveableRobot> robots) {
        for (MoveableRobot robot : robots) {
            if (robot != this && distanceTo(robot) <= detectionRange) {
                return robot;
            }
        }
        return null;
    }

    // Method to calculate the distance to another robot
    private double distanceTo(MoveableRobot robot) {
        double dx = robot.getXPos() - this.getXPos();
        double dy = robot.getYPos() - this.getYPos();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Method to follow a detected robot
    public void followRobot(MoveableRobot target) {
        if (target != null) {
            double dx = target.getXPos() - this.getXPos();
            double dy = target.getYPos() - this.getYPos();
            double distance = distanceTo(target);
            if (distance > 0) {
                double angle = Math.atan2(dy, dx);
                this.setDirection(Math.toDegrees(angle));
                this.setSpeed(this.getMaxSpeed());
                this.setPosition(this.getXPos() + dx / distance, this.getYPos() + dy / distance);
            }
        } else {
            this.setSpeed(this.getMinSpeed());
        }
    }

    // Method to draw the detection beam
    public void drawBeam(GraphicsContext gc) {
        // Save the current state of the GraphicsContext
        gc.save();

        // Translate to the center of the robot
        double centerX = this.getXPos() + this.getXSize() / 2;
        double centerY = this.getYPos() + this.getYSize() / 2;
        gc.translate(centerX, centerY);

        // Rotate the GraphicsContext
        gc.rotate(this.getDirection());

        // Translate back and draw the beam
        gc.translate(-centerX, -centerY);
        gc.setStroke(beamColor);
        gc.setLineWidth(2);
        gc.strokeLine(centerX, centerY, centerX + this.getDetectionRange(), centerY);

        // Restore the GraphicsContext state
        gc.restore();
    }

    // Method to check if this robot collides with another robot
    public boolean collidesWith(MoveableRobot other) {
        double thisLeft = this.getXPos() - this.getXSize() / 2;
        double thisRight = this.getXPos() + this.getXSize() / 2;
        double thisTop = this.getYPos() - this.getYSize() / 2;
        double thisBottom = this.getYPos() + this.getYSize() / 2;

        double otherLeft = other.getXPos() - other.getXSize() / 2;
        double otherRight = other.getXPos() + other.getXSize() / 2;
        double otherTop = other.getYPos() - other.getYSize() / 2;
        double otherBottom = other.getYPos() + other.getYSize() / 2;

        return thisRight > otherLeft && thisLeft < otherRight && thisBottom > otherTop && thisTop < otherBottom;
    }

    // Override the update method to include detection and following logic
    @Override
    public void update() {
        MoveableRobot target = detectRobot(this.getArena().getRobots());
        followRobot(target);
        super.update();
    }
}