package geometry.polygon.triangle;

import color.Color;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.ColoredPoint;
import geometry.point.ColoredPointImpl;
import geometry.point.Point;
import geometry.polygon.ColoredPolygon;
import geometry.polygon.Polygon;

/**
 * A colored triangle.
 *
 * @author Kim-Anh Tran
 */
public class ColoredTriangle implements ColoredPolygon {

    /**
     * Number of points that define a triangle.
     */
    public static final int N_POINTS = 3;

    /**
     * The points that define a triangle.
     */
    private ColoredPoint[] points;

    /**
     * The coordinates corresponding to each point.
     */
    private Coordinate[] coordinates;

    /**
     * The triangle's color.
     */
    private Color color;

    /**
     * Returns an instance of a Colored Triangle given three points.
     *
     * @param p1    First point characterizing triangle.
     * @param p2    Second point characterizing triangle.
     * @param p3    Third point characterizing triangle.
     * @return
     * @throws IllegalArgumentException Thrown, if points are collinear or
     *                                  differ in their colors.
     */
    public static ColoredTriangle fromPoints(ColoredPoint p1, ColoredPoint p2, ColoredPoint p3)
                                            throws IllegalArgumentException {
        return new ColoredTriangle(p1, p2, p3);
    }

    /**
     *
     * @param x1            First coordinate's x value.
     * @param y1            First coordinate's y value.
     * @param x2            Second coordinate's x value.
     * @param y2            Second coordinate's y value.
     * @param x3            Third coordinate's x value.
     * @param y3            Third coordinate's x value.
     * @param color Color   The color.
     * @return
     * @throws IllegalArgumentException Thrown, if points are collinear.
     */
    public static ColoredTriangle fromCoordinates(double x1, double y1, double x2, double y2,
                                                  double x3, double y3, int color)
                                                  throws IllegalArgumentException {
        ColoredPoint p1 = ColoredPointImpl.create2D(x1, y1, color);
        ColoredPoint p2 = ColoredPointImpl.create2D(x2, y2, color);
        ColoredPoint p3 = ColoredPointImpl.create2D(x3, y3, color);

        return new ColoredTriangle(p1, p2, p3);

    }

    /**
     * Creates a new colored triangle.
     *
     * @param p1                        First point characterizing the triangle.
     * @param p2                        Second point characterizing the triangle.
     * @param p3                        Third point characterizing the triangle.
     * @throws IllegalArgumentException Thrown, if points are collinear or
     *                                  differ in their colors.
     */
    public ColoredTriangle(ColoredPoint p1, ColoredPoint p2, ColoredPoint p3)
            throws IllegalArgumentException {
        Color color = p1.getColor();
        if (color != p2.getColor() || color != p3.getColor()) {
            throw new IllegalArgumentException("Incompatible point colors.");
        }

        int orientation = CGAlgorithms.computeOrientation(p1.getCoordinate(),
                p2.getCoordinate(), p3.getCoordinate());
        if (orientation == CGAlgorithms.COLLINEAR) {
            throw new IllegalArgumentException("Points are collinear.");
        }

        this.color = color;
        this.points = new ColoredPoint[N_POINTS];
        this.points[0] = p1;
        this.points[1] = p2;
        this.points[2] = p3;

        // For calculating distances.
        this.coordinates = new Coordinate[N_POINTS + 1];
        for (int i = 0; i < N_POINTS; ++i) {
            coordinates[i] = this.points[i].getCoordinate();
        }
        coordinates[N_POINTS] = this.points[0].getCoordinate();
    }

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    @Override
    public boolean enclosesPoint(Point point) {
        Coordinate pointCoordinate = point.getCoordinate();
        return CGAlgorithms.isPointInRing(pointCoordinate, this.coordinates);
    }

    @Override
    public boolean intersectsWithPolygon(Polygon polygon) {
        Point[] points = polygon.getPoints();
        int nPoints = points.length;

        // Check if line segments intersect
        for (int i = 0; i < nPoints; ++i) {
            if (intersectsWithLine(points[i], points[(i+1) % nPoints])) {
                return true;
            }
        }

        // Check if triangle encloses polygon
        if (enclosesPoint(points[0])) {
            return true;
        }

        // Check if polygon encloses triangle
        if (polygon.enclosesPoint(this.points[0])) {
            return true;
        }
        return false;

    }

    @Override
    public boolean intersectsWithLine(Point p1, Point p2) {
        Coordinate c1 = p1.getCoordinate();
        Coordinate c2 = p2.getCoordinate();

        // Check if line segments intersect each other.
        double distance = CGAlgorithms.distanceLineLine(
                this.coordinates[0], this.coordinates[1], c1, c2);
        if (distance == 0.0) {
            return true;
        }

        distance = CGAlgorithms.distanceLineLine(
                this.coordinates[1], this.coordinates[2], c1, c2);
        if (distance == 0.0 ) {
            return true;
        }

        CGAlgorithms.distanceLineLine(
                this.coordinates[0], this.coordinates[2], c1, c2);
        if (distance == 0.0) {
            return true;
        }

        // Check if polygon encloses line segment
        if (enclosesPoint(p1)) {
            return true;
        }

        return false;
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
            output += String.format("%s %s ", coordinate.x, coordinate.y);
        }
        output += this.color.getIntRepresentation();

        return output;
    }
}
