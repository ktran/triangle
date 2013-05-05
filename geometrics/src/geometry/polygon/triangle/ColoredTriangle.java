package geometry.polygon.triangle;

import color.Color;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.ColoredPoint;
import geometry.point.Point;
import geometry.polygon.Polygon;

/**
 * @author Kim-Anh Tran
 */
public class ColoredTriangle implements ColoredPolygon {

    public static final int N_POINTS = 3;

    protected ColoredPoint[] points;

    protected Color color;

    private Coordinate[] coordinates;

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    /**
     * Creates a new colored triangle.
     *
     * @param p1                        First point characterizing the triangle.
     * @param p2                        Second point characterizing the triangle.
     * @param p3                        Third point characterizing the triangle.
     * @throws IllegalStateException    Thrown, if the colors of points are incompatible.
     */
    public ColoredTriangle(ColoredPoint p1, ColoredPoint p2, ColoredPoint p3) throws IllegalStateException {
        Color color = points[0].getColor();
        for (int i = 1; i < N_POINTS; ++i) {
            if (color != points[i].getColor()) {
                throw new IllegalStateException("Incompatible point colors.");
            }
        }
        this.color = color;
        this.points = points;

        this.coordinates = new Coordinate[N_POINTS];
        for (int i = 0; i < N_POINTS; ++i) {
            coordinates[i] = this.points[i].getCoordinate();
        }
    }

    @Override
    public Boolean enclosesPoint(Point point) {
        Coordinate pointCoord = point.getCoordinate();
        return CGAlgorithms.isPointInRing(pointCoord, this.coordinates);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        String output = "";
        for (Point point : this.points) {
            Coordinate coordinate = point.getCoordinate();
            output += String.format("%d %d ", coordinate.x, coordinate.y);
        }
        output += this.color;

        return output;
    }
}
