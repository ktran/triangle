package geometry.polygon.triangle;

import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.Point;
import geometry.polygon.Polygon;

/**
 * Author: Kim-Anh Tran
 */
public class Triangle implements Polygon {

    public static final int N_POINTS = 3;

    protected Point[] points;

    protected Coordinate[] coordinates;

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    @Override
    public Boolean enclosesPoint(Point point) {
        Coordinate pointCoord = point.getCoordinate();
        return CGAlgorithms.isPointInRing(pointCoord, this.coordinates);
    }

    public Triangle(Point[] points) {
        this.points = points;

        this.coordinates = new Coordinate[N_POINTS];
        for (int i = 0; i < N_POINTS; ++i) {
            coordinates[i] = this.points[i].getCoordinate();
        }
    }
}
