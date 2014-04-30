package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public interface ContextAware<C> {

    void setContext(C context);

    C getContext();
}
