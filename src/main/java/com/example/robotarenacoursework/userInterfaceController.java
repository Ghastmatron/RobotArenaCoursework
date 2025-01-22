package com.example.robotarenacoursework;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class UserInterfaceController {
    private CustomCanvas customCanvas;
    private GUI gui;

    @FXML
    private Canvas canvas;
    @FXML
    private Slider maxSpeedSlider;
    @FXML
    private Slider minSpeedSlider;
    @FXML
    private TextField xSizeField;
    @FXML
    private TextField ySizeField;

    private Arena arena;
    private ArrayList<MoveableRobot> robots = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    // Getter for the canvas
    public Canvas getCanvas() {
        return canvas;
    }

    // Setter for the arena
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    // Setter for the robots
    public void setRobots(ArrayList<MoveableRobot> robots) {
        this.robots = robots;
        intialiseSliders();
    }

    // Setter for the obstacles
    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    // Setter for the GUI
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    // Method to handle adding a robot
    @FXML
    private void handleAddRobot() {
        // Create a choice dialog to select the robot type
        List<String> choices = List.of("'Normal' Robot", "Sensor Robot", "Bumper Robot");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("'Normal' Robot", choices);
        dialog.setTitle("Select Robot Type");
        dialog.setHeaderText("Choose the type of robot to create:");
        dialog.setContentText("Robot Type:");

        // Show the dialog and wait for user selection
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedRobot = result.get();
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
            Optional<ButtonType> positionResult = alert.showAndWait();
            if (positionResult.isPresent()) {
                if (positionResult.get() == buttonRandom) {
                    addRobotRandomPosition(selectedRobot);
                } else if (positionResult.get() == buttonSpecified) {
                    addRobotSpecifiedPosition(selectedRobot);
                }
            }
        }
    }

    // Method to add a robot at a random position
    private void addRobotRandomPosition(String robotType) {
        Random random = new Random();
        int xPos = random.nextInt(arena.getXSize());
        int yPos = random.nextInt(arena.getYSize());
        addRobot(robotType, xPos, yPos);
    }

    // Method to add a robot at a specified position
    private void addRobotSpecifiedPosition(String robotType) {
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
                    addRobot(robotType, xPos, yPos);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter valid integer positions.");
                }
            } else {
                showAlert("Invalid Input", "Please enter two positions separated by a comma.");
            }
        }
    }

    // Method to add a robot with the given position
    private void addRobot(String robotType, int xPos, int yPos) {
        if (robots.size() >= 0) {
            MoveableRobot robot;
            if (robotType.equals("Sensor Robot")) {
                robot = new SensorRobot("SensorRobot", 0.2, 5, 5, 1, xPos, yPos, 2, 0, arena, 20, 20);
            } else if(robotType.equals("Bumper Robot")) {
                robot = new BumperRobot("BumperRobot", 0.2, 5, 5, 1, xPos, yPos, 1, 0, arena, 20, 20, 25);
            } else {
                robot = new MoveableRobot("BasicRobot", 0.2, 5, 5, 1, xPos, yPos, 0, 0, arena, 20, 20);
            }
            robots.add(robot);
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
        try {
            int xSize = Integer.parseInt(xSizeField.getText());
            int ySize = Integer.parseInt(ySizeField.getText());

            if (xSize < 100 || xSize > 1000 || ySize < 100 || ySize > 800) {
                System.out.println("Invalid dimensions. X must be between 100 and 1000, and Y must be between 100 and 800.");
                return;
            }

            Arena arena = new Arena(xSize, ySize, robots, null);
            gui.setArena(arena); // Set the arena in the GUI class
            System.out.println("New arena created with dimensions: " + xSize + "x" + ySize);

            int maxWidth = 1000;
            int maxHeight = 800;
            canvas.setWidth(maxWidth);
            canvas.setHeight(maxHeight);
            Stage stage = (Stage) canvas.getScene().getWindow();
            stage.setWidth(maxWidth + 200);
            stage.setHeight(maxHeight + 100);

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gui.getCustomCanvas().draw(gc); // Ensure draw method is called
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for the dimensions.");
        }
    }

    //Method to handle adding an obstacle
    @FXML
    private void handleAddObstacle() {
        // Create a choice dialog to select the obstacle type
        List<String> choices = List.of("Rock", "Sand", "Speed", "Teleporter");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Rock", choices);
        dialog.setTitle("Select Obstacle Type");
        dialog.setHeaderText("Choose the type of obstacle to create:");
        dialog.setContentText("Obstacle Type:");

        // Show the dialog and wait for user selection
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedObstacle = result.get();
            // Create a confirmation alert to choose position type
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Add Obstacle");
            alert.setHeaderText("Choose Position Type");
            alert.setContentText("Do you want a random or specified position?");

            // Define button types
            ButtonType buttonRandom = new ButtonType("Random");
            ButtonType buttonSpecified = new ButtonType("Specified");
            ButtonType buttonCancel = ButtonType.CANCEL;

            // Set the button types in the alert
            alert.getButtonTypes().setAll(buttonRandom, buttonSpecified, buttonCancel);

            // Show the alert and wait for user response
            Optional<ButtonType> positionResult = alert.showAndWait();
            if (positionResult.isPresent()) {
                if (positionResult.get() == buttonRandom) {
                    addObstacleRandomPosition(selectedObstacle);
                } else if (positionResult.get() == buttonSpecified) {
                    addObstacleSpecifiedPosition(selectedObstacle);
                }
            }
        }
    }

    // Method to add an obstacle at a random position
    private void addObstacleRandomPosition(String obstacleType) {
        Random random = new Random();
        int xPos = random.nextInt(arena.getXSize());
        int yPos = random.nextInt(arena.getYSize());
        double width = 10 + random.nextDouble() * 40; // Random width between 10 and 50
        double height = 10 + random.nextDouble() * 40; // Random height between 10 and 50
        addObstacle(obstacleType, xPos, yPos, width, height);
    }


    private void addObstacleSpecifiedPosition(String obstacleType) {
        // Create a text input dialog to get the specified position
        TextInputDialog positionDialog = new TextInputDialog();
        positionDialog.setTitle("Specified Position");
        positionDialog.setHeaderText("Enter X and Y positions separated by a comma");
        positionDialog.setContentText("Position:");

        // Show the dialog and wait for user input
        Optional<String> positionResult = positionDialog.showAndWait();
        if (positionResult.isPresent()) {
            String[] positions = positionResult.get().split(",");
            if (positions.length == 2) {
                try {
                    int xPos = Integer.parseInt(positions[0].trim());
                    int yPos = Integer.parseInt(positions[1].trim());

                    // Create a text input dialog to get the specified size
                    TextInputDialog sizeDialog = new TextInputDialog();
                    sizeDialog.setTitle("Specified Size");
                    sizeDialog.setHeaderText("Enter width and height separated by a comma");
                    sizeDialog.setContentText("Size:");

                    // Show the dialog and wait for user input
                    Optional<String> sizeResult = sizeDialog.showAndWait();
                    if (sizeResult.isPresent()) {
                        String[] sizes = sizeResult.get().split(",");
                        if (sizes.length == 2) {
                            try {
                                double width = Double.parseDouble(sizes[0].trim());
                                double height = Double.parseDouble(sizes[1].trim());
                                addObstacle(obstacleType, xPos, yPos, width, height);
                            } catch (NumberFormatException e) {
                                showAlert("Invalid Input", "Please enter valid double values for size.");
                            }
                        } else {
                            showAlert("Invalid Input", "Please enter two size values separated by a comma.");
                        }
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter valid integer positions.");
                }
            } else {
                showAlert("Invalid Input", "Please enter two positions separated by a comma.");
            }
        }
    }

    private void addObstacle(String obstacleType, int xPos, int yPos, double width, double height) {
        Obstacle obstacle;
        if (obstacleType.equals("Rock")) {
            obstacle = new BounceObstacle(xPos, yPos, width, height);
        } else if (obstacleType.equals("Sand")) {
            obstacle = new SlowObstacle(xPos, yPos, width, height);
        } else if (obstacleType.equals("Speed")) {
            obstacle = new SpeedObstacle(xPos, yPos, width, height);
        } else {
            obstacle = new TeleportObstacle(xPos, yPos, width, height, arena);
        }
        obstacles.add(obstacle);
        arena.setObstacles(obstacles);
    }

    private void handleRemoveObstacle() {
        if (!obstacles.isEmpty()) {
            obstacles.remove(obstacles.size() - 1);
            arena.setObstacles(obstacles);
        } else {
            showAlert("Remove Obstacle", "No obstacles to remove.");
        }
    }

    private void intialiseSliders(){
        maxSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            for (MoveableRobot robot : robots) {
                robot.setMaxSpeed(newValue.doubleValue());
            }
        });

        minSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            for (MoveableRobot robot : robots) {
                robot.setMinSpeed(newValue.doubleValue());
            }
        });
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