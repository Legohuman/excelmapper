package com.github.excelmapper.core.utils;

/**
 * User: Dmitry Levin
 * Date: 11.03.14
 */
public class MathUtils {
    public static int safeSubtract(int a, int b) {
        int diff = a - b;
        // If there is a sign change, but the two values have different signs...
        if ((a ^ diff) < 0 && (a ^ b) < 0) {
            throw new ArithmeticException
                ("The calculation caused an overflow: " + a + " - " + b);
        }
        return diff;
    }

    public static int safeAdd(int val1, int val2) {
        int sum = val1 + val2;
        // If there is a sign change, but the two values have the same sign...
        if ((val1 ^ sum) < 0 && (val1 ^ val2) >= 0) {
            throw new ArithmeticException
                ("The calculation caused an overflow: " + val1 + " + " + val2);
        }
        return sum;
    }
}
