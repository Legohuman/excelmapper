package com.github.excelmapper.core.engine.converters;

/**
 * User: Dmitry Levin
 * Date: 23.03.14
 */
public interface Converter<S, D> {

    D convertTo(S sourceValue);

    S convertFrom(D destinationValue);

    Class<S> getSourceType();

    Class<D> getDestinationType();
}
