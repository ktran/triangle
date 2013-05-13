package util.io;

import geometry.polygon.ColoredPolygon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Writer for writing points to a specified stream.
 *
 * @author Kim-Anh Tran
 */
public class Writer {

    /**
     * Empty, private constructor. Prevents from initiating an object from Writer.
     */
    private Writer() {
    }

    /**
     * Writes specified list of polygons to specified stream.
     *
     *
     * @param outStream     The stream that is written to
     * @param  polygons     A list of polygons to write to outStream.
     * @throws IOException  Thrown, if writing to std:out failed.
     */
    public static void writeTriangles(OutputStream outStream, List<ColoredPolygon> polygons) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

        writer.write(polygons.size() + "\n");
        for (ColoredPolygon polygon : polygons) {
            writer.write(polygon.toString() + "\n");
        }

        writer.close();
    }
}
