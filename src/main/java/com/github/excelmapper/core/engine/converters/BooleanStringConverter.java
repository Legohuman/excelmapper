package com.github.excelmapper.core.engine.converters;

import com.github.excelmapper.core.utils.StringUtils;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class BooleanStringConverter implements Converter<Boolean, String> {

    @Override
    public String convertTo(Boolean sourceValue) {
        if (sourceValue == null) return null;
        return StringUtils.emptyIfNull(sourceValue);
    }

    @Override
    public Boolean convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        return Boolean.getBoolean(destinationValue);
    }

    @Override
    public Class<Boolean> getSourceType() {
        return Boolean.class;
    }

    @Override
    public Class<String> getDestinationType() {
        return String.class;
    }
}
