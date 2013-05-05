package geometry.point;

import color.Color;
import geometry.point.Point;

/**
 * Represents a colored point.
 *
 * @author Kim-Anh Tran
 */
public interface ColoredPoint extends Point {

    /**
     * Returns the color associated with this object.
     *
     * @return  The color of the object.
     */
    Color getColor();
}
