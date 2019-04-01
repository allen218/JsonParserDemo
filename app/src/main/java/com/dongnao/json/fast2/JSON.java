package com.dongnao.json.fast2;

import com.dongnao.json.fast2.deserializer.ObjectDeserializer;
import com.dongnao.json.fast2.serializer.ObjectSerializer;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018/5/14.
 */

public class JSON {

    public static String toJSONString(Object object) {
        ObjectSerializer serializer = JsonConfig.getGlobalInstance().getSerializer(object.getClass());
        StringBuilder sb = new StringBuilder();
        serializer.serializer(null, JsonConfig.getGlobalInstance(), sb, object);
        return sb.toString();
    }


    public static <T> T parse(String json, Class<T> clazz) {
        return parse(json, (Type) clazz);
    }

    public static <T> T parse(String json, Type type) {
        ObjectDeserializer deserializer = JsonConfig.getGlobalInstance().getDeserializer(type);
        try {
            return deserializer.deserializer(JsonConfig.getGlobalInstance(), json, null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
