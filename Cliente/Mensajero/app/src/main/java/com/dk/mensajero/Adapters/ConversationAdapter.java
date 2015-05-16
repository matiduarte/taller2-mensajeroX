package com.dk.mensajero.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.mensajero.Entities.Conversation;
import com.dk.mensajero.R;

import java.util.ArrayList;


public class ConversationAdapter extends ArrayAdapter<Conversation> {

    private final Context context;
    private final  ArrayList<Conversation> conversations;

    public ConversationAdapter(Context context, int int_view, ArrayList<Conversation> conversations) {
        super(context, int_view, conversations);
        this.context = context;
        this.conversations = conversations;
    }

    @Override
    public int getCount() {
        return this.conversations.size();
    }

    @Override
    public Conversation getItem(int position) {
        return this.conversations.get(position);
    }

    @Override
    public long getItemId(int position){
        return this.conversations.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_conversation, parent, false);

        Conversation conversation = this.conversations.get(position);


        TextView lastMessage = (TextView) rowView.findViewById(R.id.conversation_last_message);
        String message = conversation.getLastMessage();
        lastMessage.setText(message);

        TextView contactNameField = (TextView) rowView.findViewById(R.id.contact_name);
        String contactName = conversation.getContactName();
        contactNameField.setText(contactName);


        return rowView;
    }
}