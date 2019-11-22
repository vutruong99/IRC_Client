package com.example.ircclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etNick = (EditText) findViewById(R.id.etNick);
        EditText etChannel = (EditText) findViewById(R.id.etChannel);

        Button startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = etNick.getText().toString();
                Intent intent = new Intent(MainActivity.this,MessageActivity.class);
                intent.putExtra("nick",nick);
                startActivity(intent);
            }
        });
    }
}
