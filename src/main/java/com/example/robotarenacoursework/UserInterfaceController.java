package com.example.robotarenacoursework;

/*
    * This class is used to create a UserInterfaceController object
    * It is used to handle the user interface of the application
    * It is used to handle user input and events
    * It is used to interact with the GUI and Arena classes
    * It is used to create and manage robots and obstacles
    * It is used to save and load arenas
    * It is used to start and stop the simulation
    * It is used to show alerts and dialogs
    * It is used to show the about and help dialogs
 */

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

// Controller class for the user interface
public class userInterfaceController {
    private CustomCanvas customCanvas;
    private GUI gui;
    private Arena arena;
    private boolean isRunning = false;

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

    // Method to get the running status
    public boolean isRunning() {
        return isRunning;
    }

    // Method to handle adding a robot
    @FXML
    private void handleAddRobot() {
        if (arena == null) {
            showAlert("Error", "Arena is not initialized.");
            return;
        }
        // Create a choice dialog to select the robot type
        List<String> choices = List.of("'Normal' Robot", "Sensor Robot", "Bumper Robot", "Beam Robot", "Fast Robot", "Heavy Robot"); // List of robot types
        ChoiceDialog<String> dialog = new ChoiceDialog<>("'Normal' Robot", choices); // Create a choice dialog with the list of robot types
        dialog.setTitle("Select Robot Type"); // Set the title of the dialog
        dialog.setHeaderText("Choose the type of robot to create:"); // Set the header text of the dialog
        dialog.setContentText("Robot Type:"); // Set the content text of the dialog

        Optional<String> result = dialog.showAndWait(); // Show the dialog and wait for user selection
        if (result.isPresent()) { // If the user has selected a robot type
            String selectedRobot = result.get(); // Get the selected robot type
            Alert alert = new Alert(AlertType.CONFIRMATION); // Create a confirmation alert
            alert.setTitle("Add Robot");    // Set the title of the alert
            alert.setHeaderText("Choose Position Type"); // Set the header text of the alert
            alert.setContentText("Do you want a random or specified position?"); // Set the content text of the alert

            ButtonType buttonRandom = new ButtonType("Random"); // Create a button type for random position
            ButtonType buttonSpecified = new ButtonType("Specified");  // Create a button type for specified position
            ButtonType buttonCancel = ButtonType.CANCEL; // Create a button type for cancel

            alert.getButtonTypes().setAll(buttonRandom, buttonSpecified, buttonCancel); // Set the button types in the alert

            Optional<ButtonType> positionResult = alert.showAndWait(); // Show the alert and wait for user response
            if (positionResult.isPresent()) { // If the user has selected a position type
                if (positionResult.get() == buttonRandom) { // If the user has selected random position
                    addRobotRandomPosition(selectedRobot); // Add a robot at a random position
                } else if (positionResult.get() == buttonSpecified) { // If the user has selected specified position
                    addRobotSpecifiedPosition(selectedRobot); // Add a robot at a specified position
                }
            }
        }
    }

    private void addRobotRandomPosition(String robotType) {
        Random random = new Random(); // Create a new random instance
        int xPos = random.nextInt(arena.getXSize() - 20) + 10; // Generate a random x position
        int yPos = random.nextInt(arena.getYSize() - 20) + 10; // Generate a random y position
        addRobot(robotType, xPos, yPos);
    }

