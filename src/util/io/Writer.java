package util.io;

import color.Color;
import geometry.point.ColoredPoint;
import geometry.point.Point;
import geometry.polygon.triangle.ColoredPolygon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Writer for writing points to std:out.
 *
 * @author Kim-Anh Tran
 */
public class Writer {

    /**
     * Writes specified list of points to std:out.
     *
     * @param triangles A list of triangles to write to std:out.
     */
    public static void writeTriangles(List<ColoredPolygon> triangles) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            writer.write(triangles.size() + "\n");
            for (ColoredPolygon triangle : triangles) {
               writer.write(triangle.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Failed writing triangle. Stopping.");
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed while closing outstream.");
        }

    }
}
