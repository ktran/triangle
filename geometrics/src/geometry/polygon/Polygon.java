package geometry.polygon;

import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.Point;

/**
 * A basic Polygon defined by a set of points.
 *
 * Author: Kim-Anh Tran
 */
public interface Polygon {

    /**
     * Returns the points defining the polygon;
     *
     * @return The points defining the polygon
     */
    Point[] getPoints();

    /**
     * Returns true if polygon encloses the specified point.
     *
     * @return True, if specified point is enclosed in polygon
     */
    boolean enclosesPoint(Point point);

    boolean intersectsWithPolygon(Polygon polygon);

    boolean intersectsWithLine(Point p1, Point p2);
}
