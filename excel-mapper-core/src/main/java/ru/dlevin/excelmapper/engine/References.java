package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.engine.converters.Converter;

/**
 * User: Dmitry Levin
 * Date: 15.03.14
 */
public class References {

    public static <T> BeanPropertyValueReference<T> property(String propertyName) {
        return new BeanPropertyValueReference<T>(propertyName);
    }

    public static <T> StaticValueReference<T> value(T value) {
        return new StaticValueReference<T>(value);
    }

    public static <S, D> WritableConverterReference<S, D> converter(WritableValueReference<S> valueReference, Converter<S, D> converter) {
        return new WritableConverterReference<S, D>(valueReference, converter);
    }

    public static <S, D> ReadableConverterReference<S, D> converter(ReadableValueReference<S> valueReference, Converter<S, D> converter) {
        return new ReadableConverterReference<S, D>(valueReference, converter);
    }

    public static MapPropertyValueReference key(String key) {
        return new MapPropertyValueReference(key);
    }
}
