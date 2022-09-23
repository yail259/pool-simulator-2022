package pool.ball;

import org.json.simple.JSONObject;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import pool.PoolFactory;
import pool.PoolObject;

import pool.hole.BlueBallHoleStrategy;
import pool.hole.RedBallHoleStrategy;
import pool.hole.WhiteBallHoleStrategy;

import java.util.HashMap;

public class BallFactory implements PoolFactory {
    private HashMap<String, Color> paintMap;

    public BallFactory() {
        this.paintMap = new HashMap<>();

        paintMap.put("red", Color.RED);
        paintMap.put("blue", Color.BLUE);
        paintMap.put("white", Color.WHITE);
    }

    /**
     * Acts as a director and uses a BallBuilder object to create different types
     * of balls and return them.
     *
     * Implements PoolFactory interface as it creates PoolObjects.
     * @param jsonBall
     * @return Ball as PoolObject
     */
    @Override
    public PoolObject createPoolObj(JSONObject jsonBall) {
        BallBuilder aBuilder = new BallBuilder();

        // the ball colour is a String
        String colour = (String) jsonBall.get("colour");
        aBuilder.setColour(colour);

        aBuilder.setPaintColour(paintMap.get(colour));

        if (colour.equals("red")) {
            aBuilder.setHoleStrat(new RedBallHoleStrategy());
        } else if (colour.equals("blue")) {
            aBuilder.setHoleStrat(new BlueBallHoleStrategy());
        } else if (colour.equals("white")) {
            aBuilder.setHoleStrat(new WhiteBallHoleStrategy());
        }

        // the ball position, velocity, mass are all doubles
        Double positionX = (Double) ((JSONObject) jsonBall.get("position")).get("x");
        Double positionY = (Double) ((JSONObject) jsonBall.get("position")).get("y");
        aBuilder.setPosition(new Point2D(positionX, positionY));

        Double velocityX = (Double) ((JSONObject) jsonBall.get("velocity")).get("x");
        Double velocityY = (Double) ((JSONObject) jsonBall.get("velocity")).get("y");
        aBuilder.setVelocity(new Point2D(velocityX, velocityY));

        Double mass = (Double) jsonBall.get("mass");
        aBuilder.setMass(mass);

        return aBuilder.getResult();
    }
}
