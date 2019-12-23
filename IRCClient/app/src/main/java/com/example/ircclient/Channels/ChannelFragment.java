package com.example.ircclient.Channels;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ircclient.Connection;
import com.example.ircclient.MessageFragment;
import com.example.ircclient.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {

    ArrayList<Channel> channelList = new ArrayList<>();
    ChannelListAdapter adapter;
    ListView channelListView;
    boolean firstime = true;
    String channel;
    String nick;
    Connection connection;


    public ChannelFragment() {
        // Required empty public constructor


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", (Serializable) channelList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            nick = bundle.getString("nick");
            channel = bundle.getString("channel");
        }
        channelList.add(new Channel(channel, "0 people"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_channel, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null){
            channelList = (ArrayList<Channel>) savedInstanceState.getSerializable("list");
        }


            // When we call the Channel fragment from the drawer, it crashes because there is no bundle,
            // If I set bundle != null then it will show a blank list
            Bundle bundle = getArguments();
            if (bundle != null) {
                nick = bundle.getString("nick");
                channel = bundle.getString("channel");
                new ConnectTask().execute("irc.freenode.net", "6667", nick, channel);
            }

            channelListView = getView().findViewById(R.id.channelListView);


            adapter = new ChannelListAdapter(getActivity(), R.layout.adapter_view_channel_layout, channelList);
            channelListView.setAdapter(adapter);

            channelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Channel item = adapter.getItem(position);

//                Intent intent = new Intent(getContext(),SingleChannelActivity.class);
//                //based on item add info to intent
//                intent.putExtra("nick",nick);
//                intent.putExtra("channel",item.getChannelName());
//                startActivity(intent);
                  SingleChannelFragement singleChannelFragement;
//                    if (firstime) {
                        singleChannelFragement = new SingleChannelFragement();
                        Bundle bundle = new Bundle();
                        bundle.putString("nick", nick);
                    assert item != null;
                    bundle.putString("channel", item.getChannelName());
                        bundle.putParcelable("connection",connection);
                        singleChannelFragement.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.layout_for_fragments, singleChannelFragement, "Single Channel Fragment");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

//                        firstime = false;
//                    }




                    Toast.makeText(getContext(), "LMAO", Toast.LENGTH_LONG).show();
                }
            });

            Button addChannel = getView().findViewById(R.id.addChannel);
            addChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addChannel("#hoduw");
                }
            });



    }

    public void addChannel(String channel) {
        channelList.add(new Channel(channel, "0 people"));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("onDestroyView t", "onDestroyView: ChannelFragmen");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy ", "onDestroy: ChannelFragment");

    }

    private class ConnectTask extends AsyncTask<String, String, Connection> {

        @Override
        protected Connection doInBackground(String... params) {
            connection = new Connection(params[0],
                    Integer.parseInt(params[1]),
                    params[2],
                    params[3]
                    ,
                    new Connection.MessageCallback() {
                        @Override
                        public void rcv(String message) {
                            publishProgress(message);
                        }
                    });

            try {
                Log.i("Connection", "doInBackground:  Task Starting");

                connection.start();
                Log.i("Connection ", "doInBackground: Task done");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }
    }



}
