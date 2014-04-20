package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.engine.converters.Converter;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class WritableConverterReference<S, D> implements WritableValueReference<D>, ContextAwareValueReference {

    private final WritableValueReference<S> wrappedRef;
    private final Converter<S, D> converter;

    protected WritableConverterReference(WritableValueReference<S> wrappedRef,
        Converter<S, D> converter) {
        this.wrappedRef = wrappedRef;
        this.converter = converter;
    }

    @Override
    public D getValue() {
        return converter.convertTo(wrappedRef.getValue());
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
    public void setContext(Object context) {
        if (wrappedRef instanceof ContextAwareValueReference) {
            ((ContextAwareValueReference)wrappedRef).setContext(context);
        }
    }
}
