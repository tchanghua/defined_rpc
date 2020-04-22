package com.lagou.service;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

/**
 * @ClassName:JSONSerializer
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/4/22
 */
public class JSONSerializer implements Serializer {
    public byte[] serialize(Object object) throws IOException {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
        return JSON.parseObject(bytes, clazz);
    }
}