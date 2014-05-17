package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.exceptions.EvaluationException;
import com.github.excelmapper.core.utils.Validate;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class BeanPropertyValueReference extends PropertyValueReference<Object, Object> {

    private Object bean;

    public BeanPropertyValueReference(String property) {
        super(property);
    }

    @Override
    public void setContext(Object context) {
        this.bean = context;
    }

    @Override
    public Object getContext() {
        return bean;
    }

    @Override
    public Object getValue() {
        String property = getProperty();
        try {
            Validate.notNull(bean, "Can not get property \"" + property + "\" value from null bean object.");
            return PropertyUtils.getProperty(bean, property);
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
    public void setValue(Object value) {
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
    public Class getType() {
        String property = getProperty();
        try {
            PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(bean, property);
            if (propertyDescriptor == null) {
                throw new EvaluationException("Can not evaluate property " + property + " of bean with class " + bean.getClass().getName() +
                    ". Property is not found.");
            }
            return propertyDescriptor.getPropertyType();
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
