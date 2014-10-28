package com.urban.fragments.pages;

import java.util.Collection;
import java.util.Iterator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.urban.data.Contact;
import com.urban.data.Position;

public class ContactsFragment extends Fragment {

    private static final int LAYOUT_ID = R.layout.position_contacts;

    Position position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);

        Collection<Contact> contacts = position.getOrganization().getContacts();

        Iterator<Contact> iterator = contacts.iterator();
        TextView text;
        if (iterator.hasNext()) {
            text = (TextView)view.findViewById(R.id.first);
            text.setText(iterator.next().getContact());
        }
        if (iterator.hasNext()) {
            text = (TextView)view.findViewById(R.id.second);
            text.setText(iterator.next().getContact());
        }
        if (iterator.hasNext()) {
            text = (TextView)view.findViewById(R.id.third);
            text.setText(iterator.next().getContact());
        }

        return view;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
