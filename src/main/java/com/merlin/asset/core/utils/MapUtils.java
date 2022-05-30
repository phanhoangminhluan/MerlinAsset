package com.merlin.asset.core.utils;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2021.05.13 20:14
 */
public class MapUtils {

    public static ImmutableMap.Builder<String, Object> ImmutableMap() {
        return ImmutableMap.<String, Object>builder();
    }

    public static ImmutableMap.Builder<String, String> ImmutableMapStrStr() {
        return ImmutableMap.<String, String>builder();
    }

    public static ImmutableMap.Builder<String, Integer> ImmutableMapStrInt() {
        return ImmutableMap.<String, Integer>builder();
    }

    public static ZonedDateTime getZonedDateTime(@Nullable Map<String, Object> map, @Nonnull String key) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        try {
            Object raw = map.get(key);
            return (ZonedDateTime) raw;
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDate(@Nullable Map<String, Object> map, @Nonnull String key) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        try {
            return (Date) map.get(key);
        } catch (Exception exception) {
            return null;
        }
    }

    public static String getString(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getString(map, key, "");
    }

    public static String getString(@Nullable Map<String, Object> map, @Nonnull String key, String defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        String result = "";
        if (key.contains(".")) {
            BiFunction<Map<String, Object>, String, Object> getMethod = (aMap, singleKey) -> getString(aMap, singleKey, defaultValue);
            Object obj = getNestedObject(map, key, defaultValue, getMethod);
            try {
                result = ParserUtils.toString(obj, null);
                return result == null ? defaultValue : result;
            }catch (Exception e) {
                return defaultValue;
            }
        }

        try {
            result = ParserUtils.toString( map.get(key),null);

            if (result == null) return defaultValue;
            else return result;

        } catch (Exception exception) {
            return defaultValue;
        }
    }

