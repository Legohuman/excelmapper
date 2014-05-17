package com.github.excelmapper.core.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public abstract class ContextAwareValueReference<C, T>
    extends AbstractContextAwareValueReference<C, T> {

    @Override
    public T getValue() {
        throw new UnsupportedOperationException("Implement method in your subclass");
    }

    @Override
    public void setValue(T value) {
        throw new UnsupportedOperationException("Implement method in your subclass");
    }
}
