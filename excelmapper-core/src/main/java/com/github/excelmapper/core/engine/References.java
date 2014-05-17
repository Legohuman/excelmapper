package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.engine.converters.Converter;

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

    public static ConverterReference converter(ValueReference valueReference,
        Converter converter) {
        return new ConverterReference(valueReference, converter);
    }

    public static MapPropertyValueReference key(String key) {
        return new MapPropertyValueReference(key);
    }
}
