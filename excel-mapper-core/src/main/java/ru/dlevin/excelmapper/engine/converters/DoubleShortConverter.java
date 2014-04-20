package ru.dlevin.excelmapper.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleShortConverter implements Converter<Double, Short> {
    @Override
    public Short convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return sourceValue.shortValue();
    }

    @Override
    public Double convertFrom(Short destinationValue) {
        if (destinationValue == null) return null;
        return (double)destinationValue;
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<Short> getDestinationType() {
        return Short.class;
    }
}
