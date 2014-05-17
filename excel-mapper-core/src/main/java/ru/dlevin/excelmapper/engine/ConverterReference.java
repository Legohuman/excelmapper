package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.engine.converters.Converter;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class ConverterReference<S, D, C> extends AbstractProcessMessagesHolderAware implements ValueReference<D>, ContextAware<C> {

    private final ValueReference<S> wrappedRef;
    private final Converter<S, D> converter;

    public ConverterReference(ValueReference<S> wrappedRef,
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
