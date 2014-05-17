package com.github.excelmapper.core.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleIntConverter implements Converter<Double, Integer> {
    @Override
    public Integer convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return sourceValue.intValue();
    }

    @Override
    public Double convertFrom(Integer destinationValue) {
        if (destinationValue == null) return null;
        return (double)destinationValue;
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<Integer> getDestinationType() {
        return Integer.class;
    }
}
