package com.example.robotarenacoursework;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    // Method to save the arena state to a file
    public static void saveArena(Arena arena, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write arena dimensions
            writer.write(arena.getXSize() + "," + arena.getYSize());
            writer.newLine();
            // Write each robot's details
            for (MoveableRobot robot : arena.getRobots()) {
                writer.write("Robot," + robot.getClass().getSimpleName() + "," + robot.getXPos() + "," + robot.getYPos());
                writer.newLine();
            }
            // Write each obstacle's details
            for (Obstacle obstacle : arena.getObstacles()) {
                writer.write("Obstacle," + obstacle.getClass().getSimpleName() + "," + obstacle.getXPos() + "," + obstacle.getYPos() + "," + obstacle.getXSize() + "," + obstacle.getYSize());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the arena state from a file
    public static Arena loadArena(String filePath) {
        Arena arena = null;
        ArrayList<MoveableRobot> robots = new ArrayList<>();
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                // Read arena dimensions
                String[] dimensions = line.split(",");
                int xSize = Integer.parseInt(dimensions[0]);
                int ySize = Integer.parseInt(dimensions[1]);
                arena = new Arena(xSize, ySize, robots, obstacles);
            }
            // Read each robot and obstacle
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("Robot")) {
                    String type = parts[1];
                    double x = Double.parseDouble(parts[2]);
                    double y = Double.parseDouble(parts[3]);
                    MoveableRobot robot = createRobot(type, x, y, arena);
                    robots.add(robot);
                } else if (parts[0].equals("Obstacle")) {
                    String type = parts[1];
                    double x = Double.parseDouble(parts[2]);
                    double y = Double.parseDouble(parts[3]);
                    double width = Double.parseDouble(parts[4]);
                    double height = Double.parseDouble(parts[5]);
                    Obstacle obstacle = createObstacle(type, x, y, width, height, arena);
                    obstacles.add(obstacle);
                }
            }
            if (arena != null) {
                arena.setRobots(robots);
                arena.setObstacles(obstacles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arena;
    }

    // Method to create a robot based on its type
    private static MoveableRobot createRobot(String type, double x, double y, Arena arena) {
        switch (type) {
            case "SensorRobot":
                return new SensorRobot("SensorRobot", 0.2, 5, 5, 1, x, y, 2, 0, arena, 20, 20);
            case "BumperRobot":
                return new BumperRobot("BumperRobot", 0.2, 5, 5, 1, x, y, 1, 0, arena, 20, 20, 25);
            case "FastRobot":
                return new FastRobot("FastRobot", 0.3, 7.5, 5, 1, x, y, 0, 0, arena, 20, 20);
            case "HeavyRobot":
                return new HeavyRobot("HeavyRobot", 0.1, 2.5, 5, 1, x, y, 0, 0, arena, 30, 30);
            case "BeamRobot":
                return new BeamRobot("BeamRobot", 0.2, 5, 5, 1, x, y, 0, 0, arena, 20, 20, 100);
            default:
                return new MoveableRobot("BasicRobot", 0.2, 5, 5, 1, x, y, 0, 0, arena, 20, 20);
        }
    }

    // Method to create an obstacle based on its type
    private static Obstacle createObstacle(String type, double x, double y, double width, double height, Arena arena) {
        switch (type) {
            case "Rock":
                return new BounceObstacle(x, y, width, height);
            case "Sand":
                return new SlowObstacle(x, y, width, height);
            case "Speed":
                return new SpeedObstacle(x, y, width, height);
            default:
                return new TeleportObstacle(x, y, width, height, arena);
        }
    }
}