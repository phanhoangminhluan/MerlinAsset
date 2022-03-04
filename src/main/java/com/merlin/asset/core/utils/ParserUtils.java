package com.merlin.asset.core.utils;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2021.05.08 20:14
 */
public class ParserUtils {

    public static String toString(Object obj) {
        if (obj == null) return "";
        else return obj.toString();
    }

    public static String toString(Object obj, String defaultValue) {
        if (obj == null) return defaultValue;
        else return obj.toString();
    }

    public static boolean isNullOrEmpty(Object o) {
        String str = toString(o);
        return str.isEmpty();
    }

    public static int toInt(Object obj, int defaultValue) {
        String numString = toString(obj);
        if (ParserUtils.isNullOrEmpty(numString)) return defaultValue;
        try {
            return Integer.parseInt(numString);

        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    public static long toLong(Object obj, long defaultValue) {
        String numString = toString(obj);
        if (ParserUtils.isNullOrEmpty(numString)) return defaultValue;
        try {
            double db = Double.parseDouble(numString);
            return Math.round(db);

        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long toLong(Object obj) {
        return toLong(obj, 0);
    }

    public static double toDouble(Object obj, double defaultValue) {
        String numString = toString(obj);
        if (ParserUtils.isNullOrEmpty(numString)) return defaultValue;
        try {
            return Double.parseDouble(numString);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double toDouble(Object obj) {
        return toDouble(obj, 0);
    }

    public static boolean toBoolean(Object obj, boolean defaultValue) {
        if (isNullOrEmpty(obj)) return defaultValue;
        String value = toString(obj);
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static boolean toBoolean(Object obj) {
        return toBoolean(obj, false);
    }

}
