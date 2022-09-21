package pool.ball;

import javafx.scene.paint.Color;
import pool.Builder;
import javafx.geometry.Point2D;
import pool.HoleStrategy;
import pool.hole.RedBallHoleStrategy;
import pool.hole.BlueBallHoleStrategy;
import pool.hole.WhiteBallHoleStrategy;

import java.util.HashMap;

public class BallBuilder implements Builder {
    private String colour;
    private Color paintColour;
    private Point2D position;
    private Point2D velocity;
    private double mass;
    private HoleStrategy thisBallHoleStrat;
    private HashMap<String, Color> paintMap;

    public BallBuilder() {
        this.paintMap = new HashMap<>();

        paintMap.put("red", Color.RED);
        paintMap.put("blue", Color.BLUE);
        paintMap.put("white", Color.WHITE);
    }

    @Override
    public void setColour(String colour) {
        this.colour = colour;
        this.paintColour = paintMap.get(colour);

        if (colour.equals("red")) {
            setHoleStrat(new RedBallHoleStrategy());
        } else if (colour.equals("blue")) {
            setHoleStrat(new BlueBallHoleStrategy());
        } else if (colour.equals("white")) {
            setHoleStrat(new WhiteBallHoleStrategy());
        }
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
