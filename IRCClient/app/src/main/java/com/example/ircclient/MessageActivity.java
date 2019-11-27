package com.example.ircclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Calendar;


public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        TextView greetUser = (TextView) findViewById(R.id.greetUser); //Great user
        Intent myIntent = getIntent();
        String nick = myIntent.getStringExtra("nick");

        greetUser.setText("Hello " + nick + " !");

        ListView listView = (ListView) findViewById(R.id.message_container); //Get list view

        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL); //Make list view scrolls everytime we type
        listView.setStackFromBottom(true);

        final ArrayList<Message> list = new ArrayList<>();

        Button send = (Button) findViewById(R.id.button_chatbox_send); //The send button
        final EditText chatBox = (EditText) findViewById(R.id.edittext_chatbox); //The chat box

        final MessageAdapter adapter = new MessageAdapter(this, R.layout.item_message_sent, list);

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

    }
}
