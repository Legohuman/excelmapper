package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 23.03.14
 */
public interface ConversionEngine {

    Object convert(Class destinationType, Object sourceValue);
}
