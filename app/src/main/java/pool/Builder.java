package pool;
import javafx.geometry.Point2D;

public interface Builder {
    void setColour(String colour);

    void setPosition(Point2D position);

    void setVelocity(Point2D velocity);

    void setMass(double mass);

    void setLife(int life);
}
