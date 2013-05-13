package util.io;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.ColoredPoint;
import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

/**
 * Tests the Reader's reading functionality.
 *
 * @author Kim-Anh Tran
 */
public class ReaderTest {

    // Used for double comparisons.
    private static final double EPSILON = 0.0001;


    @Test
    public void testReadPoints() {
        testWrongColor();
        testWrongRepresentation();
        testWrongX();
        testWrongY();
        testWrongNumber();
        testMissingNumber();
        testValidString();
    }

    // Checks if the Reader instance detects a wrong passed type for color.
    private void testWrongRepresentation() {
        String input = "1 \n 0.1 3 e";
        testExpectedException(input);
    }

    // Checks if the Reader instance detects an invalid color.
    private void testWrongColor() {
        String input = "1 \n 0.1 3.0 100";
        testExpectedException(input);
    }

    // Checks if the Reader instance detects a wrong passed type for the x value.
    private void testWrongX() {
        String input = "1 \n e 0.2 0.3";
        testExpectedException(input);
    }

    // Checks if the Reader instance detects a wrong passed type for the y value.
    private void testWrongY() {
        String input = "1 \n 0.1 f 0.3";
        testExpectedException(input);
    }

    // Checks if the Reader instance detects a wrong passed type for the number of points.
    private void testWrongNumber() {
        String input = "a \n 0.1 0.3 3";
        testExpectedException(input);
    }

    // Checks if the Reader instance detects the missing number of points to read.
    private void testMissingNumber() {
        String input = "0.1 f 0.3";
        testExpectedException(input);
    }

    // Checks if the specified input raises an expected Exception
    private void testExpectedException(String input) {
        InputStream inStream = new ByteArrayInputStream(input.getBytes());
        try {
            Reader.readPoints(inStream);
            Assert.fail("ParseException should have been thrown.");
        } catch (ParseException e) {
            // Success
        }

        try {
            inStream.close();
        } catch (IOException e) {
            Assert.fail("Should not have raised an exception while closing stream.");
        }
    }

    // Checks if a valid input is parsed correctly.
    private void testValidString() {
        String input = "1 \n 0.1 0.3 3";
        InputStream inStream = new ByteArrayInputStream(input.getBytes());

        List<ColoredPoint> points = null;
        try {
            points = Reader.readPoints(inStream);
        } catch (ParseException e) {
            Assert.fail("ParseException should not have been thrown. Valid input.");
        }

        Assert.assertNotNull(points);
        Assert.assertEquals(points.size(), 1);

        ColoredPoint point = points.get(0);
        Coordinate coordinate = point.getCoordinate();
        Color color = point.getColor();

        Assert.assertEquals(color.getIntRepresentation(), 3);
        Assert.assertTrue(coordinate.x - 0.1 < EPSILON);
        Assert.assertTrue(coordinate.y - 0.3 < EPSILON);
    }
}
