package pool;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import pool.hole.Hole;

public interface Builder {
    void setColour(String colour);
    void setPaintColour(Color paintColour);
    void setPosition(Point2D position);

    void setVelocity(Point2D velocity);

    void setMass(double mass);
    void setHoleStrat(HoleStrategy thisBallHoleStrat);
}
