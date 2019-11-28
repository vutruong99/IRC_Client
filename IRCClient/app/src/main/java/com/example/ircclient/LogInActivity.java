package com.example.ircclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        final EditText etNick = (EditText) findViewById(R.id.etNick);
        EditText etChannel = (EditText) findViewById(R.id.etChannel);

        final Button startButton = (Button) findViewById(R.id.startButton);

        etNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }
        });

        etChannel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }
        });

        if (isEmpty(etNick) || isEmpty(etChannel)) {
            startButton.setEnabled(false);
        } else {
            startButton.setEnabled(true);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = etNick.getText().toString();
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
