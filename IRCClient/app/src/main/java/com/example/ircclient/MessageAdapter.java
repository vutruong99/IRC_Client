package com.example.ircclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;

public class MessageAdapter extends ArrayAdapter<Message> {

    private static class ViewHolder {
        TextView name;
        TextView home;
    }

    private Context mContext;
    int mResource;
    public MessageAdapter(Context context, int resource, ArrayList<Message> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String content = getItem(position).getContent();
        String time = getItem(position).getTime();
        Message ms = new Message(content,time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv1 = (TextView)convertView.findViewById(R.id.text_message_body);
//        TextView tv2 = (TextView)convertView.findViewById(R.id.text_message_time);

        tv1.setText(content);
//        tv2.setText(time);

        return convertView;
    }


}
