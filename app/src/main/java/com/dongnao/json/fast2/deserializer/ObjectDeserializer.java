package com.dongnao.json.fast2.deserializer;

import com.dongnao.json.fast2.JsonConfig;

/**
 * Created by Administrator on 2018/5/16.
 */

public interface ObjectDeserializer {

    <T> T deserializer(JsonConfig config, String json, Object object) throws Throwable;
}
