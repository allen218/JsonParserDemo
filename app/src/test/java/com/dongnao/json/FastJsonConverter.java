package com.dongnao.json;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONWriter;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Lance
 * @date 2018/5/15
 */

public class FastJsonConverter extends Converter.Factory {


    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[]
            parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new RequestConverter<>();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new ResponseConverter<>(type);
    }


    static class ResponseConverter<T> implements Converter<ResponseBody, T> {


        private final Type type;

        public ResponseConverter(Type type) {
            this.type = type;

        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                return JSON.parseObject(value.string(), type);
            } finally {
                value.close();
            }
        }
    }

    static class RequestConverter<T> implements Converter<T, RequestBody> {

        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; " +
                "charset=UTF-8");


        @Override
        public RequestBody convert(T value) throws IOException {
            String json = JSON.toJSONString(value);
            return RequestBody.create(MEDIA_TYPE, json);
        }
    }
}
