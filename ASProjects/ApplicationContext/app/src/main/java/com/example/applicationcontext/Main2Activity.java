package com.example.applicationcontext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ApplicationExample appExample = (ApplicationExample) getApplicationContext();
        appExample.getAge();
        appExample.getName();

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(appExample.getName());
    }
}
