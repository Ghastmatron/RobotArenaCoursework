package com.example.robotarenacoursework;

/*
 * This class is used to handle events for the robot controls
 * It is used to add event handlers for robot controls and update the state of all robots
 * It also checks for collisions between the BeamRobot and other robots
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class RobotEventHandler {
    private boolean movingForward = false;
    private boolean movingBackward = false;
    private Timeline turningLeftTimeline;
    private Timeline turningRightTimeline;

    // Method to add event handlers for robot controls
    public void addEventHandlers(Scene scene, ArrayList<MoveableRobot> robots) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                movingForward = true; // Start moving forward
            }
            if (e.getCode() == KeyCode.S) {
                movingBackward = true; // Start moving backward
            }
            if (e.getCode() == KeyCode.A) {
                if (turningLeftTimeline == null) {
                    // Start turning left
                    turningLeftTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
                        robots.get(0).startTurningLeft(100);
                        robots.get(0).update();
                    }));
                    turningLeftTimeline.setCycleCount(Timeline.INDEFINITE);
                    turningLeftTimeline.play();
                }
            }
            if (e.getCode() == KeyCode.D) {
                if (turningRightTimeline == null) {
                    // Start turning right
                    turningRightTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
                        robots.get(0).startTurningRight(100);
                        robots.get(0).update();
                    }));
                    turningRightTimeline.setCycleCount(Timeline.INDEFINITE);
                    turningRightTimeline.play();
                }
            }
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) {
                movingForward = false; // Stop moving forward
            }
            if (e.getCode() == KeyCode.S) {
                movingBackward = false; // Stop moving backward
            }
            if (e.getCode() == KeyCode.A) {
                if (turningLeftTimeline != null) {
                    // Stop turning left
                    turningLeftTimeline.stop();
                    turningLeftTimeline = null;
                    robots.get(0).stopTurningLeft();
                }
            }
            if (e.getCode() == KeyCode.D) {
                if (turningRightTimeline != null) {
                    // Stop turning right
                    turningRightTimeline.stop();
                    turningRightTimeline = null;
                    robots.get(0).stopTurningRight();
                }
            }
        });
    }

    // Method to check for collisions between the BeamRobot and other robots
    private void checkCollisions(BeamRobot beamRobot, ArrayList<MoveableRobot> robots) {
        for (int i = robots.size() - 1; i >= 0; i--) {
            MoveableRobot robot = robots.get(i);
            if (robot != beamRobot && beamRobot.collidesWith(robot)) {
                robots.remove(i); // Remove the robot if it collides with the BeamRobot
            }
        }
    }

    // Method to update the state of all robots
    public void updateRobots(ArrayList<MoveableRobot> robots) {
        for (MoveableRobot robot : robots) {
            if (robot instanceof SensorRobot) {
                ((SensorRobot) robot).update(); // Update SensorRobot
            } else if (robot instanceof BumperRobot) {
                ((BumperRobot) robot).update(); // Update BumperRobot
            } else if (robot instanceof BeamRobot) {
                ((BeamRobot) robot).update(); // Update BeamRobot
                checkCollisions((BeamRobot) robot, robots); // Check for collisions
            } else {
                robot.update(); // Update other types of robots
            }
        }
    }
}