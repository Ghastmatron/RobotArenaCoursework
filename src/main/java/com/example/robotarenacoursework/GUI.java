package com.example.robotarenacoursework;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GUI extends Application {
    private CustomCanvas customCanvas;
    private static final int SCALE = 10; //Scaling factor

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/robotarenacoursework/UserInterface.fxml"));
        Parent root = loader.load();

        // Get the controller
        UserInterfaceController controller = loader.getController();

        // Create the arena, robots, and obstacles
        Arena arena = new Arena(50, 50, null, null);

        // Create robots
        MoveableRobot[] robots = new MoveableRobot[2]; // array sized 2 for testing
        robots[0] = new MoveableRobot("Robot1", 1, 20, 30, 3, 0, arena);
        robots[1] = new MoveableRobot("Robot2", 1, 20, 20, 3, 0, arena);

        // Create obstacles
        Obstacle[] obstacles = new Obstacle[2]; // array sized 2 for testing
        obstacles[0] = new Obstacle(5, 0, 0, "rock");
        obstacles[1] = new Obstacle(5, 5, 5, "sand");

        // Initialize the arena with the robots and obstacles using the set methods
        arena.setRobots(robots);
        arena.setObstacles(obstacles);

        this.customCanvas = new CustomCanvas(arena);

        // Set arena and robots in the controller
        controller.setArena(arena);
        controller.setRobots(robots);

        // Get the canvas from the FXML
        Canvas fxCanvas = controller.getCanvas();
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        // Draw the initial state of the arena, robots, and obstacles
        this.customCanvas.draw(gc);

        // Set up the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Robot Arena");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set up the event handler
        RobotEventHandler eventHandler = new RobotEventHandler();
        eventHandler.addEventHandlers(scene, robots);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                eventHandler.updateRobots(robots);
                // Draw the updated state of the arena, robots, and obstacles
                customCanvas.draw(gc);
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}