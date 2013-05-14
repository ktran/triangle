package search;

import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.Point;

import java.util.Comparator;

/**
 * @author Kim-Anh Tran
 */
public enum YComparator implements Comparator<Point> {

    YCOMPARATOR;

    @Override
    public int compare(Point p1, Point p2) {
        Coordinate p1Coordinate = p1.getCoordinate();
        Coordinate p2Coordinate = p2.getCoordinate();
        if (p1Coordinate.y < p2Coordinate.y) {
            return -1;
        }

        if (p2Coordinate.y < p1Coordinate.y) {
            return 1;
        }

        return 0;
    }
}
