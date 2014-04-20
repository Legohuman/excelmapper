package ru.dlevin.excelmapper.engine.converters;

import ru.dlevin.excelmapper.utils.StringUtils;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class ShortStringConverter implements Converter<Short, String> {
    @Override
    public String convertTo(Short sourceValue) {
        if (sourceValue == null) return null;
        return StringUtils.emptyIfNull(sourceValue);
    }

    @Override
    public Short convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        return Short.parseShort(destinationValue);
    }

    @Override
    public Class<Short> getSourceType() {
        return Short.class;
    }

    @Override
    public Class<String> getDestinationType() {
        return String.class;
    }
}
