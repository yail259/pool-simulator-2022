package pool.hole;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Hole {
    private Point2D position;

    private final Color paintColour = Color.BLACK;
    private final double radius = 10.5;

    public Hole(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public Point2D getPosition() {
        return position;
    }

    public Color getPaintColour() {
        return paintColour;
    }

    public double getRadius() {
        return radius;
    }
}
