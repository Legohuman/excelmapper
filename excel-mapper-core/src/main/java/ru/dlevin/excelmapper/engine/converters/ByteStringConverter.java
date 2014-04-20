package ru.dlevin.excelmapper.engine.converters;

import ru.dlevin.excelmapper.utils.StringUtils;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class ByteStringConverter implements Converter<Byte, String> {
    @Override
    public String convertTo(Byte sourceValue) {
        if (sourceValue == null) return null;
        return StringUtils.emptyIfNull(sourceValue);
    }

    @Override
    public Byte convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        return Byte.parseByte(destinationValue);
    }

    @Override
    public Class<Byte> getSourceType() {
        return Byte.class;
    }

    @Override
    public Class<String> getDestinationType() {
        return String.class;
    }
}
