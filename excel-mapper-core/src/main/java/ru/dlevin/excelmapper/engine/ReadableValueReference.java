package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public interface ReadableValueReference<T> {

    T getValue();

    Class<T> getType();
}
