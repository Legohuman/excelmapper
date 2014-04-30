package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.engine.converters.Converter;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class ReadableConverterReference<S, D, C> implements ReadableValueReference<D>, ContextAware<C> {

    private final ReadableValueReference<S> wrappedRef;
    private final Converter<S, D> converter;

    protected ReadableConverterReference(ReadableValueReference<S> wrappedRef,
        Converter<S, D> converter) {
        this.wrappedRef = wrappedRef;
        this.converter = converter;
    }

    @Override
    public D getValue() {
        return converter.convertTo(wrappedRef.getValue());
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
