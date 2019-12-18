package com.example.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] cheeses = {
                "lmao",
                "hello",
                "gg"
        };
        ArrayAdapter<String> cheeseAdapter =
                new ArrayAdapter<String>(this,
                        R.layout.item_message_sent,
                        R.id.text_message_body,
                        cheeses
                );

        ListView cheeseList = new ListView(this);
        setContentView(cheeseList);
        cheeseList.setAdapter(cheeseAdapter);
    }
}
