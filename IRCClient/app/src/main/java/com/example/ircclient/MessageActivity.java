package com.example.ircclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.ToggleButton;

import org.w3c.dom.Text;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Calendar;

public class MessageActivity extends AppCompatActivity {
    private RelativeLayout layout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        configureNavigationDrawer();
        configureToolbar();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        return super.onCreateOptionsMenu(menu);
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_more);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavigationDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        /*NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener((new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            //work with items in navigation view
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment f = null;
                int itemId = menuItem.getItemId();

                // on Item click
                if(f != null){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id., f);
                    transaction.commit();
                    drawerLayout.closeDrawer();
                    return true;
                }

                return false;
            }
        }));*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        switch (itemId){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return true;
    }
}
