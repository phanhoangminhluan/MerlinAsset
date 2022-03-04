package com.merlin.asset.core.utils;

import com.google.gson.*;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2021.05.08 20:08
 */
public class JsonUtils {

    private static final Gson GSON = new Gson();

    private static final Gson GSON_NULL = new GsonBuilder().serializeNulls().create();

    public static String toJson(Object src, boolean isNull) {
        if (isNull) return GSON_NULL.toJson(src);
        else return GSON.toJson(src);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }

    public enum TYPE_TOKEN {
        MAP_STRING_OBJECT(new TypeToken<Map<String, Object>>() {
        }.getType()),
        MAP_STRING_STRING(new TypeToken<Map<String, String>>() {
        }.getType()),

        LIST_MAP_STRING_OBJECT(new TypeToken<List<Map<String, Object>>>() {
        }.getType()),
        LIST_MAP_STRING_STRING(new TypeToken<List<Map<String, String>>>() {
        }.getType()),
        LIST_STRING(new TypeToken<List<String>>() {
        }.getType());

        public final Type type;

        TYPE_TOKEN(Type type) {
            this.type = type;
        }

    }

    public static String toJson(Object src, Type typeOfSrc) {
        return GSON.toJson(src, typeOfSrc);
    }

    public static String toJson(JsonElement jsonElement) {
        return GSON.toJson(jsonElement);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return GSON.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return GSON.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return GSON.fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        Object object = fromJson(json, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        return GSON.fromJson(json, typeOfT);
    }
}

