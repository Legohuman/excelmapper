package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.engine.converters.Converter;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class WritableConverterReference<S, D, C> implements WritableValueReference<D>, ContextAware<C> {

    private final WritableValueReference<S> wrappedRef;
    private final Converter<S, D> converter;

    protected WritableConverterReference(WritableValueReference<S> wrappedRef,
        Converter<S, D> converter) {
        this.wrappedRef = wrappedRef;
        this.converter = converter;
    }

    @Override
    public void setValue(D value) {
        wrappedRef.setValue(converter.convertFrom(value));
    }

    @Override
    public Class<D> getType() {
        return converter.getDestinationType();
    }

    @Override
    public void setContext(C context) {
        if (wrappedRef instanceof ContextAware) {
            ((ContextAware<C>)wrappedRef).setContext(context);
        }
    }

    @Override
    public C getContext() {
        return (wrappedRef instanceof ContextAware) ? ((ContextAware<C>)wrappedRef).getContext() : null;
    }
}
