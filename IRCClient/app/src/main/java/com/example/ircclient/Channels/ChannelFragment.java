package com.example.ircclient.Channels;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ircclient.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {

    ChannelListAdapter adapter;
    ListView channelListView;
    ArrayList<Channel> channelList;

    public ChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_channel, container, false);

        // When we call the Channel fragment from the drawer, it crashes because there is no bundle,
        // If I set bundle != null then it will show a blank list
        Bundle bundle = getArguments();
        String nick = bundle.getString("nick");
        String channel = bundle.getString("channel");

        channelListView = v.findViewById(R.id.channelListView);

        Channel channel1 = new Channel("#hehe", "20 peoople");
        Channel channel2 = new Channel("#havefun", "35 peoople");
        Channel channel3 = new Channel("#ggibhc", "67 peoople");
        Channel channel4 = new Channel("#t3oasd", "43 peoople");
        Channel channel5 = new Channel("#osdj", "0 peoople");

        channelList = new ArrayList<>();
        channelList.add(new Channel(bundle.getString("channel"), "0 people"));

        adapter = new ChannelListAdapter(getActivity(), R.layout.adapter_view_channel_layout, channelList);
        channelListView.setAdapter(adapter);

        channelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Channel item = adapter.getItem(position);

                Intent intent = new Intent(getContext(),SingleChannelActivity.class);
                //based on item add info to intent
                intent.putExtra("nick",nick);
                intent.putExtra("channel",channel);
                startActivity(intent);
                Toast.makeText(getContext(),"LMAO",Toast.LENGTH_LONG).show();
            }
        });


        return v;
    }

    public void addChannel(String channel) {
        channelList.add(new Channel(channel, "0 people"));
    }

}
