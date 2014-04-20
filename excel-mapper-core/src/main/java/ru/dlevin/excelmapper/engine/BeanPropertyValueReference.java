package ru.dlevin.excelmapper.engine;

import org.apache.commons.beanutils.PropertyUtils;
import ru.dlevin.excelmapper.exceptions.EvaluationException;
import ru.dlevin.excelmapper.utils.Validate;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class BeanPropertyValueReference<T> extends PropertyValueReference<T> {

    private Object bean;

    public BeanPropertyValueReference(String property) {
        super(property);
    }

    @Override
    public void setContext(Object context) {
        this.bean = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getValue() {
        String property = getProperty();
        try {
            Validate.notNull(bean, "Can not get property \"" + property + "\" value from null bean object.");
            return (T)PropertyUtils.getProperty(bean, property);
        } catch (IllegalAccessException e) {
            handleException(property, e);
        } catch (InvocationTargetException e) {
            handleException(property, e);
        } catch (NoSuchMethodException e) {
            handleException(property, e);
        }
        return null;
    }

    @Override
    public void setValue(T value) {
        String property = getProperty();
        try {
            PropertyUtils.setProperty(bean, property, value);
        } catch (IllegalArgumentException e) {
            handleException(property, e);
        } catch (IllegalAccessException e) {
            handleException(property, e);
        } catch (InvocationTargetException e) {
            handleException(property, e);
        } catch (NoSuchMethodException e) {
            handleException(property, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> getType() {
        String property = getProperty();
        try {
            PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(bean, property);
            if (propertyDescriptor == null) {
                throw new EvaluationException("Can not evaluate property " + property + " of bean with class " + bean.getClass().getName() +
                    ". Property is not found.");
            }
            return (Class<T>)propertyDescriptor.getPropertyType();
        } catch (IllegalAccessException e) {
            handleException(property, e);
        } catch (InvocationTargetException e) {
            handleException(property, e);
        } catch (NoSuchMethodException e) {
            handleException(property, e);
        }
        return null;
    }

    private void handleException(String property, Exception e) {
        throw new EvaluationException("Can not evaluate property " + property + " of bean with class " + bean.getClass().getName(), e);
    }
}
