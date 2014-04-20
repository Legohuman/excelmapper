package ru.dlevin.excelmapper.engine;

import java.util.Map;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class MapPropertyValueReference extends PropertyValueReference<Object> {
    private Map<String, Object> map;

    public MapPropertyValueReference(String property) {
        super(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setContext(Object context) {
        this.map = (Map)context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getValue() {
        return map.get(getProperty());
    }

    @Override
    public void setValue(Object value) {
        map.put(getProperty(), value);
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }
}
