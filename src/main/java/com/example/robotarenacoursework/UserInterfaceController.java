// UserInterfaceController.java
package com.example.robotarenacoursework;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class UserInterfaceController {
    @FXML
    private Canvas canvas;

    private Arena arena;
    private List<MoveableRobot> robots = new ArrayList<>();

    // Getter for the canvas
    public Canvas getCanvas() {
        return canvas;
    }

    // Setter for the arena
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    // Setter for the robots
    public void setRobots(List<MoveableRobot> robots) {
        this.robots = robots;
    }

    // Method to handle adding a robot
    @FXML
    private void handleAddRobot() {
        // Create a confirmation alert to choose position type
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Add Robot");
        alert.setHeaderText("Choose Position Type");
        alert.setContentText("Do you want a random or specified position?");

        // Define button types
        ButtonType buttonRandom = new ButtonType("Random");
        ButtonType buttonSpecified = new ButtonType("Specified");
        ButtonType buttonCancel = ButtonType.CANCEL;

        // Set the button types in the alert
        alert.getButtonTypes().setAll(buttonRandom, buttonSpecified, buttonCancel);

        // Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonRandom) {
                addRobotRandomPosition();
            } else if (result.get() == buttonSpecified) {
                addRobotSpecifiedPosition();
            }
        }
    }

    // Method to add a robot at a random position
    private void addRobotRandomPosition() {
        Random random = new Random();
        int xPos = random.nextInt(arena.getXSize());
        int yPos = random.nextInt(arena.getYSize());
        addRobot(xPos, yPos);
    }

    // Method to add a robot at a specified position
    private void addRobotSpecifiedPosition() {
        // Create a text input dialog to get the specified position
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Specified Position");
        dialog.setHeaderText("Enter X and Y positions separated by a comma");
        dialog.setContentText("Position:");

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] positions = result.get().split(",");
            if (positions.length == 2) {
                try {
                    int xPos = Integer.parseInt(positions[0].trim());
                    int yPos = Integer.parseInt(positions[1].trim());
                    addRobot(xPos, yPos);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter valid integer positions.");
                }
            } else {
                showAlert("Invalid Input", "Please enter two positions separated by a comma.");
            }
        }
    }

    // Method to add a robot with the given position
    private void addRobot(int xPos, int yPos) {
        if (robots.size() >= 0) {
            //create a new basic robot
            //later add different types of robots
            MoveableRobot basicRobot = new MoveableRobot("Robot", 0.2, xPos, yPos, 3, 0, arena, 5, 5);

            //add the robot to the list of robots in arena
            robots.add(basicRobot);
            arena.setRobots(robots);
        }
    }

    // Method to handle removing a robot
    @FXML
    private void handleRemoveRobot() {
        // Logic to remove a robot
        if (!robots.isEmpty()) {
            robots.remove(robots.size() - 1);
            arena.setRobots(robots);
        } else {
            showAlert("Remove Robot", "No robots to remove.");
        }
    }

    // Method to handle creating a new arena
    @FXML
    private void handleCreateNewArena() {
        // Logic to create a new arena
        arena = new Arena(50, 50, null, null);
        robots.clear();
        arena.setRobots(robots);
        showAlert("Create New Arena", "New arena created.");
    }

    // Method to show an alert with a given title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}