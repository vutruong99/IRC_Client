package com.example.applicationexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = findViewById(R.id.layout);
        TextView textViewAppContext = new TextView(getApplicationContext());
        textViewAppContext.setText("LMAO");

        TextView textViewActivity = new TextView(this);
        textViewActivity.setText("Activity Context");

        layout.addView

    }
}
