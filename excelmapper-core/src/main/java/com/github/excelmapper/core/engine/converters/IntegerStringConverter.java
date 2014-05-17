package com.github.excelmapper.core.engine.converters;

import com.github.excelmapper.core.utils.StringUtils;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class IntegerStringConverter implements Converter<Integer, String> {
    @Override
    public String convertTo(Integer sourceValue) {
        if (sourceValue == null) return null;
        return StringUtils.emptyIfNull(sourceValue);
    }

    @Override
    public Integer convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        return Integer.parseInt(destinationValue);
    }

    @Override
    public Class<Integer> getSourceType() {
        return Integer.class;
    }

    @Override
    public Class<String> getDestinationType() {
        return String.class;
    }
}
