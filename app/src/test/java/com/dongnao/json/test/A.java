package com.dongnao.json.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018/5/16.
 */

public abstract class A<T> {

    public A() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        System.out.println(parameterizedType.getActualTypeArguments()[0]);
    }
}

