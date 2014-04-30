package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public abstract class PropertyValueReference<C, T>
    implements ReadableValueReference<T>, WritableValueReference<T>, ContextAware<C> {

    private final String property;

    protected PropertyValueReference(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertyValueReference that = (PropertyValueReference)o;

        if (property != null ? !property.equals(that.property) : that.property != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return property != null ? property.hashCode() : 0;
    }
}
