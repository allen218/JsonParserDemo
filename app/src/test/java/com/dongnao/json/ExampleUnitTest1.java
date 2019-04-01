package com.dongnao.json;

import com.dongnao.json.test.A;
import com.dongnao.json.test.JsonRootBean;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test.json, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest1 {


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);


        Method method = JsonRootBean.class.getMethod("setItems2", List.class);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        System.out.println(parameterTypes[0]);

        System.out.println((genericParameterTypes[0] instanceof ParameterizedType));

        if (genericParameterTypes[0] instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericParameterTypes[0];
            Type actualType = type.getActualTypeArguments()[0];
            if (actualType instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) actualType;
                Type[] upperBounds = wildcardType.getUpperBounds();
                System.out.println(upperBounds[0]);
            }
        }

    }

    class B extends A<String> {

    }

    @Test
    public void addition_isCorrect1() throws Exception {
        //匿名内部类
//        new A<String>() {
//        };

        new A<String>(){};

        new B();

    }


}