package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.engine.converters.Converter;
import com.github.excelmapper.core.engine.converters.ReverseConverter;
import com.github.excelmapper.core.exceptions.ConversionException;
import com.github.excelmapper.core.utils.Pair;
import org.apache.commons.beanutils.MethodUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Dmitry Levin
 * Date: 23.03.14
 */
public class SimpleConversionEngine implements ConversionEngine {
    //source, destination types -> converter
    private Map<Pair<Class, Class>, Converter> converters = new HashMap<Pair<Class, Class>, Converter>();

    public <S, D> void registerConverter(Class<S> sourceType, Class<D> destinationType, Converter<S, D> converter) {
        converters.put(new Pair<Class, Class>(sourceType, destinationType), converter);
    }

    @Override
    public Object convert(Class destinationType, Object sourceValue) {
        if (sourceValue == null) return null;
        Class sourceType = MethodUtils.toNonPrimitiveClass(sourceValue.getClass());
        Class realDestinationType = MethodUtils.toNonPrimitiveClass(destinationType);
        if (realDestinationType.isAssignableFrom(sourceType)) {
            return sourceValue;
        }
        Converter converter = converters.get(new Pair<Class, Class>(sourceType, realDestinationType));
        if (converter == null) {
            converter = findReverseConverter(sourceType, realDestinationType);
        }
        if (converter == null) {
            for (Map.Entry<Pair<Class, Class>, Converter> entry : converters.entrySet()) {
                Pair<Class, Class> classPair = entry.getKey();
                if (destinationType.isAssignableFrom(classPair.getRight()) && sourceType.isAssignableFrom(classPair.getLeft())) {
                    converter = entry.getValue();
                    break;
                } else if (destinationType.isAssignableFrom(classPair.getLeft()) && sourceType.isAssignableFrom(classPair.getRight())) {
                    converter = new ReverseConverter(entry.getValue());
                    break;
                }
            }
        }

        if (converter != null) {
            try {
                return converter.convertTo(sourceValue);
            } catch (IllegalArgumentException e) {
                throw new ConversionException("Can not convert value: " + sourceValue + " to type: " + destinationType.getName(), e);
            }
        }
        throw new ConversionException(
            "Can not find appropriate converter from type: " + sourceType.getName() + " to type: " + destinationType.getName());
    }

    private Converter findReverseConverter(Class sourceType, Class realDestinationType) {
        Converter converter = converters.get(new Pair<Class, Class>(realDestinationType, sourceType));
        return converter == null ? null : new ReverseConverter(converter);
    }
}
