package com.urban.service.urban.impl.http;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * Created by MetallFoX on 09.11.2014.
 */
public class JsonHelper {

    public static String toJSON(Object obj) {
        return new JSONSerializer().exclude("*.class").deepSerialize(obj);
    }

    public static <T> T fromJSON(Class <T> clazz, String json) {
        return new JSONDeserializer<T>().deserialize(json);
    }

}
