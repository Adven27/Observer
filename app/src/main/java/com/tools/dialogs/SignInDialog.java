package com.tools.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tools.PrototypeView;
import com.urban.activity.registration.RegistrationActivity;
import com.urban.activity.task.SignInTask;
import com.urban.appl.Settings;
import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.observer.R;
import com.urban.validation.ValidationHelper;

import java.sql.SQLException;

/**
 * Created by MetallFoX on 06.12.2014.
 */
public class SignInDialog extends SimpleDialog {

    EditText login;
    EditText password;
    TextView error;

    private static final View.OnClickListener REGISTER_CLICK_LISTENER = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PrototypeView.getActivity(), RegistrationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PrototypeView.getActivity().startActivity(intent);
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.sign_in_dialog, null);

        TextView registerBtn = (TextView)view.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(REGISTER_CLICK_LISTENER);

        login = (EditText)view.findViewById(R.id.loginInput);
        password = (EditText)view.findViewById(R.id.passwordInput);
        error = (TextView)view.findViewById(R.id.signInError);

        builder.setView(view)
                .setPositiveButton(R.string.dialog_enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // This button click is overrides in onStart()
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SignInDialog.this.getDialog().cancel();
                    }
                })
                .setTitle("Вход в систему");
        return builder.create();
    }

    public static SignInDialog getInstance(DialogListener listener) {
        SignInDialog instance = new SignInDialog();
        instance.setListener(listener);
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null) {
            Button positiveButton = (Button)dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }
    }

    /**
     * SignIn button click
     */
    private void signIn() {
        if (validateInput()) {
            SignInTask task = new SignInTask(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, login.getText().toString(), password.getText().toString());
            } else {
                task.execute(login.getText().toString(), password.getText().toString());
            }
        } else {
            setError("Заполните все поля!");
        }
    }

    public void signIn(User user) {
        if (user != null) {
            //FIXME: надо что-то сделать с транзакциями единого DAO. Наверное, написать получение, закрытие и их стэк.
            //Transaction trn = null;
            try {
                //trn = DAO.beginTransaction();
                DAO.deleteAll(User.class);
                DAO.save(user);
                //trn.commit;
                //dialog.logIn(loggedUser);
                Settings.setLoggedUser(user);
                listener.onPositive();
                dismiss();
            } catch (SQLException e) {
                notify("Проблема с входом!");
            } finally {
                /*
                if (trn != null) {
                    trn.close();
                }
                */
            }
        } else {
            notify("Пользователя с указанной парой логин/пароль не существует!");
        }
    }

    private boolean validateInput() {
        return !(ValidationHelper.isEmpty(login.getText().toString())
                || ValidationHelper.isEmpty(password.getText().toString()));
    }

    public void notify(String msg) {
        setError(msg);
    }

    private void setError(String errorMsg) {
        error.setText(errorMsg);
    }
}
