package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.engine.converters.Converter;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class ReadableConverterReference<S, D> implements ReadableValueReference<D>, ContextAwareValueReference {

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
    public void setContext(Object context) {
        if (wrappedRef instanceof ContextAwareValueReference) {
            ((ContextAwareValueReference)wrappedRef).setContext(context);
        }
    }
}
