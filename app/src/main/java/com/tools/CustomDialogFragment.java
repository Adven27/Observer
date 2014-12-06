package com.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.test.R;

/**
 * Created by MetallFoX on 06.12.2014.
 */
public class CustomDialogFragment extends DialogFragment {

    public interface DialogListener {
        public void onPositive();
        public void onNegative();
    }

    private DialogListener listener;
    private String title;
    private String text;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog, null);
        TextView textView = (TextView)view.findViewById(R.id.dialog_text);
        textView.setText(text);
        builder.setView(view)
                .setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onPositive();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onNegative();
                        CustomDialogFragment.this.getDialog().cancel();
                    }
                })
                .setTitle(title);
        return builder.create();
    }

    public static CustomDialogFragment getInstance(DialogListener listener, String title, String text) {
        CustomDialogFragment instance = new CustomDialogFragment();
        instance.listener = listener;
        instance.title = title;
        instance.text = text;
        return instance;
    }
}
