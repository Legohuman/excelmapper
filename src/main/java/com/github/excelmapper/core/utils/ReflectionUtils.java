package com.github.excelmapper.core.utils;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class ReflectionUtils {
    public static Class<?> getCorrsepondigObjectType(Class<?> type) {
        if (type.isPrimitive()) {
            if (byte.class.equals(type)) {
                return Byte.class;
            } else if (short.class.equals(type)) {
                return Short.class;
            } else if (int.class.equals(type)) {
                return Integer.class;
            } else if (long.class.equals(type)) {
                return Long.class;
            } else if (float.class.equals(type)) {
                return Float.class;
            } else if (double.class.equals(type)) {
                return Double.class;
            } else if (boolean.class.equals(type)) {
                return Boolean.class;
            } else if (char.class.equals(type)) {
                return Character.class;
            } else if (void.class.equals(type)) {
                return Void.class;
            }
        }
        return type;
    }
}
