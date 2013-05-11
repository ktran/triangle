package util.io;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.ColoredPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

/**
 * Tests the Reader's reading functionality.
 *
 * @author Kim-Anh Tran
 */
public class ReaderTest {

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


    private void testWrongRepresentation() {
        String input = "1 \n 0.1 3 e";

        InputStream inStream = new ByteArrayInputStream(input.getBytes());

        try {
            Reader.readPoints(inStream);
            Assert.fail("Should have raised a ParseException. Invalid type for color.");
        } catch (ParseException e) {
            // Success
        }
    }

    private void testWrongColor() {
        String input = "1 \n 0.1 3.0 100";

        InputStream inStream = new ByteArrayInputStream(input.getBytes());

        try {
            Reader.readPoints(inStream);
            Assert.fail("Should have raised a ParseException. Invalid color representation.");
        } catch (ParseException e) {
            // Success
        }
    }

    private void testWrongX() {
        // X is not a double
        String input = "1 \n e 0.2 0.3";

        InputStream inStream = new ByteArrayInputStream(input.getBytes());

        try {
            Reader.readPoints(inStream);
            Assert.fail("ParseException should have been thrown. Invalid x value.");
        } catch (ParseException e) {
            // Success
        }
    }

    private void testWrongY() {
        // Y is not a double
        String input = "1 \n 0.1 f 0.3";
        InputStream inStream = new ByteArrayInputStream(input.getBytes());

        try {
            Reader.readPoints(inStream);
            Assert.fail("ParseException should have been thrown. Invalid y value.");
        } catch (ParseException e) {
            // Success
        }
    }

    private void testWrongNumber() {
        // Number of poitns is not an integer
        String input = "1.0 \n 0.1 f 0.3";
        InputStream inStream = new ByteArrayInputStream(input.getBytes());

        try {
            Reader.readPoints(inStream);
            Assert.fail("ParseException should have been thrown. Invalid type for number of points.");
        } catch (ParseException e) {
            // Success
        }
    }

    private void testMissingNumber() {
        // Number of points is missing.
        String input = "0.1 f 0.3";
        InputStream inStream = new ByteArrayInputStream(input.getBytes());

        try {
            Reader.readPoints(inStream);
            Assert.fail("ParseException should have been thrown. Number of points was skipped.");
        } catch (ParseException e) {
            // Success
        }
    }

    private void testValidString() {
        // Valid input.
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