package util.comparator;

import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.Point;

import java.util.Comparator;

/**
 * An instance of EuclidComparator provides a compare function, comparing
 * the distances to a pre-defined node.
 *
 * @author Kim-Anh Tran
 */
public class EuclidComparator implements Comparator<Point> {

    /**
     * Point, to which the distance is computed.
     */
    private Point point;

    /**
     * Creates a new comparator. The compare functionality
     * will compare two points based on the distance to this specified
     * point.
     *
     * @param point The reference point.
     */
    public EuclidComparator(Point point) {
        this.point = point;
    }

    @Override
    public int compare(Point p1, Point p2) {
        Coordinate p1Coordinate = p1.getCoordinate();
        Coordinate p2Coordinate = p2.getCoordinate();

        // Squared distances are sufficient for comparisons.
        double distP1 = squaredDistancePointPoint(this.point, p1);
        double distP2 = squaredDistancePointPoint(this.point, p2);

        if (distP1 < distP2) {
            return -1;
        }

        if (distP2 < distP1) {
            return 1;
        }

        return 0;
    }

    /**
     * Returns the squared distance between two points.
     *
     * @param p1    First point
     * @param p2    Second point
     * @return      Squared distance between p1 and p2
     */
    private double squaredDistancePointPoint(Point p1, Point p2) {
        Coordinate p1Coordinate = p1.getCoordinate();
        Coordinate p2Coordinate = p2.getCoordinate();

        double powX = Math.pow(p1Coordinate.x - p2Coordinate.x, 2);
        double powY = Math.pow(p1Coordinate.y - p2Coordinate.y, 2);

        return (powX + powY);
    }

}