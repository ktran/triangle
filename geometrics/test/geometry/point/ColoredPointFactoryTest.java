package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the creation of ColoredPoint instances by
 * the ColoredPointFactory.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointFactoryTest {

    // Epsilon used for comparing double values.
    private static final double EPSILON = 0.001;

    // Value that indicates equality of two points.
    private static final int EQUAL = 0;

    @Test
    public void testCreate2dColoredPoint() throws Exception {
        double x = 0.0;
        double y = 1.0;
        int dummyColor = 0;

        // The constructed ColoredPoint should be as expected
        ColoredPoint point = ColoredPointFactory.create2dColoredPoint(x, y, dummyColor);
        Coordinate pointCoordinates = point.getCoordinate();
        assertTrue(pointCoordinates.x - x < EPSILON);
        assertTrue(pointCoordinates.y - y < EPSILON);
        assertEquals(point.getColor(), Color.fromInt(dummyColor));

        // A ColoredPoint instance created manually should be equal
        Coordinate coordinate = new Coordinate(x, y);
        ColoredPoint equalPoint = new ColoredPointImpl(coordinate, Color.fromInt(dummyColor));
        assertEquals(point.compareTo(equalPoint), EQUAL);
    }
}
