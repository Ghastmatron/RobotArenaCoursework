package com.example.robotarenacoursework;

public class Obstacle {
    //an obstacle will need
    //a size, a position, and a type

    private int size, xPos, yPos;
    private String type;

    public Obstacle(int size, int xPos, int yPos, String type){
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
    }

    //getters
    public int getSize(){
        return size;
    }
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public String getType(){
        return type;
    }


    //main method for testing
    public static void main(String[] args){
        Obstacle obstacle = new Obstacle(5, 0, 0, "rock");
        System.out.println(obstacle.size);
        System.out.println(obstacle.xPos);
        System.out.println(obstacle.yPos);
        System.out.println(obstacle.type);
    }
}
