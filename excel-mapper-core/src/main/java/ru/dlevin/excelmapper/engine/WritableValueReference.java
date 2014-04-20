package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public interface WritableValueReference<T> extends ReadableValueReference<T> {

    void setValue(T value);
}
