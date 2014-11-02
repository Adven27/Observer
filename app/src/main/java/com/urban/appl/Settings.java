package com.urban.appl;

import com.urban.data.User;

public class Settings {

    private String daoClassName;

    public String getDaoImplClassName(){
        return null;
    }

    public static String getDBName(){
        //TODO: may be take it from settings?
        return "urban.db";
    }

    private static User loggedUser = null;

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }


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
        return rct.URLBase;
    }
}
