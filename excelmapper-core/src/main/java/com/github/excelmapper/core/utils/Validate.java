package com.github.excelmapper.core.utils;

/**
 * User: Dmitry Levin
 * Date: 11.03.14
 */
public class Validate {

    public static void notNull(Object obj, String message) {
        isTrue(obj != null, message);
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }
}
