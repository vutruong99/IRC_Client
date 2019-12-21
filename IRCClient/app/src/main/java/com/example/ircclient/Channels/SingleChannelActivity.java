package com.example.ircclient.Channels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ircclient.R;

public class SingleChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_channel);

        TextView name = findViewById(R.id.channelName);
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));

    }
}
