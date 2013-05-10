package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * A ColoredPointFactory can be used to create ColoredPoint instances.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointFactory {

    /**
     * Returns a 2d ColoredPoint.
     *
     * @param x     The x coordinate.
     * @param y     The y coordinate.
     * @param color A valid color.
     * @return      A 2d colored point.
     * @see         color.Color#validColor(int)
     */
    public static ColoredPoint create2dColoredPoint(double x, double y, int color) {
        Coordinate coordinate = new Coordinate(x,y);
        Color pointColor = Color.fromInt(color);
        return new ColoredPointImpl(coordinate, pointColor);
    }
}
