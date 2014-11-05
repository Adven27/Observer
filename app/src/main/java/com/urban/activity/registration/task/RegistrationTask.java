package com.urban.activity.registration.task;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.urban.activity.registration.RegistrationActivity;
import com.urban.data.Person;
import com.urban.data.ResponseError;
import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.task.HttpTask;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.Date;

import src.com.urban.data.sqlite.pojo.PersonPojo;
import src.com.urban.data.sqlite.pojo.UserPojo;

public class RegistrationTask implements HttpTask {

    private static final int NUMBER_OF_PARAMETERS = 8;

    private WeakReference<RegistrationActivity> parent;

    private String errorMsg;
    private User loggedUser;

    public RegistrationTask(RegistrationActivity parent) {
        super();
        this.parent = new WeakReference<RegistrationActivity>(parent);
    }

    @Override
    public Object prepareRequestData(String... params) {
        if (params.length == NUMBER_OF_PARAMETERS) {
            return register(params[0], params[1], params[2], params[3], params[4], params[5], null, params[7]);
        }
        return null;
    }

    private User register(String login, String password, String email, String surname, String name, String secondName, Date birthday, String phone) {
        Person person = new PersonPojo();
        person.setSurname(surname);
        person.setFirstName(name);
        person.setSecondName(secondName);
        person.setAge(25);//Remove this!!! And remove property from the Pojo! To write calculating method!
        person.setPhone(phone);
        person.setBirthday(birthday);

        User user = new UserPojo();
        user.setLogin(login);
        user.setPassword(password);
        user.setPerson(person);
        return user;
    }

    @Override
    public void handleResponse(String response) {
        try {
            loggedUser = new Gson().fromJson(response, UserPojo.class);
        } catch (JsonSyntaxException e){
            ResponseError error = new Gson().fromJson(response, ResponseError.class);
            registerError(error.getErrorText());
        }
    }

    @Override
    public void onPostExecute() {
        RegistrationActivity activity = parent.get();
        if (activity != null) {
            if (errorMsg != null) {
                activity.notify(errorMsg);
            } else {
                if (loggedUser != null) {
                    //FIXME: надо что-то сделать с транзакциями единого DAO. Наверное, написать получение, закрытие и их стэк.
                    //Transaction trn = null;
                    try {
                        //trn = DAO.beginTransaction();
                        DAO.deleteAll(User.class);
                        DAO.save(loggedUser);
                        //trn.commit;
                        activity.logIn(loggedUser);
                    } catch (SQLException e) {
                        activity.notify("Problem with saving to DAO!");
                    } finally {
                        /*
                        if (trn != null) {
                            trn.close();
                        }
                        */
                    }
                } else {
                    activity.notify("ololo!");
                }
            }
        }
    }

    @Override
    public String getURL() {
        return "ServicesTomcatWAR/register";
    }

    @Override
    public void registerError(String error) {
        this.errorMsg = error;
    }

}
