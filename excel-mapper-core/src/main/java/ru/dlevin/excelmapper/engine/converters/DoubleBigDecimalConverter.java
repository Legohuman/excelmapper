package ru.dlevin.excelmapper.engine.converters;

import java.math.BigDecimal;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleBigDecimalConverter implements Converter<Double, BigDecimal> {
    @Override
    public BigDecimal convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return new BigDecimal(sourceValue);
    }

    @Override
    public Double convertFrom(BigDecimal destinationValue) {
        if (destinationValue == null) return null;
        return destinationValue.doubleValue();
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<BigDecimal> getDestinationType() {
        return BigDecimal.class;
    }
}
