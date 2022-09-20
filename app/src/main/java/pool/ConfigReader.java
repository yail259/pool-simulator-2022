package pool;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pool.ball.Ball;
import pool.ball.BallFactory;
import pool.table.Table;
import pool.table.TableFactory;

public class ConfigReader {

    /**
     *
     * @param path The path of the json file to read
     */
    public Table parse(String path) {

        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;

            TableFactory tFac = new TableFactory();
            Table newTable = (Table) tFac.createPoolObj(jsonObject);

            // reading the "Balls" section:
            JSONObject jsonBalls = (JSONObject) jsonObject.get("Balls");

            // reading the "Balls: ball" array:
            JSONArray jsonBallsBall = (JSONArray) jsonBalls.get("ball");

            BallFactory bFac = new BallFactory();

            // reading from the array:
            for (Object obj : jsonBallsBall) {
                JSONObject jsonBall = (JSONObject) obj;

                PoolObject newBall = bFac.createPoolObj(jsonBall);

                newTable.addBall((Ball) newBall);

                System.out.println(((Ball) newBall).getPosition());
            }

            return newTable;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
