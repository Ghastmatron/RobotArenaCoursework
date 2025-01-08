package com.example.robotarenacoursework;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class RobotEventHandler {
    private boolean movingForward = false;
    private boolean movingBackward = false;
    private Timeline turningLeftTimeline;
    private Timeline turningRightTimeline;

    public void addEventHandlers(Scene scene, MoveableRobot[] robots) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                movingForward = true;
            }
            if (e.getCode() == KeyCode.S) {
                movingBackward = true;
            }
            if (e.getCode() == KeyCode.A) {
                if (turningLeftTimeline == null) {
                    turningLeftTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
                        robots[0].startTurningLeft(100);
                        robots[0].update();
                    }));
                    turningLeftTimeline.setCycleCount(Timeline.INDEFINITE);
                    turningLeftTimeline.play();
                }
            }
            if (e.getCode() == KeyCode.D) {
                if (turningRightTimeline == null) {
                    turningRightTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
                        robots[0].startTurningRight(100);
                        robots[0].update();
                    }));
                    turningRightTimeline.setCycleCount(Timeline.INDEFINITE);
                    turningRightTimeline.play();
                }
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) {
                movingForward = false;
            }
            if (e.getCode() == KeyCode.S) {
                movingBackward = false;
            }
            if (e.getCode() == KeyCode.A) {
                if (turningLeftTimeline != null) {
                    turningLeftTimeline.stop();
                    turningLeftTimeline = null;
                    robots[0].stopTurningLeft();
                }
            }
            if (e.getCode() == KeyCode.D) {
                if (turningRightTimeline != null) {
                    turningRightTimeline.stop();
                    turningRightTimeline = null;
                    robots[0].stopTurningRight();
                }
            }
        });
    }

    public void updateRobots(MoveableRobot[] robots) {
        for (MoveableRobot robot : robots) {
            robot.update();
            if (movingForward) {
                robot.moveForward();
            }
            if (movingBackward) {
                robot.moveBackward();
            }
        }
    }
}