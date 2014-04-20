package ru.dlevin.excelmapper.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class CollectionUtils {

    public static <T> Set<T> hashSet(T... a) {
        return new HashSet<T>(Arrays.asList(a));
    }
}
