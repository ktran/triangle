package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * A colored point.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointImpl implements ColoredPoint {

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

    @Override
    public int compareTo(Object point) {
        ColoredPoint coloredPoint = (ColoredPoint) point;
        Coordinate pointCoord = coloredPoint.getCoordinate();
        return this.coordinate.compareTo(pointCoord);
    }
}

