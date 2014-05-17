package com.github.excelmapper.core.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class ReverseConverter<S, D> implements Converter<S, D> {

    private final Converter<D, S> converter;

    public ReverseConverter(Converter<D, S> converter) {
        this.converter = converter;
    }

    @Override
    public D convertTo(S sourceValue) {
        return converter.convertFrom(sourceValue);
    }

    @Override
    public S convertFrom(D destinationValue) {
        return converter.convertTo(destinationValue);
    }

    @Override
    public Class<S> getSourceType() {
        return converter.getDestinationType();
    }

    @Override
    public Class<D> getDestinationType() {
        return converter.getSourceType();
    }
}
