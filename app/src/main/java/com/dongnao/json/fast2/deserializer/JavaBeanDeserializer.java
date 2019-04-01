package com.dongnao.json.fast2.deserializer;

import com.dongnao.json.fast2.FieldInfo;
import com.dongnao.json.fast2.JsonConfig;
import com.dongnao.json.fast2.Utils;
import com.dongnao.json.fast2.serializer.ObjectSerializer;
import com.dongnao.json.fast2.serializer.SerializerContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/16.
 */

public class JavaBeanDeserializer implements ObjectDeserializer {

    private final Class<?> beanType;
    private final List<FieldInfo> fieldInfos;

    public JavaBeanDeserializer(Class<?> clazz) {
        this.beanType = clazz;
        Map<String, Field> fieldCacheMap = new HashMap<>();
        Utils.parserAllFieldToCache(fieldCacheMap, beanType);
        fieldInfos = Utils.computeSetters(beanType, fieldCacheMap);
    }


    @Override
    public <T> T deserializer(JsonConfig config, String json, Object object) throws Throwable {
        JSONObject jsonObject;
        if (null == object) {
            jsonObject = new JSONObject(json);
        } else {
            jsonObject = (JSONObject) object;
        }
        T t = null;
        try {
            t = (T) beanType.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //{"age":100,"name":"testname","test":1,"list":["1","2"]}
        for (FieldInfo fieldInfo : fieldInfos) {
            //json数据中没有对应的key
            if (!jsonObject.has(fieldInfo.name)) {
                continue;
            }
            Object value = jsonObject.get(fieldInfo.name);
            // "list":["1","2"]}
            if (value instanceof JSONObject || value instanceof JSONArray) {
                //todo
                ObjectDeserializer deserializer = config.getDeserializer(fieldInfo.genericType);
                Object obj = deserializer.deserializer(config, null, value);
                fieldInfo.set(t, obj);
            } else {
                //{name='T1', age=100, list=[1, 2], childs=null}
                // childs
                if (value != JSONObject.NULL) {
                    fieldInfo.set(t, value);
                }
            }
        }

        return t;
    }
}
