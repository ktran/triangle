package geometry.polygon.triangle;

import geometry.point.ColoredPoint;
import geometry.point.ColoredPointImpl;
import junit.framework.Assert;
import org.junit.Test;

/*
 * Tests an instance of ColoredTriangle.
 *
 * @author Kim-Anh Tran
 */
public class ColoredTriangleTest {
    
    private static final int DUMMY_COLOR = 0;

    // The triangle under test
    private ColoredTriangle triangle;

    @org.junit.Before
    public void setUp() {
        int color = 0;
        this.triangle = ColoredTriangle.fromCoordinates(0.0, 0.0, 3.0, 0.0, 3.0, 5.0, color);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInitialization() {
        // Create collinear triangle.
        ColoredTriangle collinearTriangle;
        ColoredTriangle.fromCoordinates(0.0, 0.0, 3.0, 0.0, 5.0, 0.0, DUMMY_COLOR);
    }

    @org.junit.Test
    public void testEnclosesPoint () {
        ColoredPoint point;

        // Point lies outside
        point = ColoredPointImpl.create2D(0.0, 3.0, DUMMY_COLOR);
        Assert.assertFalse(this.triangle.enclosesPoint(point));

        // Point is one of the triangle's points
        point = ColoredPointImpl.create2D(0.0, 0.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.enclosesPoint(point));

        // Point lies on line segment
        point = ColoredPointImpl.create2D(3.0, 3.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.enclosesPoint(point));

        // Point is enclosed
        point = ColoredPointImpl.create2D(2.0, 0.5, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.enclosesPoint(point));
    }

    @org.junit.Test
    public void testIntersectsWithPolygon() {
        ColoredTriangle sndTriangle;

        // Point-disjoint triangle.
        sndTriangle = ColoredTriangle.fromCoordinates(10.0, 21.0, 6.0, 7.0, 8.0, 9.0, DUMMY_COLOR);
        Assert.assertFalse(this.triangle.intersectsWithPolygon(sndTriangle));

        // Triangle touching at point
        sndTriangle = ColoredTriangle.fromCoordinates(3.0, 5.0, 6.0, 7.0, 8.0, 9.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithPolygon(sndTriangle));

        // Triangle touching at line segment
        sndTriangle = ColoredTriangle.fromCoordinates(2.0, 0.0, -6.0, -7.0, -8.0, -9.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithPolygon(sndTriangle));

        // Triangle intersecting line segments
        sndTriangle = ColoredTriangle.fromCoordinates(0.0, 3.0, 7.0, 3.0, 10, 9.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithPolygon(sndTriangle));

        // Triangle that is enclosed
        sndTriangle = ColoredTriangle.fromCoordinates(0.5, 0.5, 1.5, 1.5, 1.0, 1.2, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithPolygon(sndTriangle));

        // Triangle that is enclosing
        sndTriangle = ColoredTriangle.fromCoordinates(-1.5, -1.0, 5.0, -1.0, 3.0, 6.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithPolygon(sndTriangle));
    }

    @org.junit.Test
    public void testIntersectsWithLine() throws Exception {
        // Line segment does not intersect
        ColoredPoint p1;
        ColoredPoint p2;
        p1 = ColoredPointImpl.create2D(10.0, 11.0, DUMMY_COLOR);
        p2 = ColoredPointImpl.create2D(13.0, 11.0, DUMMY_COLOR);
        Assert.assertFalse(this.triangle.intersectsWithLine(p1, p2));

        // Line segment touches at point
        p1 = ColoredPointImpl.create2D(3.0, 5.0, DUMMY_COLOR);
        p2 = ColoredPointImpl.create2D(6.0, 6.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithLine(p1, p2));

        // Line segment touches at line segment
        p1 = ColoredPointImpl.create2D(3.0, 3.0, DUMMY_COLOR);
        p2 = ColoredPointImpl.create2D(4.0, 6.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithLine(p1, p2));

        // Line segment intersects
        p1 = ColoredPointImpl.create2D(1.0, 1.0, DUMMY_COLOR);
        p2 = ColoredPointImpl.create2D(10.0, 6.0, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithLine(p1, p2));

        // Line segment is enclosed
        p1 = ColoredPointImpl.create2D(0.5, 0.5, DUMMY_COLOR);
        p2 = ColoredPointImpl.create2D(1.5, 1.5, DUMMY_COLOR);
        Assert.assertTrue(this.triangle.intersectsWithLine(p1, p2));
    }

}
