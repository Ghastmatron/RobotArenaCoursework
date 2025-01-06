package com.example.robotarenacoursework;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;


public class RobotEventHandler {
    private boolean movingForward = false;
    private boolean movingBackward = false;

    public void addEventHandlers(Scene scene, MoveableRobot[] robots) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                movingForward = true;
            }
            if (e.getCode() == KeyCode.S) {
                movingBackward = true;
            }
            if (e.getCode() == KeyCode.A) {
                robots[0].startTurningLeft();
            }
            if (e.getCode() == KeyCode.D) {
                robots[0].startTurningRight();
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
                robots[0].stopTurningLeft();
            }
            if (e.getCode() == KeyCode.D) {
                robots[0].stopTurningRight();
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
