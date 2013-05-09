package main;

import com.vividsolutions.jts.algorithm.CGAlgorithms;
import geometry.point.ColoredPoint;
import geometry.polygon.triangle.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;
import util.io.Reader;
import util.io.Writer;
import util.search.TriangleSearch;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Author: Kim-Anh Tran
 *
 * Main class for starting up the triangle detection.
 */
public class TriangleMain {

    /**
     * Reads in 2d points from std:in, finds triangles and writes them to std:out.
     *
     * @param args // None
     */
    public static void main(String[] args) {
        List<ColoredPoint> points;
        points = Reader.readPoints();

        List<ColoredPolygon> triangles;
        triangles = new TriangleSearch(points).searchForTriangles();

        Writer.writeTriangles(triangles);
    }
}