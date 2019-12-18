package com.example.ircclient;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        StrictMode.ThreadPolicy policy = new StrictMode
                .ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);

        final EditText etNick = (EditText) findViewById(R.id.etNick);
        final EditText etChannel = (EditText) findViewById(R.id.etChannel);

        final Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setEnabled(true);


            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nick = etNick.getText().toString();
                    String channel = etChannel.getText().toString();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    intent.putExtra("nick", nick);
                    intent.putExtra("channel", channel);

                    if (!nick.isEmpty() && !channel.isEmpty()) {
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Please fill the log in form", Toast.LENGTH_LONG).show();
                    }

                }
            });



    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
