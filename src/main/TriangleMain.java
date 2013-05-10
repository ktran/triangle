package main;

import com.vividsolutions.jts.algorithm.CGAlgorithms;
import geometry.point.ColoredPoint;
import geometry.polygon.triangle.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;
import util.io.Reader;
import util.io.Writer;
import util.search.TriangleSearch;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Main class for starting up the point-disjoint triangle search.
 *
 * @author Kim-Anh Tran
 */
public class TriangleMain {

    /**
     * Reads in 2d points from std:in, searches for triangles and writes them to std:out.
     *
     * @param args // None
     */
    public static void main(String[] args) {
        List<ColoredPoint> points;

        try {
            points = Reader.readPoints();

            List<ColoredPolygon> triangles;
            triangles = new TriangleSearch(points).searchForTriangles();

            Writer.writeTriangles(triangles);

        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);

        } catch (IOException e) {
            System.err.println("Writing triangles to std:out failed unexpected.");
            System.exit(1);
        }
    }
}