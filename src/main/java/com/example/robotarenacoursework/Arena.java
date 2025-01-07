package com.example.robotarenacoursework;

public class Arena {
    //an arena will need
    //a size, a list of robots, and a list of obstacles
    private int xSize, ySize;
    private Robot[] robots;
    private Obstacle[] obstacles;

    public Arena(int xSize, int ySize, Robot[] robots, Obstacle[] obstacles){
        this.xSize = xSize;
        this.ySize = ySize;
        this.robots = robots;
        this.obstacles = obstacles;
    }

    //getters
    public int getXSize(){
        return xSize;
    }
    public int getYSize(){
        return ySize;
    }
    public Robot[] getRobots(){
        return robots;
    }
    public Obstacle[] getObstacles(){
        return obstacles;
    }
    public Robot[] setRobots(Robot[] robots){
        return this.robots = robots;
    }
    public Obstacle[] setObstacles(Obstacle[] obstacles){
        return this.obstacles = obstacles;
    }

    //Method to check and correct positions if they are out of bounds
    public void checkBounds(Robot robot){
        if (robot.getXPos() < 0) {
            robot.setXPos(0);
        }
        if (robot.getXPos() > xSize) {
            robot.setXPos(xSize - 1);
        }
        if (robot.getYPos() < 0) {
            robot.setYPos(0);
        }
        if (robot.getYPos() > ySize) {
            robot.setYPos(ySize - 1);
        }
    }

    //main method for testing
    public static void main(String[] args){
        Robot[] robots = new Robot[2];
        robots[0] = new Robot("Robot1", 5, 4, 4, 3, 0);
        robots[1] = new Robot("Robot2", 5, 6, 6, 3, 0);

        Obstacle[] obstacles = new Obstacle[2];
        obstacles[0] = new Obstacle(5, 0, 0, "rock");
        obstacles[1] = new Obstacle(5, 5, 5, "rock");


        Arena arena = new Arena(10, 10, robots, obstacles);
        System.out.println(arena.xSize);
        System.out.println(arena.ySize);
        System.out.println(arena.robots[0].getName());
        System.out.println(arena.robots[1].getName());
        System.out.println(arena.obstacles[0]);
        System.out.println(arena.obstacles[1]);
    }
}
