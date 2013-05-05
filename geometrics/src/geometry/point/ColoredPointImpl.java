package geometry.point;

import color.Color;
import color.Colored;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * A colored point.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointImpl extends Coordinate implements Colored, Point {

    /**
     * The coordinate of the point.
     */
    protected Coordinate coordinate;

    /**
     * The color of the point.
     */
    protected Color color;

    /**
     * Creates a new colored point.
     *
     * @param coordinate    The coordinate of the point
     * @param color         The color of the point
     */
    public ColoredPointImpl(Coordinate coordinate, Color color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public Color getColor() {
        return this.color;
    }
}

