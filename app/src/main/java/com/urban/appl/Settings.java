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
}
