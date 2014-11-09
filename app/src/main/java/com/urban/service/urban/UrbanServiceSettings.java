package com.urban.service.urban;

/**
 * Created by MetallFoX on 09.11.2014.
 */
public class UrbanServiceSettings {

    private static UrbanServiceType type;

    public enum UrbanServiceType {
        STUB,
        HTTP_POST,
        WEB_SERVICE;
    }

    public static UrbanServiceType getServiceType() {
        return type;
    }

    public static void setServiceType(UrbanServiceType type) {
        UrbanServiceSettings.type = type;
    }
}
