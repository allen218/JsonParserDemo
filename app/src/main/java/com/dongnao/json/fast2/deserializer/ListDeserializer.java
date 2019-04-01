package com.dongnao.json.fast2.deserializer;

import com.dongnao.json.fast2.JsonConfig;
import com.dongnao.json.fast2.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class ListDeserializer implements ObjectDeserializer {

    private final ParameterizedType type;

    public ListDeserializer(ParameterizedType type) {
        this.type = type;
    }

    @Override
    public <T> T deserializer(JsonConfig config, String json, Object object) throws Throwable {
        JSONArray jsonArray;
        if (null == object) {
            jsonArray = new JSONArray(json);
        } else {
            jsonArray = (JSONArray) object;
        }

        List list = new ArrayList();

        //解析list item
        for (int i = 0; i < jsonArray.length(); i++) {
            //
            Object itemObj = jsonArray.get(i);
            if (itemObj instanceof JSONArray || itemObj instanceof JSONObject) {
                //获得泛型类型
                Type itemType = Utils.getItemType(type);
                ObjectDeserializer deserializer = config.getDeserializer(itemType);
                Object item = deserializer.deserializer(config, null, itemObj);
                list.add(item);
            } else {
                list.add(itemObj);
            }
        }

        return (T) list;
    }
}
