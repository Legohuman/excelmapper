package ru.dlevin.excelmapper.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleLongConverter implements Converter<Double, Long> {
    @Override
    public Long convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return sourceValue.longValue();
    }

    @Override
    public Double convertFrom(Long destinationValue) {
        if (destinationValue == null) return null;
        return (double)destinationValue;
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<Long> getDestinationType() {
        return Long.class;
    }
}
