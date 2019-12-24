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
import com.example.ircclient.R;

import java.io.IOException;
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
    Boolean[] firstTime= new Boolean[20];   // boolean of arrays set to true for each channel
    int firstTime_counter = 0;  // to get the index of the newly added channel in firstTime array
    SingleChannelFragement singleChannelFragement;
    int last_clicked_item_position; // get position id of the last item clicked in the listview
    Connection connection;


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
        outState.putInt("last_clicked_item_position", last_clicked_item_position);
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
            last_clicked_item_position =  savedInstanceState.getInt("last_clicked_item_position");
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
                    /***
                     * If user clicks a different channel from the one previously clicked
                     *  - close connection
                     *     if channel has been clicked previously
                     *         - create a new connection
                     *         - save instance of singleChannelFragment with connection
                     *  If user clicks channel for the first time
                     *      - create a new connection
                     *      - save instance of singleChannelFragment with connection
                     *  else if user just left that channel
                     *      - load instance of that channel
                     *
                     */
                    if(item != adapter.getItem(last_clicked_item_position) ){   // If user clicks a different channel from the one previously clicked
                        Log.i("item", "onItemClick: Stopping Previous connection");
                        connection.stop();
                        if(firstTime[position] == false){   //  if channel has been clicked previously
                            Bundle bundle = new Bundle();
                            bundle.putString("nick", nick);
                            bundle.putString("channel", item.getChannelName());
                            bundle.putString("port", randomPort());


                            singleChannelFragement = new SingleChannelFragement();
                            ConnectTask myTask = null;
                            myTask = new ConnectTask();
                            myTask.execute("irc.freenode.net","6667", nick, item.getChannelName());


                            singleChannelFragement.setArguments(bundle);

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.layout_for_fragments, singleChannelFragement, "Single Channel Fragment");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                    }
                    if(firstTime[position]){ // If user clicks channel for the first time
                      firstTime[position] = false;
//                    SingleChannelFragement singleChannelFragement;
                        Bundle bundle = new Bundle();
                        bundle.putString("nick", nick);
//                      bundle.putString("channel", channel);
                        bundle.putString("channel", item.getChannelName());
                        bundle.putString("port", randomPort());
                        Log.i("item", "onItemClick:item clicked for the first time"+ item.getChannelName());


                        singleChannelFragement = new SingleChannelFragement();
                        ConnectTask myTask = null;
                        myTask = new ConnectTask();
                        myTask.execute("irc.freenode.net","6667", nick, item.getChannelName());

//                      SingleChannelFragement singleChannelFragement = singleChannelFragement_list.get(position);

                      singleChannelFragement.setArguments(bundle);

                      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                      fragmentTransaction.replace(R.id.layout_for_fragments, singleChannelFragement, "Single Channel Fragment");
                      fragmentTransaction.addToBackStack(null);
                      fragmentTransaction.commit();
//
                  }else { //  else if user just left that channel
                      if( singleChannelFragement == null){
                          Log.i("newchannel", "onItemClick: SingleChannelFragment was null");
                      }else{
                          Log.i("newchannel", "onItemClick: SingleChannelFragment is not null");

                      }

                        Log.i("item", "onItemClick:item clicked has been clicked before"+ item.getChannelName());

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                      fragmentTransaction.replace(R.id.layout_for_fragments, singleChannelFragement, "Single Channel Fragment");
                      fragmentTransaction.addToBackStack(null);
                      fragmentTransaction.commit();
                  }
                    last_clicked_item_position = position; // store last_clicked_item_position


                    Toast.makeText(getContext(), "LMAO", Toast.LENGTH_LONG).show();
                }
            });

            Button addChannel = getView().findViewById(R.id.addChannel);
            addChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] arr={"#jsfd", "#oalfcn", "#ojnfw", "#ojkns"};
                    Random r=new Random();
                    int randomNumber=r. nextInt(arr. length);

                    String randChannel = arr[r.nextInt(arr.length)];
                    addChannel(randChannel);
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
                Log.i("connection", "doInBackground: Trying to connect with " + nick+ channel + "on port " + randomPort());

                connection.start();
                Log.i("Connection ", "doInBackground: Task done");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            singleChannelFragement.parseSrv(values[0], connection);
        }
    }

}
