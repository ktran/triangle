package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 * Tests the ColoredPointImpl methods.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointImplTest {

    // Epsilon used for comparing double values.
    private static final double EPSILON = 0.001;

    // Value that indicates equality of two points.
    private static final int EQUAL = 0;

    @Test
    public void testValueOf() throws Exception {
        testTooFewArgs();
        testTooManyArgs();
        testWrongX();
        testWrongY();
        testWrongZ();
        testWrongColor();
        testValid();
    }

    @Test
    public void testCreate2D() throws Exception {

        double x = 0.0;
        double y = 1.0;
        int dummyColor = 0;

        // The constructed ColoredPoint should be as expected
        ColoredPoint point = ColoredPointImpl.create2D(x, y, dummyColor);
        Coordinate pointCoordinates = point.getCoordinate();
        assertTrue(pointCoordinates.x - x < EPSILON);
        assertTrue(pointCoordinates.y - y < EPSILON);
        assertEquals(point.getColor(), Color.fromInt(dummyColor));

        // A ColoredPoint instance created manually should be equal
        Coordinate coordinate = new Coordinate(x, y);
        ColoredPoint equalPoint = new ColoredPointImpl(coordinate, Color.fromInt(dummyColor));
        assertEquals(point.compareTo(equalPoint), EQUAL);

    }

    // Checks if toValue() detects that too few tokens are provided.
    private void testTooFewArgs() {
        String representation = "3 2";
        testExceptionInValueOf(representation);
    }

    // Checks if toValue() detects the existence of too many tokens are provided.
    private void testTooManyArgs() {
        String representation = "3 0.3 3 1 1" ;
        testExceptionInValueOf(representation);
    }

    // Checks if toValue() detects the existence of too many tokens are provided.
    private void testWrongX() {
        String representation = "af 0.3 3";
        testExceptionInValueOf(representation);
    }

    // Checks if toValue() detects that the y coordinate is no double.
    private void testWrongY() {
        String representation = "0 f 3";
        testExceptionInValueOf(representation);
    }

    // Checks if toValue() detects that the z coordinate is no double.
    private void testWrongZ() {
        String representation = "0 0.3 a 3";
        testExceptionInValueOf(representation);
    }

    // Checks if toValue() detects that the color is no integer.
    private void testWrongColor() {
        String representation = "0 0.3 a";
        testExceptionInValueOf(representation);
    }

    // Checks if toValue() throws an exception for a specified, illegal representation.
    private void testExceptionInValueOf(String representation) {
        try {
            ColoredPointImpl.valueOf(representation);
            Assert.fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    // Checks if toValue() passes with no exceptions while parsing a legal representation.
    private void testValid() {
        String representation = "0 0.3 3";
        try {
            ColoredPointImpl.valueOf(representation);
        } catch (Exception e) {
            Assert.fail("Should not have thrown an exception. Valid String.");
        }
    }

}
