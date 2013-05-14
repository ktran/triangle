package geometry.point;

import color.Color;
import com.vividsolutions.jts.geom.Coordinate;

import java.util.StringTokenizer;

/**
 * A ColoredPoint implementation.
 *
 * @author Kim-Anh Tran
 */
public class ColoredPointImpl implements ColoredPoint {

    /**
     * Minimum number of tokens needed for creating a ColoredPoint instance from
     * a String representation.
     *
     * @see ColoredPointImpl#valueOf(String)
     */
    private static int MIN_TOKENS = 3;

    /**
     * Maximum number of tokens for creating a ColoredPoint instance
     * from a String representation.
     *
     * @see ColoredPointImpl#valueOf(String)
     */
    private static int MAX_TOKENS = 4;

    /**
     * The coordinate of the point.
     */
    private Coordinate coordinate;

    /**
     * The color of the point.
     */
    private Color color;

    /**
     * Creates a new colored point.
     *
     * @param coordinate    A coordinate.
     * @param color         A valid color.
     * @see                 color.Color#validColor(int)
     */
    public ColoredPointImpl(Coordinate coordinate, Color color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    /**
     * Returns a 2d ColoredPoint.
     *
     * @param x     The x coordinate.
     * @param y     The y coordinate.
     * @param color A valid color.
     * @return      A 2d colored point.
     * @see         color.Color#validColor(int)
     */
    public static ColoredPointImpl create2D(double x, double y, int color) {
        Coordinate coordinate = new Coordinate(x,y);
        Color pointColor = Color.fromInt(color);
        return new ColoredPointImpl(coordinate, pointColor);
    }

    /**
     * Translates a String representation of a ColoredPointImpl to a ColoredPoint.
     * String is formatted as follows:
     * %f %f %f %d for a 3d point,
     * %f %f %d    for a 2d point.
     *
     * @param stringRepresentation      The String value to parse.
     * @return                          The corresponding ColoredPoint instance.
     * @throws NumberFormatException    If String types did not match with
     *                                  the expected types.
     * @throws IllegalArgumentException If String contains wrong number of tokens.
     */
    public static ColoredPointImpl valueOf(String stringRepresentation)
            throws NumberFormatException, IllegalArgumentException {

        StringTokenizer tokenizer = new StringTokenizer(stringRepresentation);
        int nTokens = tokenizer.countTokens();

        if (nTokens < MIN_TOKENS || nTokens > MAX_TOKENS) {
            throw new IllegalArgumentException("Unexpected number of tokens.");
        }

        double x = Double.parseDouble(tokenizer.nextToken());
        double y = Double.parseDouble(tokenizer.nextToken());
        double z = Double.NaN;
        if (nTokens == MAX_TOKENS) {
            z = Double.parseDouble(tokenizer.nextToken());
        }
        int colorRepresentation = Integer.parseInt(tokenizer.nextToken());

        Coordinate pointCoordinate = new Coordinate(x, y, z);
        Color color = Color.fromInt(colorRepresentation);

        return new ColoredPointImpl(pointCoordinate, color);
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public int compareTo(Point point) {
        Coordinate pointCoordinate = point.getCoordinate();
        return this.coordinate.compareTo(pointCoordinate);
    }

}

