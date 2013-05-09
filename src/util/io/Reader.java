package util.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import geometry.point.ColoredPoint;
import geometry.point.ColoredPointFactory;

/**
 * Reader for parsing colored points.
 *
 * @author Kim-Anh Tran
 */
public class Reader {

    private static final int X_INDEX = 0;
    private static final int Y_INDEX = 1;
    private static final int COLOR_INDEX = 2;

    /**
     * Reads std:in and returns a collection of colored points.
     *
     * Note: Input has the following format, with n being the number
     * of points, (x,y) the coordinates and c the color.
     * n
     * x y c
     * x1 y2 c2
     * ..
     *
     * @return Collection of colored points.
     * @see ColoredPoint
     */
    public static List<ColoredPoint> readPoints() {
        Scanner scanner;
        scanner = new Scanner(System.in);

        String line;

        line = scanner.nextLine();
        int nCoordinates = Integer.parseInt(line);
        List<ColoredPoint> pointList = new ArrayList<ColoredPoint>(nCoordinates);

        double x, y;
        int color;
        String[] args;
        for (int i = 0; i < nCoordinates; ++i) {
            line = scanner.nextLine();
            args = line.split(" ");

            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            color = Integer.parseInt(args[2]);

            ColoredPoint p = ColoredPointFactory.create2dColoredPoint(x, y, color);
            pointList.add(p);
        }
        scanner.close();

        return pointList;
    }
}
