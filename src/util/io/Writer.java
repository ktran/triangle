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
     * Writes specified list of polygons to std:out.
     *
     * @param  polygons     A list of polygons to write to std:out.
     * @throws IOException  Thrown, if writing to std:out failed.
     */
    public static void writeTriangles(List<ColoredPolygon> polygons) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        writer.write(polygons.size() + "\n");
        for (ColoredPolygon polygon : polygons) {
            writer.write(polygon.toString() + "\n");
        }

        writer.close();
    }
}
