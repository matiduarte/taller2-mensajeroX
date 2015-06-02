package com.dk.mensajero.Adapters;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dk.mensajero.Conversations.ConversationDataProvider;
import com.dk.mensajero.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matias on 05/05/15.
 */
public class ChatAdapter extends ArrayAdapter<ConversationDataProvider>{

    private List<ConversationDataProvider> chatList = new ArrayList<ConversationDataProvider>();
    private TextView chatTxt;
    private Context ctx;

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
        ctx = context;
    }

    @Override
    public void add(ConversationDataProvider object) {
        List<ConversationDataProvider> chatListCurrent = new ArrayList<ConversationDataProvider>(this.chatList);

        for (int i = 0; i < chatList.size(); i++) {
            //Si no tiene messageId fue porque se agrego antes de mandar al servidor. Tengo que sacarlo para poner el que viene del server
            if(chatList.get(i).messageId.equals("")) {
                chatListCurrent.remove(i);
            }

            //Si ya esta agregado no lo vuelvo a agregar
            if(chatList.get(i).messageId.equals(object.messageId)) {
                return;
            }
        }

        chatList = chatListCurrent;
        chatList.add(object);
        super.add(object);

    }

    @Override
    public int getCount() {
        return this.chatList.size();
    }

    @Override
    public ConversationDataProvider getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.single_message_layout, parent, false);
        }
        this.chatTxt = (TextView) convertView.findViewById(R.id.singleMessage);
        String Message;
        boolean POSITION;
        ConversationDataProvider provider = getItem(position);
        Message = provider.message;
        POSITION = provider.position;
        chatTxt.setText(Message);
        chatTxt.setBackgroundResource(POSITION ? R.drawable.left : R.drawable.right);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(!POSITION)
        {
            params.gravity = Gravity.RIGHT;
        } else
        {
            params.gravity = Gravity.LEFT;
        }

        chatTxt.setLayoutParams(params);

        return convertView;
    }
}
