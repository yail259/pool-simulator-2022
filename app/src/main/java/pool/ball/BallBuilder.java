package pool.ball;

import javafx.scene.paint.Color;
import pool.Builder;
import javafx.geometry.Point2D;
import pool.HoleStrategy;

public class BallBuilder implements Builder {
    private String colour;
    private Color paintColour;
    private Point2D position;
    private Point2D velocity;
    private double mass;
    private HoleStrategy thisBallHoleStrat;

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public void setPaintColour(Color paintColour) {
        this.paintColour = paintColour;
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
    public void setHoleStrat(HoleStrategy thisBallHoleStrat) {
        this.thisBallHoleStrat = thisBallHoleStrat;
    }

    public Ball getResult() {
        return new Ball(colour, paintColour, position, velocity, mass, thisBallHoleStrat);
    }
}
