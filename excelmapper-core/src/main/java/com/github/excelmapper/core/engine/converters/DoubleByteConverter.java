package com.github.excelmapper.core.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleByteConverter implements Converter<Double, Byte> {
    @Override
    public Byte convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return sourceValue.byteValue();
    }

    @Override
    public Double convertFrom(Byte destinationValue) {
        if (destinationValue == null) return null;
        return (double)destinationValue;
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<Byte> getDestinationType() {
        return Byte.class;
    }
}
