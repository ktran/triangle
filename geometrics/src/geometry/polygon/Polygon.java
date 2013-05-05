package geometry.polygon;

import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.Point;

/**
 * A basic Polygon.
 *
 * Author: Kim-Anh Tran
 */
public interface Polygon {

    Point[] getPoints();

    Boolean enclosesPoint();

}
