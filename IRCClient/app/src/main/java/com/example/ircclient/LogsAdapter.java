package com.example.ircclient;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//public class LogsAdapter<String> extends ArrayAdapter<String> {
public class LogsAdapter extends ArrayAdapter<String> {
    public LogsAdapter(Context context, List<String> items) {
        super(context, 0, items);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = getItem(position);
        String str = "";
        Boolean fromUser = false, received=false;


        String time="";
        item = StringEscapeUtils.unescapeHtml4(item);
        // get time from user message
        if(item.contains("<<time>>")){
            Pattern pattern = Pattern.compile("``(.*?)``");
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                time = matcher.group(1);
                time = time.replace("<<time>>", "");
                item = item.replaceAll("``(.*?)``", "").replaceAll("`", "");
            }
            Log.i("TIME", "getView: " + time);
            Log.i("TIME2", "getView: " + item);
        }

        // if message is from sender
        if(item.contains("fromuser")){
            // change html to text
            str =   item.replaceAll("\\<.*?\\>", "").replaceAll("&lt;","").replaceAll("&gt;","").replaceAll("&#31;","").replaceAll("&nbsp;","").replaceAll("&#1;","");
            str = str.replaceAll("fromuser", "");
            fromUser = true;
        }
        // if message is from receiver
        if(item.contains("received") && !item.contains("Highest connection count:")){
            // change html to text
            str =   item.replaceAll("\\<.*?\\>", "").replaceAll("&lt;","").replaceAll("&gt;","").replaceAll("&#31;","").replaceAll("&nbsp;","").replaceAll("&#1;","");
            str = str.replaceAll("received", "");
            // get time from receiver
            Pattern pattern = Pattern.compile("``(.*?)``");
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                time = matcher.group(1);
                time = time.replaceAll("time", "");
                str = str.replaceAll("``(.*?)``", "").replaceAll("`", "");
            }

            received = true;
        }

        // inflate layout based on sender, receiver and server message
        if (fromUser) {
                     convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            } else if(received){
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_message_received, parent, false);
             }else {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_line, parent, false);
            }



        // Set view for
        TextView line = (TextView) convertView.findViewById(R.id.text_message_body);
        TextView message_sent = (TextView) convertView.findViewById(R.id.text_message_body);
        TextView message_sent_time = (TextView) convertView.findViewById(R.id.text_message_time_sent);
        TextView message_received = (TextView) convertView.findViewById(R.id.text_message_body);
        TextView message_received_time = (TextView) convertView.findViewById(R.id.text_message_time_received);


        // Populate markup data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.i("ỬOD", "getView: "+ str);

            Log.i("HTML", "getView: " + (java.lang.String) item);
            if(fromUser) {
                message_sent.setText(str);
                message_sent_time.setText(time);
            }else if(received){
                message_received.setText(str);
                message_received_time.setText(time);
            }else{  // message from server
                line.setText(Html.fromHtml((java.lang.String) item, Html.FROM_HTML_MODE_COMPACT));
            }

            Log.i("HTML", "getView: After removing HTML Tags: " + str);
        }
        else {
            Log.i("ỬOD", "getView: "+ str);
            if(fromUser) {
                message_sent.setText(str);
                message_sent_time.setText(time);

            }else if(received){
                message_received.setText(str);
                message_received_time.setText(time);

            }else{ // message from server
                line.setText(Html.fromHtml((java.lang.String) item));
            }
        }


        // Return the completed view to render on screen
        return convertView;
    }

}
