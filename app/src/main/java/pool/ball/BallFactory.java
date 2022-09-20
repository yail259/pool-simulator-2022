package pool.ball;

import org.json.simple.JSONObject;
import javafx.geometry.Point2D;
import pool.PoolFactory;
import pool.PoolObject;

public class BallFactory implements PoolFactory {
    @Override
    public PoolObject createPoolObj(JSONObject jsonBall) {
        BallBuilder aBuilder = new BallBuilder();

        // the ball colour is a String
        String colour = (String) jsonBall.get("colour");
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
