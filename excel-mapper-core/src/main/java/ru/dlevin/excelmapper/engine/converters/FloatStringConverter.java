package ru.dlevin.excelmapper.engine.converters;

import ru.dlevin.excelmapper.utils.StringUtils;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class FloatStringConverter implements Converter<Float, String> {
    @Override
    public String convertTo(Float sourceValue) {
        if (sourceValue == null) return null;
        return StringUtils.emptyIfNull(sourceValue);
    }

    @Override
    public Float convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        return Float.parseFloat(destinationValue);
    }

    @Override
    public Class<Float> getSourceType() {
        return Float.class;
    }

    @Override
    public Class<String> getDestinationType() {
        return String.class;
    }
}
