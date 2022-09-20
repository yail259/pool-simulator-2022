package pool.table;

import javafx.geometry.Point2D;
import pool.PoolObject;
import pool.ball.Ball;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Table implements PoolObject {
    private String colour;
    private Long x;
    private Long y;
    private double friction;
    private double frictionCoeff;

    private List<Ball> balls;
    private long tickCount;

    public Table(String colour, Long x, Long y, double friction) {
        this.colour = colour;
        this.x = x;
        this.y = y;
        this.friction = friction;
        this.frictionCoeff = 1 - friction/2;
        this.balls = new ArrayList<Ball>();
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getFrictionCoeff() {
        return frictionCoeff;
    }

    public void setFrictionCoeff(double frictionCoeff) {
        this.frictionCoeff = frictionCoeff;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public void addBall(Ball aBall) {
        this.balls.add(aBall);
    }

    private boolean checkCollision(Ball ballA, Ball ballB) {
        if (ballA == ballB) {
            return false;
        }

        return Math.abs(ballA.getPosition().getX() - ballB.getPosition().getX()) < ballA.getRadius() + ballB.getRadius()
                && Math.abs(ballA.getPosition().getX() - ballB.getPosition().getX()) < ballA.getRadius() + ballB.getRadius();
    }

    /**
     * This is an updated collision calculation function for 2 balls colliding in 2D space. You may use it however
     * you wish for your assignment.
     *
     * This uses the optimised physics algorithm discussed here:
     * http://www.gamasutra.com/view/feature/3015/pool_hall_lessons_fast_accurate_.php?page=3
     * which has been converted into Java/JavaFX
     *
     * @param positionA The coordinates of the centre of ball A
     * @param velocityA The delta x,y vector of ball A (how much it moves per tick)
     * @param massA The mass of ball A (for the moment this should always be the same as ball B)
     * @param positionB The coordinates of the centre of ball B
     * @param velocityB The delta x,y vector of ball B (how much it moves per tick)
     * @param massB The mass of ball B (for the moment this should always be the same as ball A)
     *
     * @return A Pair<Point2D, Point2D> in which the first (key) Point2D is the new delta x,y vector for ball A, and the second (value) Point2D is the new delta x,y vector for ball B.
     */
    public static Pair<Point2D, Point2D> calculateCollision(Ball ballA, Ball ballB) {

        Point2D positionA = ballA.getPosition();
        Point2D velocityA = ballA.getVelocity();
        double massA = ballA.getMass();
        Point2D positionB = ballB.getPosition();
        Point2D velocityB = ballB.getVelocity();
        double massB = ballB.getMass();

        // Find the angle of the collision - basically where is ball B relative to ball A. We aren't concerned with
        // distance here, so we reduce it to unit (1) size with normalize() - this allows for arbitrary radii
        Point2D collisionVector = positionA.subtract(positionB);
        collisionVector = collisionVector.normalize();

        // Here we determine how 'direct' or 'glancing' the collision was for each ball
        double vA = collisionVector.dotProduct(velocityA);
        double vB = collisionVector.dotProduct(velocityB);

        // If you don't detect the collision at just the right time, balls might collide again before they leave
        // each others' collision detection area, and bounce twice.
        // This stops these secondary collisions by detecting
        // whether a ball has already begun moving away from its pair, and returns the original velocities
        if (vB <= 0 && vA >= 0) {
            return new Pair<>(velocityA, velocityB);
        }

        // This is the optimisation function described in the gamasutra link. Rather than handling the full quadratic
        // (which as we have discovered allowed for sneaky typos)
        // this is a much simpler - and faster - way of obtaining the same results.
        double optimizedP = (2.0 * (vA - vB)) / (massA + massB);

        // Now we apply that calculated function to the pair of balls to obtain their final velocities
        Point2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
        Point2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));

        return new Pair<>(velAPrime, velBPrime);
    }

    public void tick() {
        tickCount++;

        for (Ball aBall: this.balls) {
            // calculate and set new position by adding position and velocity
            aBall.tick(this.frictionCoeff);

            //check collision with walls
            if (aBall.getPosition().getX() + aBall.getRadius() > this.getX()) {
                aBall.setPosition(aBall.getPosition().getX() - aBall.getRadius(),
                        aBall.getPosition().getY());
                aBall.setVelocity(-1 * aBall.getVelocity().getX(),
                        aBall.getVelocity().getY());
            }

            if (aBall.getPosition().getX() - aBall.getRadius() < 0) {
                aBall.setPosition(0 + aBall.getRadius(),
                        aBall.getPosition().getY());
                aBall.setVelocity(-1 * aBall.getVelocity().getX(),
                        aBall.getVelocity().getY());
            }

            if (aBall.getPosition().getY() + aBall.getRadius() > this.getY()) {
                aBall.setPosition(aBall.getPosition().getX(),
                        this.getY() - aBall.getRadius());
                aBall.setVelocity(aBall.getVelocity().getX(),
                        -1 * aBall.getVelocity().getY());
            }

            if (aBall.getPosition().getY() - aBall.getRadius() < 0) {
                aBall.setPosition(aBall.getPosition().getX(),
                        0 + aBall.getRadius());
                aBall.setVelocity(aBall.getVelocity().getX(),
                        -1 * aBall.getVelocity().getY());
            }

            for (Ball anotherBall: this.balls) {
                if (checkCollision(aBall, anotherBall)) {
                    Pair<Point2D, Point2D> newV = calculateCollision(aBall, anotherBall);

                    aBall.setVelocity(newV.getKey());
                    anotherBall.setVelocity(newV.getValue());
                }
            }
        }
    }
}