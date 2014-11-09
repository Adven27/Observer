package com.urban.service.urban.impl.http;

/**
 * Created by MetallFoX on 09.11.2014.
 */
public class UrbanServiceHttpSettings {

    private static final String ENDPOINT = "ServicesTomcatWAR/";

    public enum RemoteConnectionType {

        LOCALHOST_CONNECTED_DEVICE("http://192.168.1.227:8080/"),
        LOCALHOST_EMULATOR("http://10.0.2.2:8080/"),
        REMOTE_SERVER("");

        RemoteConnectionType(String URLBase) {
            this.URLBase = URLBase;
        }

        private String URLBase;
        public String getURLBase() {
            return URLBase;
        }

    }

    private static RemoteConnectionType rct = RemoteConnectionType.LOCALHOST_CONNECTED_DEVICE;

    public static String getURLBase() {
        return rct.URLBase + ENDPOINT;
    }

}
