package ru.dlevin.excelmapper.utils;

/**
 * User: Dmitry Levin
 * Date: 08.03.14
 */
public class StringUtils {
    public static String emptyIfNull(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
