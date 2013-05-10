package util.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

import color.Color;
import geometry.point.ColoredPoint;
import geometry.point.ColoredPointFactory;

/**
 * Reader for parsing colored points.
 *
 * @author Kim-Anh Tran
 */
public class Reader {
    /**
     * The argument index of the x coordinate.
     */
    private static final int X_INDEX = 0;

    /**
     * The argument index of the y coordinate.
     */
    private static final int Y_INDEX = 1;

    /**
     * The argument index of the color.
     */
    private static final int COLOR_INDEX = 2;

    /**
     * Error message indicating a wrong type for specifying the
     * number of points to parse.
     */
    private static String INTEGER_ERROR =
            "Number of points has to be specified as an integer value.";

    /**
     * Error message indicating the encounter of an unexpected type while
     * parsing point and its color.
     */
    private static String UNEXPECTED_TYPE =
            "Unexpected coordinate or color. Specify points " +
            "as x y c with x,y being doubles, c being a valid integer.";

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
     * @throws IOException Thrown, if input can not be parsed.
     * @see ColoredPoint
     */
    public static List<ColoredPoint> readPoints() throws IOException, IllegalArgumentException {
        Scanner scanner;
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        int nCoordinates = 0;
        if (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                nCoordinates = scanner.nextInt();
            } else {
                throw new IOException(INTEGER_ERROR);
            }
        } else {
            throw new IOException(INTEGER_ERROR);
        }
        List<ColoredPoint> pointList = new ArrayList<ColoredPoint>(nCoordinates);
        double x = 0.0, y = 0.0;
        int color = 0;

        for (int i = 0; i < nCoordinates; ++i) {
            if (scanner.hasNextDouble()) {
                x = scanner.nextDouble();
            } else {
                throw new IOException(UNEXPECTED_TYPE);
            }

            if (scanner.hasNextDouble()) {
                y = scanner.nextDouble();
            } else {
                throw new IOException(UNEXPECTED_TYPE);
            }

            if (scanner.hasNextInt()) {
                color = scanner.nextInt();
                if (!Color.validColor(color)) throw new IOException(UNEXPECTED_TYPE);
            } else {
                throw new IOException(UNEXPECTED_TYPE);
            }

            ColoredPoint p = ColoredPointFactory.create2dColoredPoint(x, y, color);
            pointList.add(p);
        }

        scanner.close();

        return pointList;
    }
}
