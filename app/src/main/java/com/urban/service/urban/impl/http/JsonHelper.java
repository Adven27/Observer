package com.urban.service.urban.impl.http;

import java.util.Date;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * Created by MetallFoX on 09.11.2014.
 */
public class JsonHelper {

    private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("yyyy-MM-dd'T'HH:mm:ssz");

    public static String toJSON(Object obj) {
        return new JSONSerializer().transform(DATE_TRANSFORMER, Date.class).exclude("*.class").deepSerialize(obj);
    }

    public static <T> T fromJSON(Class <T> clazz, String json) {
        return new JSONDeserializer<T>().deserialize(json);
    }

}
