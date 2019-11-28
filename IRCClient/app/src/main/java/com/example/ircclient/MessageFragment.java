package com.example.ircclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_message, container, false);

        ListView listView = (ListView) v.findViewById(R.id.message_container); //Get list view

        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL); //Make list view scrolls everytime we type
        listView.setStackFromBottom(true);

        final ArrayList<Message> list = new ArrayList<>();

        Button send = (Button) v.findViewById(R.id.button_chatbox_send); //The send button
        final EditText chatBox = (EditText) v.findViewById(R.id.edittext_chatbox); //The chat box

        final MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.item_message_sent, list);

        send.setOnClickListener(new View.OnClickListener() { //When the button is clicked
            @Override
            public void onClick(View v) {

                //Get time and convert to string
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String strTime = dateFormat.format(currentTime);

                //Get message
                String getMessage = chatBox.getText().toString();

                //Create new message in Message class
                Message message = new Message(getMessage,strTime);

                //Clear editText
                chatBox.getText().clear();
                list.add(message);

                //Refreshes the view immediately
                adapter.notifyDataSetChanged();


            }
        });

        //Set the view to the adapter
        listView.setAdapter(adapter);

        return v;
    }

}
