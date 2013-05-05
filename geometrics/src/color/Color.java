package color;

/**
 * Enumeration representing color types.
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
     * Contains all color enumerationt types.
     */
    static final Color[] values = Color.values();

    /**
     * Integer that is associated with a color enum type.
     */
    private final int mask;

    /**
     * Initializes an enum with its mask.
     *
     * @param mask  Integer associated with an enumeration type.
     */
    private Color(int mask) {
        this.mask = mask;
    }

    /**
     * Returns the enumeration type associated with the mask.
     *
     * @param mask  Integer associated with an enumeration type.
     * @return      Enumeration type associated with the mask.
     */
    public static Color fromMask(int mask) {
        return values[mask];
    }

    /**
     * Returns the integer associated with the given enumeration type.
     *
     * @return Associated enumeration type.
     */
    public int getMask() {
        return this.mask;
    }
}
