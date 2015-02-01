package com.urban.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tools.contactview.ContactView;
import com.urban.data.Contact;
import com.urban.data.Organization;
import com.urban.observer.R;

import java.util.Collection;
import java.util.Iterator;

public class InfoFragment extends OrganizationTabFragment {

    private static final int LAYOUT_ID = R.layout.organization_info;

    public static InfoFragment newInstance(Organization organization) {
        InfoFragment fragment = new InfoFragment();
        fragment.organization = organization;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT_ID, container, false);
        fillPositionInfo(view);
        fillContacts(view);
        return view;
    }

    private void fillPositionInfo(View view) {
        TextView text = (TextView) view.findViewById(R.id.info_text);
        text.setText(organization.getDescription());
    }

    private void fillContacts(View view) {
        Collection<Contact> contacts = organization.getContacts();
        Iterator<Contact> iterator = contacts.iterator();

        LinearLayout contactsLayout = (LinearLayout) view.findViewById(R.id.contacts_layout);

        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            ContactView contactView = ContactView.getInstance(contact, getActivity().getApplicationContext());
            if (contactView != null) {
                contactsLayout.addView(contactView);
            }
        }
    }
}