package pool.hole;

import pool.HoleStrategy;
import pool.ball.Ball;
import pool.table.Table;


public class RedBallHoleStrategy implements HoleStrategy {
    @Override
    public void enterHole(Ball thisBall, Table gameTable) {
        gameTable.getBalls().remove(thisBall);
    }
}
