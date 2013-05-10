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

    /**
     * Returns true if this polygon intersects with the specified one
     * Two polygons intersect each other, if their bounds touch each
     * other or one is enclosed by the other.
     *
     * @param polygon   The polygon to check for intersection.
     * @return          True, if polygons intersect.
     */
    boolean intersectsWithPolygon(Polygon polygon);

    /**
     * Returns true, if this polygon intersects with a line segment.
     * A polygon intersects with a line segment, if the line touches
     * the polygon or is enclosed by the polygon.
     *
     * @param p1    The first point of the line segment.
     * @param p2    The second point of the line segment.
     * @return      True, if polygon intersects with line segment.
     */
    boolean intersectsWithLine(Point p1, Point p2);
}
