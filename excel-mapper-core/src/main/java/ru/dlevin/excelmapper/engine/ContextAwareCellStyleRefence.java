package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public abstract class ContextAwareCellStyleRefence implements CellStyleReference {

    private Object context;

    public void setContext(Object context) {
        this.context = context;
    }

    public Object getContext() {
        return context;
    }
}
