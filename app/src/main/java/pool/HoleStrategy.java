package pool;
import pool.ball.Ball;
import pool.table.Table;

public interface HoleStrategy {
    void enterHole(Ball thisBall, Table gameTable);
}
