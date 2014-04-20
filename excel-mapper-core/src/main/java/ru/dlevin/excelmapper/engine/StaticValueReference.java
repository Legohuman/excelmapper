package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class StaticValueReference<T> implements ReadableValueReference<T> {

    private final T value;

    public StaticValueReference(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> getType() {
        return (Class<T>)value.getClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StaticValueReference that = (StaticValueReference)o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
