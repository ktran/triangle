package color;

import geometry.polygon.triangle.ColoredTriangle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Kim-Anh Tran
 */
public class ColorTest {

    // All color types
    private Color[] values;

    @org.junit.Before
    public void setUp() {
        this.values = Color.values();
    }

    @Test
    public void testValidColor() {
        // Any of its color types should have a valid integer representation
        for (Color color : this.values) {
            int intRepresentation = color.getIntRepresentation();
            assertTrue(Color.validColor(intRepresentation));
        }

        // Invalid integer
        assertFalse(Color.validColor(Integer.MAX_VALUE));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFromInt() {
        // Any color should be retrievable by its integer representation
        for (Color color : this.values) {
            int intRepresentation = color.getIntRepresentation();
            assertEquals(color, Color.fromInt(intRepresentation));
        }

        // Invalid integer. Expected: IllegalArgumentException
        Color.fromInt(Integer.MAX_VALUE);
    }
}