    private void addRobotSpecifiedPosition(String robotType) { // Method to add a robot at a specified position
        TextInputDialog dialog = new TextInputDialog(); // Create a text input dialog
        dialog.setTitle("Specified Position"); // Set the title of the dialog
        dialog.setHeaderText("Enter X and Y positions separated by a comma"); // Set the header text of the dialog
        dialog.setContentText("Position:"); // Set the content text of the dialog

        Optional<String> result = dialog.showAndWait();     // Show the dialog and wait for user input
        if (result.isPresent()) {
            String[] positions = result.get().split(","); // Split the input by comma
            if (positions.length == 2) {
                try {
                    int xPos = Integer.parseInt(positions[0].trim()); // Parse the x position
                    int yPos = Integer.parseInt(positions[1].trim()); // Parse the y position
                    if (xPos >= 10 && xPos <= arena.getXSize() - 10 && yPos >= 10 && yPos <= arena.getYSize() - 10) {
                        addRobot(robotType, xPos, yPos);
                    } else {
                        showAlert("Invalid Input", "Position out of bounds.");
                    }
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
        MoveableRobot robot;
        // Create a robot based on the selected type
        if (robotType.equals("Sensor Robot")) {
            robot = new SensorRobot("SensorRobot", 2, 5, 1, 1, xPos, yPos, 2, 0, arena, 20, 20);
        } else if (robotType.equals("Bumper Robot")) {
            robot = new BumperRobot("BumperRobot", 2, 5, 1, 1, xPos, yPos, 1, 0, arena, 20, 20, 25);
        } else if (robotType.equals("Beam Robot")) {
            robot = new BeamRobot("BeamRobot", 2, 5, 1, 1, xPos, yPos, 0, 0, arena, 20, 20, 50);
        } else if (robotType.equals("Fast Robot")) {
            robot = new FastRobot("FastRobot", 2, 5, 1, 1, xPos, yPos, 0, 0, arena, 20, 20);
        } else if (robotType.equals("HeavyRobot")) {
            robot = new HeavyRobot("HeavyRobot", 2, 5, 1, 1, xPos, yPos, 0, 0, arena, 20, 20);
        } else {
            robot = new MoveableRobot("Robot", 2, 5, 1, 1, xPos, yPos, 0, 0, arena, 20, 20);
        }
        // Add the robot to the list of robots
        robots.add(robot);
        arena.setRobots(robots);
        gui.setRobots(robots);
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
            int xSize = Integer.parseInt(xSizeField.getText()); // Parse the x size
            int ySize = Integer.parseInt(ySizeField.getText()); // Parse the y size

            if (xSize < 100 || xSize > 1000 || ySize < 100 || ySize > 800) {
                System.out.println("Invalid dimensions. X must be between 100 and 1000, and Y must be between 100 and 800.");
                return;
            }

            Arena arena = new Arena(xSize, ySize, robots, null);
            setArena(arena); // Set the arena in the controller
            gui.setArena(arena); // Set the arena in the GUI class
            System.out.println("New arena created with dimensions: " + xSize + "x" + ySize);

            int maxWidth = 1000; // Maximum width of the window
            int maxHeight = 800; // Maximum height of the window
            canvas.setWidth(xSize); // Set the width of the canvas
            canvas.setHeight(ySize); // Set the height of the canvas
            Stage stage = (Stage) canvas.getScene().getWindow();
            stage.setWidth(maxWidth + 200);
            stage.setHeight(maxHeight + 100);

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gui.getCustomCanvas().draw(gc); // Ensure draw method is called
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for the dimensions.");
        }
    }

    // Method to handle adding an obstacle
    @FXML
    private void handleAddObstacle() {
        if (arena == null) {
            showAlert("Error", "Arena is not initialized.");
            return;
        }

        // Create a choice dialog to select the obstacle type
        List<String> choices = List.of("Rock", "Sand", "Speed", "Teleporter"); // List of obstacle types
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Rock", choices); // Create a choice dialog with the list of obstacle types
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
        if (arena == null) {
            showAlert("Error", "Arena is not initialized.");
            return;
        }

        // Generate a random position and size for the obstacle
        Random random = new Random();
        int xPos = random.nextInt(arena.getXSize());
        int yPos = random.nextInt(arena.getYSize());
        double width = 10 + random.nextDouble() * 40; // Random width between 10 and 50
        double height = 10 + random.nextDouble() * 40; // Random height between 10 and 50
        addObstacle(obstacleType, xPos, yPos, width, height);
    }

    // Method to add an obstacle at a specified position
    private void addObstacleSpecifiedPosition(String obstacleType) {
        if (arena == null) {
            showAlert("Error", "Arena is not initialized.");
            return;
        }

        // Create a text input dialog to get the specified position
        TextInputDialog positionDialog = new TextInputDialog();
        positionDialog.setTitle("Specified Position");
        positionDialog.setHeaderText("Enter X and Y positions separated by a comma");
        positionDialog.setContentText("Position:");

        // Show the dialog and wait for user input
        Optional<String> positionResult = positionDialog.showAndWait();
        if (positionResult.isPresent()) {
            String[] positions = positionResult.get().split(","); // Split the input by comma
            if (positions.length == 2) {
                try {
                    int xPos = Integer.parseInt(positions[0].trim()); // Parse the x position
                    int yPos = Integer.parseInt(positions[1].trim()); // Parse the y position

                    // Create a text input dialog to get the specified size
                    TextInputDialog sizeDialog = new TextInputDialog(); // Create a text input dialog
                    sizeDialog.setTitle("Specified Size"); // Set the title of the dialog
                    sizeDialog.setHeaderText("Enter width and height separated by a comma"); // Set the header text of the dialog
                    sizeDialog.setContentText("Size:"); // Set the content text of the dialog

                    // Show the dialog and wait for user input
                    Optional<String> sizeResult = sizeDialog.showAndWait();
                    if (sizeResult.isPresent()) {
                        String[] sizes = sizeResult.get().split(",");
                        if (sizes.length == 2) {
                            try {
                                double width = Double.parseDouble(sizes[0].trim()); // Parse the width
                                double height = Double.parseDouble(sizes[1].trim());// Parse the height
                                addObstacle(obstacleType, xPos, yPos, width, height); // Add the obstacle
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

    // Method to add an obstacle with the given position and size
    private void addObstacle(String obstacleType, int xPos, int yPos, double width, double height) {
        Obstacle obstacle;
        // Create an obstacle based on the selected type
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

    // Method to handle removing an obstacle
    @FXML
    private void handleRemoveObstacle() {
        if (!obstacles.isEmpty()) {
            obstacles.remove(obstacles.size() - 1);
            arena.setObstacles(obstacles);
        } else {
            showAlert("Remove Obstacle", "No obstacles to remove.");
        }
    }

    // Method to initialize sliders
    private void intialiseSliders(){
        maxSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> { // Add a listener to the max speed slider
            for (MoveableRobot robot : robots) { // Loop through the list of robots
                robot.setMaxSpeed(newValue.doubleValue()); // Set the max speed of the robot
            }
        });

        minSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> { // Add a listener to the min speed slider
            for (MoveableRobot robot : robots) { // Loop through the list of robots
                robot.setMinSpeed(newValue.doubleValue()); // Set the min speed of the robot
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

    private void showTextDialog(String title, String message) { // Method to show a dialog with text content
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);

        // Set the button types
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

        // Create the content
        TextArea textArea = new TextArea(message);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        // Set the content in the dialog pane
        dialog.getDialogPane().setContent(textArea);

        dialog.showAndWait();
    }

    // Method to handle the open arena button
    @FXML
    private void handleOpenArena() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Arena");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt")); // Add a filter for text files
        File file = fileChooser.showOpenDialog(null); // Show the file chooser dialog
        if (file != null) {
            Arena loadedArena = FileHandler.loadArena(file.getPath()); // Load the arena from the file
            if (loadedArena != null) {
                setArena(loadedArena);// Set the arena in the controller
                gui.setArena(loadedArena);
                showAlert("Open Arena", "Arena loaded successfully.");
            } else {
                showAlert("Error", "Failed to load arena.");
            }
        }
    }

    // Method to handle the save arena button
    @FXML
    private void handleSaveArena() {
        if (arena == null) {
            showAlert("Error", "No arena to save.");
            return;
        }

        FileChooser fileChooser = new FileChooser(); // Create a new file chooser
        fileChooser.setTitle("Save Arena"); // Set the title of the file chooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt")); // Add a filter for text files
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            FileHandler.saveArena(arena, file.getPath());
            showAlert("Save Arena", "Arena saved successfully.");
        }
    }

    // Method to handle the exit button
    @FXML
    private void handleExit() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();
    }

    // Method to handle the start button
    @FXML
    private void handleStart() {
        if (arena == null) {
            showAlert("Error", "No arena to start.");
            return;
        }
        isRunning = true;
        showAlert("Start Arena", "Arena started.");
    }

    // Method to handle the stop button
    @FXML
    private void handleStop() {
        if (arena == null) {
            showAlert("Error", "No arena to stop.");
            return;
        }
        isRunning = false;
        showAlert("Stop Arena", "Arena stopped.");
    }

    // Method to handle mouse pressed event
    @FXML
    public void handleMousePressed(MouseEvent event) {
        if (arena != null) {
            arena.handleMousePressed(event, this);
        }
    }

    // Method to handle mouse dragged event
    @FXML
    public void handleMouseDragged(MouseEvent event) {
        if (arena != null) {
            arena.handleMouseDragged(event, this);
        }
    }

    // Method to handle mouse released event
    @FXML
    public void handleMouseReleased(MouseEvent event) {
        if (arena != null) {
            arena.handleMouseReleased(event, this);
        }
    }

    public void redrawCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gui.getCustomCanvas().draw(gc);
    }

    public void handleDeleteSelected() {
        if (arena != null) {
            arena.deleteSelected();
            redrawCanvas();
        }
    }

    @FXML
    public void showAbout() {
        String aboutText = "Robot Arena Simulation\n" +
                "Version: 1.0\n" +
                "This simulation demonstrates the behaviour of different types of robots in an arena.\n" +
                "Developed by: Kieran Thompson. \n" +
                "Student Number: 32025125";
        showTextDialog("About", aboutText);
    }

    @FXML
    public void showHelp() {
        String helpText = "Robot Arena Simulation Help\n" +
                "1. Create a new arena by entering the dimensions and clicking 'Create Arena'.\n" +
                "2. Add robots and obstacles to the arena using the buttons provided.\n" +
                "3. Save and load arenas using the respective buttons in file(WIP).\n" +
                "4. Start and stop the simulation using the 'Start' and 'Stop' buttons.\n" +
                "5. Click and drag to move robots and obstacles around the arena when it is stopped.\n" +
                "6. Delete selected robots or obstacles using the 'Delete Selected' button." +
                "7. Default arena can be created using the 'Default Arena' button.(WIP)";
        showTextDialog("Help", helpText);
    }

    @FXML
    private void handleDefaultArena(){
        gui.createDefaultArena();
    }
}