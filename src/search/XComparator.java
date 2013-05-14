package search;

import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.Point;

import java.util.Comparator;

/**
 * @author Kim-Anh Tran
 */
public enum XComparator implements Comparator<Point> {

    XCOMPARATOR;

    @Override
    public int compare(Point p1, Point p2) {
        Coordinate p1Coordinate = p1.getCoordinate();
        Coordinate p2Coordinate = p2.getCoordinate();
        if (p1Coordinate.x < p2Coordinate.x) {
            return -1;
        }

        if (p2Coordinate.x < p1Coordinate.x) {
            return 1;
        }

        return 0;
    }
}
