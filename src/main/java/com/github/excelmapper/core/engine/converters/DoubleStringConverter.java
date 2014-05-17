package com.github.excelmapper.core.engine.converters;

import com.github.excelmapper.core.utils.StringUtils;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DoubleStringConverter implements Converter<Double, String> {
    @Override
    public String convertTo(Double sourceValue) {
        if (sourceValue == null) return null;
        return StringUtils.emptyIfNull(sourceValue);
    }

    @Override
    public Double convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        return Double.parseDouble(destinationValue);
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<String> getDestinationType() {
        return String.class;
    }
}
