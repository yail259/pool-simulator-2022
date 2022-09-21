package pool.ball;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import pool.HoleStrategy;
import pool.PoolObject;
import pool.table.Table;

public class Ball implements PoolObject {
    public static final double STATIONARY_THRESHOLD = 0.1;
    private String colour;
    private Color paintColour;
    private Point2D position;
    private Point2D initialPosition;
    private Point2D velocity;
    private double mass;
    private HoleStrategy thisBallHoleStrat;
    private final double radius = 5.0;

    public Ball(String colour, Color paintColour, Point2D position, Point2D velocity, double mass,
                HoleStrategy thisBallHoleStrat) {
        this.colour = colour;
        this.paintColour = paintColour;

        this.position = position;
        this.velocity = velocity;

        this.mass = mass;
        this.initialPosition = position;

        this.thisBallHoleStrat = thisBallHoleStrat;
    }

    public String getColour() {
        return colour;
    }

    public Color getPaintColour() {
        return paintColour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getInitialPosition() {
        return initialPosition;
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
        if (velocity.magnitude() < STATIONARY_THRESHOLD) {
            this.velocity = (new Point2D(0, 0));
            return;
        }

        this.velocity = velocity;
    }

    public void setVelocity(double x, double y) {
        this.setVelocity(new Point2D(x, y));
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

    /**
     * Increments the mechanics of the game, allows velocity to be applied to position and friction to work.
     * @param frictionCoeff
     */
    public void tick(double frictionCoeff) {
        this.setPosition(this.position.add(this.getVelocity()));

        this.setVelocity(this.velocity.multiply(frictionCoeff));
    }

    // strategy method for different behaviour when entering the hole
    public void enterHole(Table table) {
        this.thisBallHoleStrat.enterHole(this, table);
    }
}
