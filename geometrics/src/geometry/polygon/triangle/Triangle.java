package geometry.polygon.triangle;

import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.Point;
import geometry.polygon.Polygon;

/**
 * Author: Kim-Anh Tran
 */
public class Triangle implements Polygon {

    protected Point[] points;

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    @Override
    public Boolean enclosesPoint() {
        return null;
    }

    public Triangle(Point[] points) {
        this.points = points;
    }
}
