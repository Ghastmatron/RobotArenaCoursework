package com.example.robotarenacoursework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class GUI extends Application {
    private CustomCanvas customCanvas;

    @Override
    public void start(Stage primaryStage) {
        // Create the arena, robots, and obstacles
        Robot[] robots = new Robot[2];
        robots[0] = new Robot("Robot1", 5, 10, 10, 3, 0);
        robots[1] = new Robot("Robot2", 5, 0, 0, 3, 0);

        Obstacle[] obstacles = new Obstacle[2];
        obstacles[0] = new Obstacle(5, 0, 0, "rock");
        obstacles[1] = new Obstacle(5, 5, 5, "rock");

        // Initialize the arena with the robots and obstacles
        Arena arena = new Arena(500, 500, robots, obstacles);
        this.customCanvas = new CustomCanvas(arena);

        // Set up the JavaFX canvas
        int canvasWidth = arena.getXSize();
        int canvasHeight = arena.getYSize();
        Canvas fxCanvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        // Set up the scene and stage
        StackPane root = new StackPane();
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, canvasWidth, canvasHeight);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Robot Arena");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Draw the initial state of the arena, robots, and obstacles
        this.customCanvas.draw(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

