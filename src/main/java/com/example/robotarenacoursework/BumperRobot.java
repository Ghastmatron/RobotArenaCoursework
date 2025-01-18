package com.example.robotarenacoursework;

class BumperRobot extends MoveableRobot {
    private double ringRadius;

    public BumperRobot(String name, double speed, double maxSpeed, double minSpeed, double acceleration, double xPos, double yPos, int sensors, double direction, Arena arena, double xSize, double ySize, double ringRadius) {
        super(name, speed, maxSpeed, minSpeed, acceleration, xPos, yPos, sensors, direction, arena, xSize, ySize);
        this.ringRadius = ringRadius;
    }

    public double getRingRadius() {
        return ringRadius;
    }

    // Method to check if the ring is colliding with another robot
    public boolean checkRingCollision(MoveableRobot otherRobot) {
        double dx = this.getXPos() - otherRobot.getXPos();
        double dy = this.getYPos() - otherRobot.getYPos();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < this.ringRadius + Math.max(otherRobot.getXSize(), otherRobot.getYSize()) / 2;
    }

    // Override the update method to include ring collision logic
    @Override
    public void update() {
        for (MoveableRobot otherRobot : this.getArena().getRobots()) {
            if (otherRobot != this && checkRingCollision(otherRobot)) {
                // Make the other robot bounce away
                otherRobot.setDirection(otherRobot.getDirection() + 180 + Math.random() * 20 - 10);
                otherRobot.startDeccelerationTask();
            }
        }
        // Call the parent update method
        super.update();
    }
}