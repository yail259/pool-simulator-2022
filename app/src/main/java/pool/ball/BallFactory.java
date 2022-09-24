package pool.ball;

import org.json.simple.JSONObject;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import pool.PoolFactory;
import pool.PoolObject;
import pool.Builder;

import pool.hole.BlueBallHoleStrategy;
import pool.hole.RedBallHoleStrategy;
import pool.hole.WhiteBallHoleStrategy;

import java.util.HashMap;

public class BallFactory implements PoolFactory {
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
        Builder aBuilder = null;
        // the ball colour is a String
        String colour = (String) jsonBall.get("colour");

        if (colour.equals("red")) {
            aBuilder = new RedBallBuilder();
        } else if (colour.equals("blue")) {
            aBuilder = new BlueBallBuilder();
        } else if (colour.equals("white")) {
            aBuilder = new WhiteBallBuilder();
        }

        aBuilder.setColour(colour);

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
