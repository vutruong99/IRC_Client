package com.example.ircclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentTransaction;

import com.example.ircclient.Channels.ChannelFragment;
import com.example.ircclient.Message.MessageFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RelativeLayout layout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    ChannelFragment channelFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //Getting intent values (nick and channel
        Intent intent = getIntent();
        String nick = intent.getStringExtra("nick");
        String channel = intent.getStringExtra("channel");

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Drawer toolbar
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        //Navigation view for side bar navigation
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView name = (TextView) headerView.findViewById(R.id.username_navbar);
        name.setText(intent.getStringExtra("nick"));

        // Call MessageFragment by default
        MessageFragment messageFragment = new MessageFragment();

        // Start asynchronous task in fragment
        //messageFragment.startConnect();
//        if (savedInstanceState == null) {
            channelFragment = new ChannelFragment();

            // Create a bundle to pass arguments to the fragment
            Bundle bundle = new Bundle();
            bundle.putString("nick", nick);
            bundle.putString("channel", channel);
            //messageFragment.setArguments(bundle);

            channelFragment.setArguments(bundle);
            //Show the fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout_for_fragments, channelFragment, "Channel Fragment");
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
//        } else {
//            Log.i("Prev", "onCreate: Getting previous state");
//            channelFragment = (ChannelFragment) getSupportFragmentManager()
//                    .findFragmentByTag("Channel Fragment");
//        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
//
//        switch (itemId){
//            case R.id.action_settings:
//                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//                startActivity(intent);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.channels) {
//            ChannelFragment channelFragment = new ChannelFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout_for_fragments, channelFragment, "Channel Fragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


        else if (id == R.id.about) {
            AboutFragment aboutFragment = new AboutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout_for_fragments, aboutFragment, "About Fragment");
            fragmentTransaction.commit();
        }

        else if (id == R.id.logout) {
            this.finish();
            System.exit(0);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

}
