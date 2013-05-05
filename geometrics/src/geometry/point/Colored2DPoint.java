package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * A colored 2D point.
 *
 * @author Kim-Anh Tran
 */
public class Colored2DPoint extends ColoredPointImpl {

    /**
     * Creates a new 2d point.
     *
     * @param coordinate The coordinate of the new point.
     * @param color      The color of the new point.
     */
    public Colored2DPoint(Coordinate coordinate, Color color) {
        super(coordinate, color);
    }

    /**
     * Creates a new 2d point from basic types.
     *
     * @param x      The x coordinate of the 2d point.
     * @param y      The y coordinate of the 2d point.
     * @param color  The color of the 2d point.
     */
    public Colored2DPoint(double x, double y, int color) {
        super(new Coordinate(x, y), Color.fromMask(color));
    }

    @Override
    public String toString() {
        return this.coordinate.x + " " + this.coordinate.y + " "  + this.color;
    }
}
