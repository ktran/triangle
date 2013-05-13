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

    public static ColoredPointImpl valueOf(String stringRepresentation) {
        double x, y, z;
        int color;

        StringTokenizer tokenizer = new StringTokenizer(stringRepresentation);
        int nTokens = tokenizer.countTokens();

        if (nTokens < MIN_TOKENS || nTokens > MAX_TOKENS)
            throw new IllegalArgumentException("Unexpected number of tokens.");

            tokenizer.nextElement();

        return null;
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
    public int compareTo(Object point) {
        ColoredPoint coloredPoint = (ColoredPoint) point;
        Coordinate pointCoord = coloredPoint.getCoordinate();
        return this.coordinate.compareTo(pointCoord);
    }

}

