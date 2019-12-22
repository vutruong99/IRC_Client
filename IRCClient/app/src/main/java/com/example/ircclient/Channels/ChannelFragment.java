package com.example.ircclient.Channels;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ircclient.MessageFragment;
import com.example.ircclient.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {

    ArrayList<Channel> channelList = new ArrayList<>();
    ChannelListAdapter adapter;
    ListView channelListView;

    String channel;
    String nick;


    public ChannelFragment() {
        // Required empty public constructor
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
        channelList = new ArrayList<>();
        // When we call the Channel fragment from the drawer, it crashes because there is no bundle,
        // If I set bundle != null then it will show a blank list
        Bundle bundle = getArguments();
        if (bundle != null) {
            nick = bundle.getString("nick");
            channel = bundle.getString("channel");
        }

        channelListView = getView().findViewById(R.id.channelListView);

        channelList.add(new Channel(channel, "0 people"));

        adapter = new ChannelListAdapter(getActivity(), R.layout.adapter_view_channel_layout, channelList);
        channelListView.setAdapter(adapter);

        channelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Channel item = adapter.getItem(position);
                Bundle messageBundle = new Bundle();
                messageBundle.putString("nick",nick);
                messageBundle.putString("channel",item.getChannelName());
                MessageFragment messageFragment = new MessageFragment();
                messageFragment.setArguments(messageBundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_for_fragments, messageFragment, "MessageF").commit();
                /*Intent intent = new Intent(getContext(),SingleChannelActivity.class);
                //based on item add info to intent
                intent.putExtra("nick",nick);
                intent.putExtra("channel",item.getChannelName());
                startActivity(intent);
                Toast.makeText(getContext(),"LMAO",Toast.LENGTH_LONG).show();*/
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
}
