package com.example.robotarenacoursework;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.Random;

import java.util.ArrayList;
import java.util.Random;

// Main class for the GUI
public class GUI extends Application {
    private CustomCanvas customCanvas;
    private InfoPanel infoPanel;
    private ArrayList<MoveableRobot> robots = new ArrayList<>(); // Use a dynamic list
    private ArrayList<Obstacle> obstacles = new ArrayList<>(); // Use a dynamic list
    private Arena arena; // Class-level variable for arena

    // Start method for the GUI
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/robotarenacoursework/UserInterface.fxml"));
            Parent root = loader.load(); // Load the FXML file

            // Get the controller
            UserInterfaceController controller = loader.getController();
            controller.setGUI(this); // Set the GUI instance in the controller

            customCanvas = new CustomCanvas(null); // Create a new CustomCanvas instance
            infoPanel = new InfoPanel(); // Create a new InfoPanel instance

            // Create an HBox to hold the canvas and the info root
            HBox hbox = new HBox(); // Create a new HBox
            hbox.getChildren().addAll(infoPanel, root); // Add the info panel and the root to the HBox

            // Set up the scene and stage
            BorderPane mainLayout = new BorderPane();
            mainLayout.setCenter(hbox);

            // Set up the scene and stage
            Scene scene = new Scene(mainLayout, 800, 600); // Create a new scene
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());  // Link the CSS file
            primaryStage.setTitle("Robot Arena"); // Set the title
            primaryStage.setScene(scene); // Set the scene
            primaryStage.setResizable(false); // Set the stage to not be resizable
            primaryStage.show(); // Show the stage

            // Set up the canvas
            Canvas canvas = controller.getCanvas();

            // Add event handlers for mouse events
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, controller::handleMousePressed);
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller::handleMouseDragged);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, controller::handleMouseReleased);


            // Set up the event handler
            RobotEventHandler eventHandler = new RobotEventHandler();
            eventHandler.addEventHandlers(scene, robots);
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) { // Handle method for the timer
                    if (controller.isRunning()) { // If the controller is running
                        if (robots.isEmpty()) {
                            // If there are no robots, pass
                        } else {
                            eventHandler.updateRobots(robots);
                        }
                        // Clear the canvas
                        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
                        gc.clearRect(0, 0, controller.getCanvas().getWidth(), controller.getCanvas().getHeight());
                        // Draw the updated state of the arena, robots, and obstacles if the arena exists
                        if (arena != null) {
                            customCanvas.setArena(arena);
                            customCanvas.draw(gc);
                            infoPanel.updateInfo(arena); // Update the info panel
                        }
                    }
                }
            };
            timer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Set the arena
    public void setArena(Arena arena) {
        this.arena = arena;
        if (customCanvas != null) {
            customCanvas.setArena(arena);
        }
        if (arena != null) {
            arena.setRobots(robots);
        }
        if (arena != null) {
            arena.setObstacles(obstacles);
        }
    }

    // Set the robots
    public void setRobots(ArrayList<MoveableRobot> robots) {
        this.robots = robots;
    }

    // get the Canvas
    public CustomCanvas getCustomCanvas() {
        return customCanvas;
    }

    public void createDefaultArena() {
        arena = new Arena(800, 600, null, null); // Create a new arena with default size
        arena.initializeDefaultArena(); // Initialize the default arena

        // Set the arena
        setArena(arena);

        Canvas canvas = customCanvas.getCanvas();
        canvas.setHeight(arena.getYSize());
        canvas.setWidth(arena.getXSize());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        customCanvas.draw(gc);
        infoPanel.updateInfo(arena);
    }

    public static void main(String[] args) {
        launch(args);
    }
}