package com.github.excelmapper.core.engine.converters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class ReverseConverterTest {

    @Test
    public void testReverse() {
        Converter<Double, Integer> directConverter = new DoubleIntConverter();
        ReverseConverter<Integer, Double> reverseConverter = new ReverseConverter<Integer, Double>(directConverter);

        assertEquals(5, (int)directConverter.convertTo(5d));
        assertEquals(5, (int)reverseConverter.convertFrom(5d));
    }
}
