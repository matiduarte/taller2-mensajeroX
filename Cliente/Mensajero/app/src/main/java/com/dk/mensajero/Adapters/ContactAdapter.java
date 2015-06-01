package com.dk.mensajero.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.R;
import com.dk.mensajero.Utilities.Utilities;

import java.util.ArrayList;


public class ContactAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final  ArrayList<User> contacts;

    public ContactAdapter(Context context, int int_view, ArrayList<User> contacts) {
        super(context, int_view, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return this.contacts.size();
    }

    @Override
    public User getItem(int position) {
        return this.contacts.get(position);
    }

    @Override
    public long getItemId(int position){
        return this.contacts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_contact, parent, false);

        User contact = this.contacts.get(position);

        TextView contactNameField = (TextView) rowView.findViewById(R.id.contact_name);
        String contactName = contact.getName();
        contactNameField.setText(contactName);

        if (!contact.getProfilePicture().equals("default")) {
            ImageView contactPictureField = (ImageView) rowView.findViewById(R.id.contact_image);
            Bitmap picture = Utilities.stringToBitmap(contact.getProfilePicture());
            contactPictureField.setImageBitmap(picture);
        }

        return rowView;
    }
}