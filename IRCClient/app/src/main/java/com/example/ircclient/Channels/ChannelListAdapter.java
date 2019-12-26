package com.example.ircclient.Channels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ircclient.R;

import java.util.ArrayList;

public class ChannelListAdapter extends ArrayAdapter<Channel> {
    private static final String TAG = "CLA";

    private Context mContext;
    int mResource;

    public ChannelListAdapter(@NonNull Context context, int resource, ArrayList<Channel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String channelName = getItem(position).getChannelName();
        String channelDescription = getItem(position).getChannelDescription();
        Channel channel = new Channel(channelName,channelDescription);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvChannelName = convertView.findViewById(R.id.channelName);
        TextView tvChannelDesc = convertView.findViewById(R.id.channelDescription);

        tvChannelName.setText(channelName);

        return convertView;
    }

    @Nullable
    @Override
    public Channel getItem(int position) {
        return super.getItem(position);
    }
}
