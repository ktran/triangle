package util.parallel;

import com.vividsolutions.jts.util.Assert;
import geometry.point.ColoredPoint;
import geometry.point.ColoredPointImpl;
import geometry.polygon.ColoredPolygon;
import geometry.polygon.Polygon;
import geometry.polygon.triangle.ColoredTriangle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Kim-Anh Tran
 */
public class EnclosedPointsFinderTest {

    // The EnclosedPointsFinder instance under test.
    private EnclosedPointsFinder finder;

    // The points that are enclosed.
    private ColoredPoint p1;
    private ColoredPoint p2;

    // The point that is not enclosed.
    private ColoredPoint p3;

    @Before
    public void setUp() throws Exception {
        this.p1 = ColoredPointImpl.create2D(0.0, 1.0, 0);
        this.p2 = ColoredPointImpl.create2D(3.0, 3.0, 0);
        this.p3 = ColoredPointImpl.create2D(5.0, 3.0, 0);

        ColoredPoint p4 = ColoredPointImpl.create2D(0.0, 5.0, 0);
        ColoredPoint p5 = ColoredPointImpl.create2D(4.0, 3.0, 0);
        ColoredPolygon triangle = ColoredTriangle.fromPoints(this.p1, p4, p5);

        List<ColoredPoint> cPoints = new LinkedList<ColoredPoint>();
        cPoints.add(p1);
        cPoints.add(p2);
        cPoints.add(p3);

        this.finder = new EnclosedPointsFinder(cPoints, triangle);
    }

    @Test
    public void testCompute() throws Exception {
        List<ColoredPoint> expectedEnclosed = new LinkedList<ColoredPoint>();
        expectedEnclosed.add(p1);
        expectedEnclosed.add(p2);

        List<ColoredPoint> actualEnclosed = this.finder.compute();

        Assert.equals(expectedEnclosed.size(), actualEnclosed.size());

        int nPoints = expectedEnclosed.size();
        for (int i = 0; i < nPoints; ++i) {
            Assert.equals(expectedEnclosed.get(i), actualEnclosed.get(i));
        }
    }

}
