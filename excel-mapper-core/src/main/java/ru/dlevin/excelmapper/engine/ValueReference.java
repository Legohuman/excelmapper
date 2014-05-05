package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public interface ValueReference<T> {

    T getValue();

    void setValue(T value);

    Class<T> getType();
}
