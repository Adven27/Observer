package com.urban.appl;

public class Settings {

    private String daoClassName;

    public String getDaoImplClassName(){
        return null;
    }

    public static String getDBName(){
        //TODO: may be take it from settings?
        return "urban.db";
    }

    public enum RemoteConnectionType {

        LOCALHOST_CONNECTED_DEVICE("http://176.126.48.121:8080/"),
        LOCALHOST_EMULATOR("http://10.0.2.2:8080/"),
        REMOTE_SERVER("");

        RemoteConnectionType(String URLBase) {
            this.URLBase = URLBase;
        }

        private String URLBase;


        public static String getURLBase() {
            return "http://10.0.2.2:8080/";
        }

    }

    private static RemoteConnectionType rct = RemoteConnectionType.LOCALHOST_EMULATOR;

    public static String getURLBase() {
        return rct.URLBase;
    }
}
