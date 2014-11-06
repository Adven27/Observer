package com.urban.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.urban.data.Contact;
import com.urban.data.Position;

import java.util.Collection;
import java.util.Iterator;

public class ContactsFragment extends PositionTabFragment {

    private static final int LAYOUT_ID = R.layout.position_contacts;

    /**
     * newInstance constructor for creating fragment with arguments
     *
     * @param position
     */
    public static ContactsFragment newInstance(Position position) {
        ContactsFragment fragment = new ContactsFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);

        Collection<Contact> contacts = position.getOrganization().getContacts();

        Iterator<Contact> iterator = contacts.iterator();
        TextView text;
        if (iterator.hasNext()) {
            text = (TextView) view.findViewById(R.id.first);
            text.setText(iterator.next().getContact());
        }
        if (iterator.hasNext()) {
            text = (TextView) view.findViewById(R.id.second);
            text.setText(iterator.next().getContact());
        }
        if (iterator.hasNext()) {
            text = (TextView) view.findViewById(R.id.third);
            text.setText(iterator.next().getContact());
        }

        return view;
    }
}
