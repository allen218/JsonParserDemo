package com.dongnao.json.fast2.serializer;

import com.dongnao.json.fast2.JsonConfig;
import com.dongnao.json.fast2.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/14.
 * <p>
 * 专门负责 对一个javaBean对象进行序列化
 */
public class JavaBeanSerializer implements ObjectSerializer {

    //需要序列化的成员
    private final List<FieldSerializer> fieldSerializers;

    public JavaBeanSerializer(Class<?> beanType) {
        //1、查找自己与父类所有的 符合条件的 函数  getXX  isXX
        //2、查找自己与父类的public的Filed

        //所有的属性
        Map<String, Field> fieldCacheMap = new HashMap<>();
        Utils.parserAllFieldToCache(fieldCacheMap, beanType);
        fieldSerializers = Utils.computeGetters(beanType, fieldCacheMap);
    }

    @Override
    public void serializer(SerializerContext context, JsonConfig config, StringBuilder out, Object object) {
        //{"age":100,"name":"testname","test":1,"list":["1","2"]}
        out.append("{");
        boolean lastEmpty = false;
        for (FieldSerializer fieldSerializer : fieldSerializers) {

            //"name":"testname" 、 "age":100
            // 如果遇到属性没有值 (null) 则返回 ""
            String serializer = fieldSerializer.serializer(context != null ? context : new SerializerContext(), config, object);
            //
            if (lastEmpty && !serializer.isEmpty()) {
                out.append(",");
            }
            if (!lastEmpty) {
                lastEmpty = !serializer.isEmpty();
            }
            out.append(serializer);

        }
        out.append("}");
    }
}
