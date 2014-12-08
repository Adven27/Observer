package com.tools.contactview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tools.CustomDialogFragment;
import com.urban.data.Contact;

/**
 * Created by MetallFoX on 08.12.2014.
 */
public abstract class ContactView extends TextView implements CustomDialogFragment.DialogListener {
    public ContactView(Context context) {
        super(context);
    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static ContactView getInstance(Contact contact, Context context) {
        ContactView contactView = null;
        switch (contact.getContactType().getType()) {
            case PHONE :
                contactView = new PhoneView(context);
                break;
            case EMAIL :
                contactView = new EmailView(context);
                break;
            case WEB :
                contactView = new WebsiteView(context);
                break;
            default :
                break;
        }
        if (contactView != null) {
            contactView.setText(contact.getContact());
        }
        return contactView;
    }

    /*private static  <T extends ContactView> T createInstance(Class<T> clazz, Contact contact) {
        T contactView =
        contactView.setText(contact.getContact());
        return contactView;
    }*/

}
