package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.engine.converters.Converter;

/**
 * User: Dmitry Levin
 * Date: 15.03.14
 */
public class References {

    public static BeanPropertyValueReference property(String propertyName) {
        return new BeanPropertyValueReference(propertyName);
    }

    public static <T> StaticValueReference<T> value(T value) {
        return new StaticValueReference<T>(value);
    }

    public static <S, D, C> ConverterReference<S, D, C> converter(ValueReference<S> valueReference,
        Converter<S, D> converter) {
        return new ConverterReference<S, D, C>(valueReference, converter);
    }

    public static MapPropertyValueReference key(String key) {
        return new MapPropertyValueReference(key);
    }
}
