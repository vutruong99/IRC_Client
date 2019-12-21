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

    public ChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_channel, container, false);

        ListView channelListView = v.findViewById(R.id.channelListView);

        Channel channel1 = new Channel("#hehe", "20 peoople");
        Channel channel2 = new Channel("#havefun", "35 peoople");
        Channel channel3 = new Channel("#ggibhc", "67 peoople");
        Channel channel4 = new Channel("#t3oasd", "43 peoople");
        Channel channel5 = new Channel("#osdj", "0 peoople");

        ArrayList<Channel> channelList = new ArrayList<>();
        channelList.add(channel1);
        channelList.add(channel2);
        channelList.add(channel3);
        channelList.add(channel4);
        channelList.add(channel5);

        adapter = new ChannelListAdapter(getActivity(), R.layout.adapter_view_channel_layout, channelList);
        channelListView.setAdapter(adapter);

        channelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Channel item = adapter.getItem(position);

                Intent intent = new Intent(getContext(),SingleChannelActivity.class);
                //based on item add info to intent
                intent.putExtra("name", item.getChannelName());
                intent.putExtra("channel", item.getChannelName());
                startActivity(intent);
                Toast.makeText(getContext(),"LMAO",Toast.LENGTH_LONG).show();
            }
        });


        return v;
    }

}
