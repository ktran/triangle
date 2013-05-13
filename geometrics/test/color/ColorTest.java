package color;

import junit.framework.Assert;
import org.junit.Test;

/*
 * Tests the enumeration Color.
 *
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
            Assert.assertTrue(Color.validColor(intRepresentation));
        }

        // Invalid integer
        Assert.assertFalse(Color.validColor(Integer.MAX_VALUE));
    }

    @Test
    public void testFromInt() {
        testCorrectRepresentation();
        testInvalidRepresentation();
    }

    // Checks if exception is thrown if fromInt() is called with an invalid integer.
    private void testInvalidRepresentation() {
        try {
            Color.fromInt(Integer.MAX_VALUE);
            Assert.fail("Should have thrown an exception. Invalid color.");
        } catch (IllegalArgumentException e) {
            //Success
        }
    }

    // Checks if every valid color's int representation can be used
    // to retrieve the color itself.
    private void testCorrectRepresentation() {
        // Any color should be retrievable by its integer representation
        for (Color color : this.values) {
            int intRepresentation = color.getIntRepresentation();
            Assert.assertEquals(color, Color.fromInt(intRepresentation));
        }
    }
}
