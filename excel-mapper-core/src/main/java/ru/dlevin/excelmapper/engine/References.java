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

    public static <S, D, C> WritableConverterReference<S, D, C> converter(WritableValueReference<S> valueReference,
        Converter<S, D> converter) {
        return new WritableConverterReference<S, D, C>(valueReference, converter);
    }

    public static <S, D, C> ReadableConverterReference<S, D, C> converter(ReadableValueReference<S> valueReference,
        Converter<S, D> converter) {
        return new ReadableConverterReference<S, D, C>(valueReference, converter);
    }

    public static MapPropertyValueReference key(String key) {
        return new MapPropertyValueReference(key);
    }
}
