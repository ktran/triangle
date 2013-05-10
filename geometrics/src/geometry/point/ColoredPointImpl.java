package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * A ColoredPoint implementation.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointImpl implements ColoredPoint {

    /**
     * The coordinate of the point.
     */
    private Coordinate coordinate;

    /**
     * The color of the point.
     */
    private Color color;

    /**
     * Creates a new colored point.
     *
     * @param coordinate    A coordinate.
     * @param color         A valid color.
     * @see                 color.Color#validColor(int)
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

