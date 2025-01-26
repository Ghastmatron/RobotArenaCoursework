package com.example.robotarenacoursework;

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
        StringBuilder info = new StringBuilder("Robot Info:\n");
        for (MoveableRobot robot : arena.getRobots()) {
            info.append(String.format("Name: %s, X: %.2f, Y: %.2f\n", robot.getName(), robot.getXPos(), robot.getYPos()));
        }
        infoLabel.setText(info.toString());
    }
}