package com.example.robotarenacoursework;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int xSize, ySize;
    private ArrayList<MoveableRobot> robots;
    private ArrayList<Obstacle> obstacles;

    public Arena(int xSize, int ySize, ArrayList<MoveableRobot> robots, ArrayList<Obstacle> obstacles) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.robots = robots == null ? new ArrayList<>() : robots;
        this.obstacles = obstacles == null ? new ArrayList<>() : obstacles;
    }

    public Arena(ArrayList<MoveableRobot> robots, ArrayList<Obstacle> obstacles) {
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public ArrayList<MoveableRobot> getRobots() {
        return robots;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setRobots(ArrayList<MoveableRobot> robots) {
        this.robots = robots;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void addRobot(MoveableRobot robot) {
        this.robots.add(robot);
    }
}