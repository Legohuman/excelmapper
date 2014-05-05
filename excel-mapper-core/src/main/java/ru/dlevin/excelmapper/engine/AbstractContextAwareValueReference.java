package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public abstract class AbstractContextAwareValueReference<C, T> extends AbstractProcessMessagesHolderAware
    implements ValueReference<T>, ContextAware<C> {

    private C context;

    @SuppressWarnings("unchecked")
    @Override
    public void setContext(C context) {
        this.context = context;
    }

    @Override
    public C getContext() {
        return context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class getType() {
        return context.getClass();
    }
}
