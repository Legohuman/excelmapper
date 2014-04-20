package ru.dlevin.excelmapper.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleFloatConverter implements Converter<Double, Float> {
    @Override
    public Float convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return sourceValue.floatValue();
    }

    @Override
    public Double convertFrom(Float destinationValue) {
        if (destinationValue == null) return null;
        return (double)destinationValue;
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<Float> getDestinationType() {
        return Float.class;
    }
}
