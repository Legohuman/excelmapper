package ru.dlevin.excelmapper.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleCharConverter implements Converter<Double, Character> {
    @Override
    public Character convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return (char)sourceValue.shortValue();
    }

    @Override
    public Double convertFrom(Character destinationValue) {
        if (destinationValue == null) return null;
        return (double)destinationValue;
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<Character> getDestinationType() {
        return Character.class;
    }
}
