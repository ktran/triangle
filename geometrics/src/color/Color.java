package color;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration representing color types. Each color type corresponds to an integer
 * in range [0..4].
 *
 * @author Kim-Anh Tran
 */
public enum Color {

    /**
     * Yellow color.
     */
    YELLOW(0),

    /**
     * Green color.
     */
    GREEN(1),

    /**
     * Blue color.
     */
    BLUE(2),

    /**
     * Red color.
     */
    RED(3),

    /**
     * Violet color.
     */
    VIOLET(4);

    /**
     * Map from integer value representation to color type.
     */
    private static final Map<Integer, Color> intToColor = new HashMap<Integer, Color>();
    static {
        Color[] colorValues = Color.values();
        for (Color color : colorValues) {
            intToColor.put(color.getIntRepresentation(), color);
        }
    }

    /**
     * Integer that is associated with a color enum type.
     */
    private final int intRepresentation;

    /**
     * Initializes an enum with its intRepresentation.
     *
     * @param intRepresentation  Integer associated with an enumeration type.
     */
    private Color(int intRepresentation) {
        this.intRepresentation = intRepresentation;
    }

    /**
     * Returns true, if the specified integer value correspons to a color type.
     *
     * @param colorInt  The integer value to check.
     * @return          True, if a color is associated with the specified integer.
     */
    public static boolean validColor(int colorInt) {
        return intToColor.containsKey(colorInt);
    }

    /**
     * Returns the enumeration type associated with the colorInt.
     *
     * @param colorInt  Integer associated with an enumeration type.
     * @return      Enumeration type associated with the colorInt.
     */
    public static Color fromInt(int colorInt) throws IllegalArgumentException {
        Color color = intToColor.get(colorInt);
        if (color == null) {
            throw new IllegalArgumentException("Illegal color. Not in range: " + intToColor.keySet());
        }

        return color;
    }

    /**
     * Returns the integer associated with the given enumeration type.
     *
     * @return Associated enumeration type.
     */
    public int getIntRepresentation() {
        return this.intRepresentation;
    }
}