    private static String getNestedString(@Nullable Map<String, Object> map, @Nonnull String key, String defaultValue) {

        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        try {
            String[] keys = key.split("\\.");

            if (keys.length == 0) {
                return defaultValue;
            }

            String result = null;
            Map<String, Object> tmpMap = map;
            for (int i = 0; i < keys.length; i++) {
                String singleKey = keys[i];

                if (i == keys.length - 1) {
                    result = getString(tmpMap, singleKey);
                } else {

                    tmpMap = getMapStringObject(tmpMap, singleKey);
                    if (tmpMap == null || tmpMap.isEmpty()) {
                        return defaultValue;
                    }
                }
            }
            if (result == null) return defaultValue;
            else return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static Object getNestedObject(@Nullable Map<String, Object> map, @Nonnull String key, Object defaultValue, BiFunction<Map<String, Object>, String, Object> getMethod) {

        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        try {
            String[] keys = key.split("\\.");

            if (keys.length == 0) {
                return defaultValue;
            }

            Object result = null;
            Map<String, Object> tmpMap = map;
            for (int i = 0; i < keys.length; i++) {
                String singleKey = keys[i];

                if (i == keys.length - 1) {
                    result = getMethod.apply(tmpMap, singleKey);
                } else {

                    tmpMap = getMapStringObject(tmpMap, singleKey);
                    if (tmpMap == null || tmpMap.isEmpty()) {
                        return defaultValue;
                    }
                }
            }
            if (result == null) return defaultValue;
            else return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(@Nullable Map<String, Object> map, @Nonnull String key) {
        if (map == null || map.isEmpty()) {
            return false;
        }
        try {
            return ParserUtils.toString(map.get(key)).equalsIgnoreCase("true");
        } catch (Exception exception) {
            return false;
        }
    }

    public static List<String> getListString(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getListString(map, key, new ArrayList<>());
    }

    public static List<String> getListString(@Nullable Map<String, Object> map, @Nonnull String key, List<String> defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        if (map.get(key) == null) {
            return defaultValue;
        }

        try {
            List<String> result = (List<String>) map.get(key);
            return result == null ? defaultValue : result;
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static List<Object> getListObject(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getListObject(map, key, new ArrayList<>());
    }

    public static List<Object> getListObject(@Nullable Map<String, Object> map, @Nonnull String key, List<Object> defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        if (map.get(key) == null) {
            return defaultValue;
        }

        try {
            List<Object> result = (List<Object>) map.get(key);
            return result == null ? defaultValue : result;
        } catch (Exception exception) {
            return defaultValue;
        }
    }


    public static Map<String, String> getMapStringString(@Nullable Map<String, Object> map, @Nonnull String key) {
        if (map == null || map.isEmpty()) {
            return new HashMap<>();
        }

        if (map.get(key) == null) {
            return new HashMap<>();
        }

        try {
            Map<String, String> result = (Map<String, String>) map.get(key);
            return result == null ? new HashMap<>() : result;
        } catch (Exception exception) {
            return new HashMap<>();
        }
    }

    public static Map<String, Object> getMapStringObject(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getMapStringObject(map, key, new HashMap<>());
    }

    public static Map<String, Object> getMapStringObject(@Nullable Map<String, Object> map, @Nonnull String key, Map<String, Object> defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }
        Map<String, Object> result;


        if (key.contains(".")) {
            BiFunction<Map<String, Object>, String, Object> getMethod = (aMap, singleKey) -> getMapStringObject(aMap, singleKey, defaultValue);
            Object obj = getNestedObject(map, key, defaultValue, getMethod);
            try {
                result = (Map<String, Object>) obj;
                return result == null ? defaultValue : result;
            }catch (Exception e) {
                return defaultValue;
            }
        }

        if (map.get(key) == null) {
            return defaultValue;
        }

        try {
            result = (Map<String, Object>) map.get(key);
            return result == null ? defaultValue : result;
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static int getInt(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getInt(map, key, 0);
    }

    public static int getInt(@Nullable Map<String, Object> map, @Nonnull String key, int defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        if (key.contains(".")) {
            BiFunction<Map<String, Object>, String, Object> getMethod = (aMap, singleKey) -> getInt(aMap, singleKey, defaultValue);
            Object obj = getNestedObject(map, key, defaultValue, getMethod);
            try {
                String doubleResult = ParserUtils.toString(obj);
                return new Double(doubleResult).intValue();
            }catch (Exception e) {
                return defaultValue;
            }
        }

        if (map.get(key) == null) {
            return defaultValue;
        }

        try {
            String doubleResult = ParserUtils.toString(map.get(key));
            return new Double(doubleResult).intValue();
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static double getDouble(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getDouble(map, key, 0);
    }

    public static double getDouble(@Nullable Map<String, Object> map, @Nonnull String key, double defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        if (key.contains(".")) {
            BiFunction<Map<String, Object>, String, Object> getMethod = (aMap, singleKey) -> getDouble(aMap, singleKey, defaultValue);
            Object obj = getNestedObject(map, key, defaultValue, getMethod);
            try {
                String doubleResult = ParserUtils.toString(obj);
                return new Double(doubleResult);
            }catch (Exception e) {
                return defaultValue;
            }
        }

        if (map.get(key) == null) {
            return defaultValue;
        }

        try {
            String doubleResult = ParserUtils.toString(map.get(key));
            return new Double(doubleResult);
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static long getLong(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getLong(map, key, 0);
    }
    public static long getLong(@Nullable Map<String, Object> map, @Nonnull String key, long defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        if (map.get(key) == null) {
            return defaultValue;
        }

        try {
            String doubleResult = ParserUtils.toString(map.get(key));
            return new Double(doubleResult).longValue();
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static Set<String> getSetString(@Nullable Map<String, Object> map, @Nonnull String key, Set<String> defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        if (map.get(key) == null) {
            return defaultValue;
        }

        try {
            return (Set<String>) map.get(key);
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static Set<String> getSetString(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getSetString(map, key, new HashSet<>());
    }

    public static List<Map<String, Object>> getListMapStringObject(@Nullable Map<String, Object> map, @Nonnull String key, List<Map<String, Object>> defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }

        List<Map<String, Object>> result = null;
        if (key.contains(".")) {
            BiFunction<Map<String, Object>, String, Object> getMethod = (aMap, singleKey) -> getListMapStringObject(aMap, singleKey, defaultValue);
            Object obj = getNestedObject(map, key, defaultValue, getMethod);
            try {
                result = (List<Map<String, Object>>) obj;
                return result == null ? defaultValue : result;
            } catch (Exception e) {
                return defaultValue;
            }
        }

        try {
            result = (List<Map<String, Object>>) map.get(key);

            if (result == null) return defaultValue;
            else return result;

        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static List<Map<String, Object>> getListMapStringObject(@Nullable Map<String, Object> map, @Nonnull String key) {
        return getListMapStringObject(map, key, new ArrayList<>());
    }

//    public static void main(String[] args) {
//        Map<String, Object> map = MapUtils.ImmutableMap()
//                .put("key1", 2980914608L)
//                .put("key2", "hiiiiiiii")
//                .put("key3", MapUtils.ImmutableMap()
//                        .put("key31", "okey-dokey")
//                        .put("key32", "hhohohohoho")
//                        .put("key33", MapUtils.ImmutableMap()
//                                .put("key331", 1)
//                                .put("key332", "ko co gi goi la last")
//                                .put("key3", MapUtils.ImmutableMap()
//                                        .put("key31", 1.0)
//                                        .put("key32", "hhohohohoho")
//                                        .build()
//                                ).build()
//                        ).build())
//                .build();
////        System.out.println(JsonUtil.toJson(MapUtil.getMapStringObject(map, "key3.key33.key33", MapUtil.ImmutableMap().put("goal", "being recognized").build())));
//        System.out.println(MapUtils.getString(map, "key3.key33.key3.key32",null));
//    }
}
