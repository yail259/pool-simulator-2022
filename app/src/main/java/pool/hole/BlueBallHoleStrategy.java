package pool.hole;

import pool.HoleStrategy;
import pool.ball.Ball;
import pool.table.Table;

public class BlueBallHoleStrategy implements HoleStrategy {
    private int life;

    public BlueBallHoleStrategy() {
        this.life = 2;
    }

    @Override
    public void enterHole(Ball thisBall, Table gameTable) {
        if (this.life == 2) {
            thisBall.setPosition(thisBall.getInitialPosition());

            this.life--;
            return;
        }

        gameTable.getBalls().remove(thisBall);
    }
}
