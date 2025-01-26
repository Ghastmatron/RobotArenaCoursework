package com.example.robotarenacoursework;

/*
    * This class is used to create an InfoPanel object
    * It extends the VBox class and is used
    * to display information about the robot
 */

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

// InfoPanel class to display robot information
public class InfoPanel extends VBox {
    private Label infoLabel;

    // Constructor to create the InfoPanel
    public InfoPanel() {
        this.getStyleClass().add("info-panel");

        // Create a label for the info
        infoLabel = new Label("Robot Info:");
        infoLabel.getStyleClass().add("label");
        this.getChildren().add(infoLabel);
    }

    // Method to update the information displayed
    public void updateInfo(Arena arena) {
        StringBuilder info = new StringBuilder("Arena Info:\n");
        // Add robot information
        for (MoveableRobot robot : arena.getRobots()) {
            info.append(String.format("Robot - Name: %s, X: %.2f, Y: %.2f\n", robot.getName(), robot.getXPos(), robot.getYPos()));
        }
        // Add obstacle information
        for (Obstacle obstacle : arena.getObstacles()) {
            info.append(String.format("Obstacle - Type: %s, X: %.2f, Y: %.2f, Width: %.2f, Height: %.2f\n",
                    obstacle.getClass().getSimpleName(), obstacle.getXPos(), obstacle.getYPos(), obstacle.getXSize(), obstacle.getYSize()));
        }
        infoLabel.setText(info.toString());
    }
}