package com.example.applicationcontext;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

public class ApplicationExample extends Application {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Welcome back boiz",Toast.LENGTH_LONG).show();
        Log.d("Debug Application", "LMAO BOIZ");

    }

}
