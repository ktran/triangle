package search;

import color.Color;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import geometry.point.ColoredPoint;
import geometry.point.Point;
import geometry.polygon.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import util.io.Reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/*
 * Tests the search algorithm for finding triangles in a 2d set of points.
 *
 * @author Kim-Anh Tran
 */
public class TriangleSearchTest {

    // Test data file name.
    private String TEST_FILE = "testdata/data0";

    // Test 2d points.
    private List<ColoredPoint> points;

    // TriangleSearch instance to test.
    private TriangleSearch search;

    @Before
    public void setUp() {
        try {
            FileInputStream inputStream = new FileInputStream(TEST_FILE);
            this.points = Reader.readPoints(inputStream);
            this.search = new TriangleSearch(this.points, 0);
        } catch (ParseException e) {
            Assert.fail("Failed reading test data from file.");
        } catch (FileNotFoundException e) {
            Assert.fail("Could not find test data file.");
        }
    }

    @Test
    public void testSearchForTriangles() throws Exception {
        int processors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(processors);
        List<ColoredPolygon> triangles = forkJoinPool.invoke(this.search);

        // Remove all points that are already belonging to a triangle
        for (ColoredPolygon triangle : triangles) {
            Point[] trianglePoints = triangle.getPoints();
            for (Point point : trianglePoints) {
                this.points.remove(point);
            }
        }

        // Separate by color.
        List<List<ColoredPoint>> cPoints =  new LinkedList<List<ColoredPoint>>();
        int nColors = Color.values().length;
        for (int i = 0; i < nColors; ++i)
            cPoints.add(new LinkedList<ColoredPoint>());
        for (ColoredPoint cPoint : this.points) {
            int colorIndex = cPoint.getColor().getIntRepresentation();
            cPoints.get(colorIndex).add(cPoint);
        }

        // Check, if any point-disjoint triangle can be inserted for any color
        for (List<ColoredPoint> coloredPoints : cPoints) {
            testNewTriangles(coloredPoints, triangles);
        }
    }

    // Tests if any new point-disjoint triangle can be inserted.
    private void testNewTriangles(List<ColoredPoint> coloredPoints, List<ColoredPolygon> triangles) {
        Iterator<ColoredPoint> iterator = coloredPoints.iterator();

        // Test every possible combination of points
        while (iterator.hasNext()) {
            if (coloredPoints.size() < 3) {
                break;
            }
            ColoredPoint p1 = iterator.next();
            iterator.remove();

            int nPoints = coloredPoints.size();
            for (int i = 0; i < nPoints; ++i) {
                ColoredPoint p2 = coloredPoints.get(i);
                for (int j = i + 1; j < nPoints; ++j) {
                    ColoredPoint p3 = coloredPoints.get(j);
                    if (CGAlgorithms.computeOrientation(p1.getCoordinate(), p2.getCoordinate(), p3.getCoordinate())
                            != CGAlgorithms.COLLINEAR){
                        ColoredPolygon newTriangle = ColoredTriangle.fromPoints(p1, p2, p3);

                        /* A triangle is invalid, if it intersects at least one already
                         * existing triangle.
                         */
                        boolean intersect = false;
                        for (ColoredPolygon triangle : triangles) {
                            if (triangle.intersectsWithPolygon(newTriangle)) {
                                intersect = true;
                                break;
                            }
                        }
                        Assert.assertTrue(intersect);
                    }
                }
            }
        }

    }
}
