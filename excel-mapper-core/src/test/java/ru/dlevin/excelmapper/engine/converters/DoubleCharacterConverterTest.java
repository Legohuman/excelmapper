package ru.dlevin.excelmapper.engine.converters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleCharacterConverterTest {

    @Test
    public void testFrom() {
        assertEquals(97d, new DoubleCharConverter().convertFrom('a'), 1e-6);
    }

    @Test
    public void testTo() {
        assertEquals(new Character('a'), new DoubleCharConverter().convertTo(97d));
    }

    @Test
    public void testFromNull() {
        assertNull(new DoubleCharConverter().convertFrom(null));
    }

    @Test
    public void testToNull() {
        assertNull(new DoubleCharConverter().convertTo(null));
    }
}
