package util.comparator;

import geometry.point.ColoredPointImpl;
import geometry.point.Point;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * Tests the EuclidComparator instance.
 *
 * @author Kim-Anh Tran
 */
public class EuclidComparatorTest {

    // A dummy color used for initiating points.
    private static final int DUMMY_COLOR = 0;

    // The instance under test.
    private EuclidComparator comparator;

    // The reference point used for comparison.
    private Point referencePoint;

    @Before
    public void setUp() throws Exception {
        this.referencePoint = ColoredPointImpl.create2D(0.0, 0.0, DUMMY_COLOR);
        comparator = new EuclidComparator(referencePoint);
    }

    @Test
    public void testCompare() throws Exception {

        // Test points for distances.
        Point p1 = ColoredPointImpl.create2D(0.0, 0.0, DUMMY_COLOR);
        Point p2 = ColoredPointImpl.create2D(-3.4, 4.3, DUMMY_COLOR);
        Point p3 = ColoredPointImpl.create2D(3.4, -4.3, DUMMY_COLOR);

        Assert.assertTrue(comparator.compare(p1, p2) == -1);
        Assert.assertTrue(comparator.compare(p2, p1) == 1);

        Assert.assertTrue(comparator.compare(p3, p2) == 0);
    }
}
