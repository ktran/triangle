package util.io;

import geometry.point.ColoredPoint;
import geometry.point.ColoredPointImpl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Reader for parsing colored points.
 *
 * @author Kim-Anh Tran
 */
public class Reader {

    /**
     * Index where parses encounters the integer specifying
     * the number of points.
     */
    private static int PARSE_NUMBER_OF_POINTS_INDEX = 0;

    /**
     * Error message indicating a wrong type for specifying the
     * number of points to parse.
     */
    private static String MISSING_WRONG_POINT_NUMBERS =
            "Unexpected or no token. Specify number of points as int.";

    /**
     * Error message indicating that more points were expected.
     */
    private static String TOO_FEW =
            "Too few points are specified.";

    /**
     * Error message indicating the encounter of an unexpected type while
     * parsing point and its color.
     */
    private static String UNEXPECTED_TYPE =
            "Unexpected type encountered. Specify point (x,y) and color " +
            "as double double int.";


    /**
     * Empty, private constructor. Prevents from initiating an object from Reader.
     */
    private Reader() {
    }

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
     * @param  inStream The input stream to read from.
     * @return Collection of colored points.
     * @throws IOException Thrown, if input can not be parsed.
     * @see ColoredPoint
     */
    public static List<ColoredPoint> readPoints(InputStream inStream) throws ParseException {
        Scanner scanner;
        scanner = new Scanner(inStream);
        scanner.useLocale(Locale.US);

        // Reading and parsing number of points
        int nCoordinates = 0;
        if (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                nCoordinates = scanner.nextInt();
            } else {
                throw new ParseException(MISSING_WRONG_POINT_NUMBERS, PARSE_NUMBER_OF_POINTS_INDEX);
            }
        } else {
            throw new ParseException(MISSING_WRONG_POINT_NUMBERS, PARSE_NUMBER_OF_POINTS_INDEX);
        }

        if (nCoordinates > 0) {
            // Reading end of line
            if (scanner.hasNext())
                scanner.nextLine();
            else
               throw new ParseException(TOO_FEW, PARSE_NUMBER_OF_POINTS_INDEX);
        }

        // Reading and parsing lines of points
        List<ColoredPoint> pointList = new ArrayList<ColoredPoint>(nCoordinates);
        ColoredPoint point;
        for (int i = 0; i < nCoordinates; ++i) {

            if (scanner.hasNext()) {
                point = read(scanner.nextLine(), i);
                pointList.add(point);
            } else {
                throw new ParseException(TOO_FEW, i);
            }
        }

        scanner.close();

        return pointList;
    }

    /**
     * Parses a String and returns an instance of ColoredPoint.
     *
     * @param value        String representation of ColoredPoint.
     * @param errorOffset  The parsing error offset that is used.
     * @return             A ColoredPoint instance, if successful.
     * @throws ParseException If parsing failed.
     */
    private static ColoredPoint read(String value, int errorOffset)
            throws ParseException {
        try {
            return ColoredPointImpl.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ParseException(UNEXPECTED_TYPE, errorOffset);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), errorOffset);
        }
    }
}
