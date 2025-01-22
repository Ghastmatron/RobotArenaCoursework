package com.example.robotarenacoursework;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {
    private CustomCanvas customCanvas;
    private static final int SCALE = 10; // Scaling factor
    private ArrayList<MoveableRobot> robots = new ArrayList<>(); // Use a dynamic list
    private Arena arena; // Class-level variable for arena

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/robotarenacoursework/UserInterface.fxml"));
            Parent root = loader.load();

            // Get the controller
            UserInterfaceController controller = loader.getController();
            controller.setGUI(this); // Set the GUI instance in the controller

            // Set up the scene and stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("Robot Arena");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();

            // Set up the event handler
            RobotEventHandler eventHandler = new RobotEventHandler();
            eventHandler.addEventHandlers(scene, robots);
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (robots.size() == 0) {
                        System.out.println("No robots");
                    } else {
                        eventHandler.updateRobots(robots);
                    }
                    // Clear the canvas
                    GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
                    gc.clearRect(0, 0, controller.getCanvas().getWidth(), controller.getCanvas().getHeight());
                    // Draw the updated state of the arena, robots, and obstacles if the arena exists
                    if (arena != null) {
                        customCanvas.draw(gc);
                        System.out.println("Drawing");
                    }
                }
            };
            timer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setArena(Arena arena) {
        this.arena = arena;
        customCanvas = new CustomCanvas(arena);
    }

    public CustomCanvas getCustomCanvas() {
        return customCanvas;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
