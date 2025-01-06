package com.example.robotarenacoursework;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class GUI extends Application {
    private CustomCanvas customCanvas;
    private static final int SCALE = 10; //Scaling factor

    @Override
    public void start(Stage primaryStage) {
        // Create the arena, robots, and obstacles
        MoveableRobot[] robots = new MoveableRobot[2];//array sized 2 for testing
        robots[0] = new MoveableRobot("Robot1", 5, 20, 30, 3, 0);
        robots[1] = new MoveableRobot("Robot2", 5, 20, 20, 3, 0);

        Obstacle[] obstacles = new Obstacle[2]; //array sized 2 for testing
        obstacles[0] = new Obstacle(5, 0, 0, "rock");
        obstacles[1] = new Obstacle(5, 5, 5, "sand");

        // Initialize the arena with the robots and obstacles
        Arena arena = new Arena(50, 50, robots, obstacles);
        this.customCanvas = new CustomCanvas(arena);

        // Set up the JavaFX canvas
        int canvasWidth = arena.getXSize() * SCALE;
        int canvasHeight = arena.getYSize() * SCALE;
        Canvas fxCanvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        // Set up the scene and stage
        StackPane root = new StackPane();
        root.getChildren().add(fxCanvas);
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, canvasWidth, canvasHeight);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Robot Arena");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Draw the initial state of the arena, robots, and obstacles
        this.customCanvas.draw(gc);

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

