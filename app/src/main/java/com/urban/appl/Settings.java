package com.urban.appl;

import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.data.dao.UrbanCriterion;

import java.sql.SQLException;

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
        if (user == null) {
            loggedUser = null;
            return;
        }

        try {
            UrbanCriterion<User> criterion = DAO.createCriterion(User.class);
            criterion.eq("login", user.getLogin());
            loggedUser = DAO.getUniqByCriterion(User.class, criterion);
            if (loggedUser == null) {
                DAO.save(user);
                //Needed for reinit ForeignCollection fields from null to new object with 0 size;
                loggedUser = DAO.getUniqByCriterion(User.class, criterion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getLoggedUser() {
        return loggedUser;
    }
}
