package geometry.point;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * Represents a point that is characterized by its coordinate.
 *
 * @author Kim-Anh Tran
 */
public interface Point extends Comparable<Point> {

    /**
     * Returns the coordinate of the point.
     *
     * @return The coordinate of the point.
     */
    Coordinate getCoordinate();
}
