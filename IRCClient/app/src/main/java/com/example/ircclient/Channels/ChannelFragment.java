package com.example.ircclient.Channels;


import android.content.Intent;
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

import com.example.ircclient.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {

    ArrayList<Channel> channelList = new ArrayList<>();
//    ArrayList<SingleChannelFragement> singleChannelFragement_list = new ArrayList<>();
    ChannelListAdapter adapter;
    ListView channelListView;
    String channel;
    String nick;
    Boolean[] firstTime= new Boolean[20];
    int firstTime_counter = 0;
    SingleChannelFragement singleChannelFragement;


    public ChannelFragment() {
        // Required empty public constructor


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", (Serializable) channelList);
        outState.putSerializable("fistTime", (Serializable) firstTime);
//        outState.putSerializable("listF", (Serializable) singleChannelFragement_list);
        outState.putInt("firstTime_counter", firstTime_counter);
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
        firstTime[firstTime_counter] = true;
        firstTime_counter++;
//        SingleChannelFragement singleChannelFragement= new SingleChannelFragement();
//        singleChannelFragement_list.add(singleChannelFragement);

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
//            singleChannelFragement_list = (ArrayList<SingleChannelFragement>) savedInstanceState.getSerializable("listF");
            firstTime = (Boolean[]) savedInstanceState.getSerializable("firstTime");
            firstTime_counter =  savedInstanceState.getInt("firstTime_counter");
        }

        Log.i("counter", "onViewCreated: first_counter"+ firstTime_counter);

            // When we call the Channel fragment from the drawer, it crashes because there is no bundle,
            // If I set bundle != null then it will show a blank list
            Bundle bundle = getArguments();
            if (bundle != null) {
                nick = bundle.getString("nick");
                channel = bundle.getString("channel");
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
                    Log.i("First value is:", "onItemClick: "+firstTime[position]);
                    Log.i("position is:", "onViewCreated:" + position);

                    if(firstTime[position]){
                      firstTime[position] = false;
//                    SingleChannelFragement singleChannelFragement;

                    singleChannelFragement = new SingleChannelFragement();

//                      SingleChannelFragement singleChannelFragement = singleChannelFragement_list.get(position);
                      Bundle bundle = new Bundle();
                      bundle.putString("nick", nick);
//                      bundle.putString("channel", channel);
                      bundle.putString("channel", item.getChannelName());
                      bundle.putString("port", randomPort());
                      singleChannelFragement.setArguments(bundle);

                      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                      fragmentTransaction.replace(R.id.layout_for_fragments, singleChannelFragement, "Single Channel Fragment");
                      fragmentTransaction.addToBackStack(null);
                      fragmentTransaction.commit();
//
                  }else {
                      if( singleChannelFragement == null){
                          Log.i("newchannel", "onItemClick: SingleChannelFragment was null");
                      }else{
                          Log.i("newchannel", "onItemClick: SingleChannelFragment is not null");

                      }

                      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                      fragmentTransaction.replace(R.id.layout_for_fragments, singleChannelFragement, "Single Channel Fragment");
                      fragmentTransaction.addToBackStack(null);
                      fragmentTransaction.commit();
                  }


//                    firstTime_array[position] = false;
//                    firstTime = new ArrayList<Boolean>(Arrays.asList(firstTime_array));

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
        firstTime[firstTime_counter] = true;
        firstTime_counter++;
//        SingleChannelFragement singleChannelFragement= new SingleChannelFragement();
//        singleChannelFragement_list.add(singleChannelFragement);
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

    public String randomPort(){
        String[] arr={"6665", "6666", "6667", "8000"};
        Random r=new Random();
        int randomNumber=r. nextInt(arr. length);

//        new ConnectTask().execute("irc.freenode.net",], nick, channel);
        String port = arr[r.nextInt(arr.length)];

        return port;
    }

}
