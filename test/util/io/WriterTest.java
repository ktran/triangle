package util.io;

import geometry.polygon.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;
import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Tests the Writer's writing functionality.
 *
 * @author Kim-Anh Tran
 */
public class WriterTest {

    // Used for double comparisons.
    private static final double EPSILON = 0.0001;

    @Test
    public void testWriteTriangles() {
        double x1 = 0.0;
        double y1 = 1.0;

        double x2 = 3.4;
        double y2 = 3.7;

        double x3 = 7.5;
        double y3 = 5.5;

        int colorRepresentation = 0;

        ColoredPolygon triangle = ColoredTriangle.fromCoordinates(x1, y1, x2, y2, x3, y3, colorRepresentation);
        List<ColoredPolygon> list = new LinkedList<ColoredPolygon>();
        list.add(triangle);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            Writer.writeTriangles(outStream, list);
            outStream.close();
        } catch (IOException e) {
            Assert.fail("Should not have raised an exception while writing or closing stream.");
        }

        String output = new String(outStream.toByteArray());
        StringTokenizer tokenizer = new StringTokenizer(output);

        // Number of points
        Assert.assertEquals(1, Integer.parseInt(tokenizer.nextToken()));

        Assert.assertTrue(x1 - Double.parseDouble(tokenizer.nextToken()) < EPSILON);
        Assert.assertTrue(y1 - Double.parseDouble(tokenizer.nextToken()) < EPSILON);
        Assert.assertTrue(x2 - Double.parseDouble(tokenizer.nextToken()) < EPSILON);
        Assert.assertTrue(y2 - Double.parseDouble(tokenizer.nextToken()) < EPSILON);
        Assert.assertTrue(x3 - Double.parseDouble(tokenizer.nextToken()) < EPSILON);
        Assert.assertTrue(y3 - Double.parseDouble(tokenizer.nextToken()) < EPSILON);
        Assert.assertEquals(colorRepresentation, Integer.parseInt(tokenizer.nextToken()));

    }

}
