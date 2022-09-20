package pool.ball;

import pool.Builder;
import javafx.geometry.Point2D;

public class BallBuilder implements Builder {
    private String colour;
    private Point2D position;
    private Point2D velocity;
    private double mass;
    private int life;

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    @Override
    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public void setLife(int life) {
        this.life = life;
    }

    public Ball getResult() {
        return new Ball(colour, position, velocity, mass, life);
    }
}
