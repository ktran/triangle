package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * Creates ColoredPoint instances.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointFactory {

    public static ColoredPoint create2dColoredPoint(double x, double y, int color) {
        Coordinate coordinate = new Coordinate(x,y);
        Color pointColor = Color.fromMask(color);
        return new ColoredPointImpl(coordinate, pointColor);
    }
}
