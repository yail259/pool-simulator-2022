package pool.ball;

import javafx.geometry.Point2D;
import pool.PoolObject;

public class Ball implements PoolObject {
    private String colour;
    private Point2D position;
    private Point2D initialPosition;
    private Point2D velocity;
    private double mass;
    private int life;
    private final double radius = 5.0;

    public Ball(String colour, Point2D position, Point2D velocity, double mass, int life) {
        this.colour = colour;
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.life = life;
        this.initialPosition = position;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }
    public void setVelocity(double x, double y) {

        this.velocity = new Point2D(x, y);
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void tick(double frictionCoeff) {
        this.position.add(this.getVelocity().getX(), this.getVelocity().getY());

        this.velocity.dotProduct(frictionCoeff, frictionCoeff);
    }
}
