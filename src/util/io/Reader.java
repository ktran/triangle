package util.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import java.text.ParseException;

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
     * Index where parses encounters the integer specifying
     * the number of points.
     */
    private static int PARSE_NUMBER_OF_POINTS_INDEX = 0;

    /**
     * Error message indicating a wrong type for specifying the
     * number of points to parse.
     */
    private static String INTEGER_ERROR =
            "Unexpected or no token. Specify number of points as int.";

    /**
     * Error message indicating the encounter of an unexpected type while
     * parsing point and its color.
     */
    private static String UNEXPECTED_TYPE =
            "Unexpected or no token. Specify point (x,y) and color " +
            "as double double int.";

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
    public static List<ColoredPoint> readPoints() throws ParseException {
        Scanner scanner;
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        int nCoordinates = 0;
        if (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                nCoordinates = scanner.nextInt();
            } else {
                throw new ParseException(INTEGER_ERROR, PARSE_NUMBER_OF_POINTS_INDEX);
            }
        } else {
            throw new ParseException(INTEGER_ERROR, PARSE_NUMBER_OF_POINTS_INDEX);
        }
        List<ColoredPoint> pointList = new ArrayList<ColoredPoint>(nCoordinates);
        double x = 0.0, y = 0.0;
        int color = 0;

        for (int i = 0; i < nCoordinates; ++i) {
            if (scanner.hasNextDouble()) {
                x = scanner.nextDouble();
            } else {
                throw new ParseException(UNEXPECTED_TYPE, i);
            }

            if (scanner.hasNextDouble()) {
                y = scanner.nextDouble();
            } else {
                throw new ParseException(UNEXPECTED_TYPE, i);
            }

            if (scanner.hasNextInt()) {
                color = scanner.nextInt();
                if (!Color.validColor(color)) throw new ParseException(UNEXPECTED_TYPE, iX);
            } else {
                throw new ParseException(UNEXPECTED_TYPE, i);
            }

            ColoredPoint p = ColoredPointFactory.create2dColoredPoint(x, y, color);
            pointList.add(p);
        }

        scanner.close();

        return pointList;
    }
}
