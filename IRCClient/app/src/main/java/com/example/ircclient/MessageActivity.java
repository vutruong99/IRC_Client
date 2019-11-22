package com.example.ircclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        TextView greetUser = (TextView) findViewById(R.id.greetUser);
        Intent myIntent = getIntent();
        String nick = myIntent.getStringExtra("nick");

        greetUser.setText("Hello " + nick + " !");


    }
}
