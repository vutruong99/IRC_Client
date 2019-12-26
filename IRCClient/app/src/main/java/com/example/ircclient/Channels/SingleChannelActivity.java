package com.example.ircclient.Channels;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ircclient.Connection;
import com.example.ircclient.LogsAdapter;
import com.example.ircclient.Message.MessageAdapter;
import com.example.ircclient.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SingleChannelActivity extends AppCompatActivity {

    Connection connection;
    MessageAdapter adapter;
    String strTime;
    Button send;
    private ArrayAdapter<String> adapter2;
    private ListView logs;
    private List<String> items =new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        String channel = intent.getStringExtra("channel");
        String nick = intent.getStringExtra("nick");


        new ConnectTask().execute("irc.freenode.net", "6667", nick, channel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_channel);

        Intent intent = getIntent();

        logs = (ListView) findViewById(R.id.message_container);
//        adapter2 = new LogsAdapter<>(getActivity(),items);
        adapter2 = new LogsAdapter(getApplicationContext(),items );


        logs.setAdapter(adapter2);

        // Set to scroll to bottom
        logs.setStackFromBottom(true);
        logs.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);


        TextView channelName = findViewById(R.id.channelName);
        channelName.setText(intent.getStringExtra("channel"));


        String channel = intent.getStringExtra("channel");
        String nick = intent.getStringExtra("nick");

        new ConnectTask().execute("irc.freenode.net", "6667", nick, channel);


        send = (Button) findViewById(R.id.button_chatbox_send); //The send button
        send.setEnabled(false);
        final EditText chatBox = (EditText) findViewById(R.id.edittext_chatbox); //The chat box

        send.setOnClickListener(new View.OnClickListener() { //When the button is clicked
            @Override
            public void onClick(View v) {

                //Get time and convert to string
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                strTime = dateFormat.format(currentTime);

                parseIn(chatBox.getText().toString() , intent.getStringExtra("channel"));
                chatBox.getText().clear();

            }
        });

    }

    private void parseIn(String message, String channel) {
        String tmp[];

        message = message.trim();
        if (message.isEmpty())
            return;

        message = message.replaceAll("\\s+", " ");

        // If normal message, send to channel
        if (message.charAt(0) != '/') {
            privmsg(channel, message);
            // Or if message starts with /msg...
        } else if (message.startsWith("/msg")) {
            tmp = message.split(" ", 3);
            if (tmp.length >= 3)
                privmsg(tmp[1], tmp[2]);
            // if message starts with /join
        } else if (message.startsWith("/join")) {
            tmp = message.split(" ", 3);
            join(tmp[1]);
        } else if (message.startsWith("/list")) {
            connection.send("LIST " + channel );
        } else if (message.startsWith("/name")) {
            connection.send("NAMES " + channel);

        } else if (message.startsWith("/part")) {
            tmp = message.split(" ", 2);

        } else {
            log(message);
        }
    }

    private void privmsg(String channel, String message) {
        Date currentTime_ = Calendar.getInstance().getTime();
        DateFormat dateFormat_ = new SimpleDateFormat("hh:mm");
        String strTime_ = dateFormat_.format(currentTime_);
        connection.send("PRIVMSG " + channel + " :" + message);
//        "``<<time>>"+ strTime_ + "``"

        //Format the thing
//        log(String.format("<font color=\"#FF4500\">%s</font>: " +
//                "<font color=\"#FF4500\">&lt;</font>" +
//                "%s<font color=\"#FF4500\">&gt;</font> " +
//                "%s", channel, connection.nick, message));

        log("fromuser " + channel + ": " + connection.nick + " " + message + "``<<time>>" + strTime_ + "``");
    }

    public void join(String channel) {
        if (!channel.isEmpty()) {
            connection.send("JOIN " + channel);

            ChannelFragment channelFragment = new ChannelFragment();
            channelFragment.addChannel(channel);
        }

        else {
            log("Unknown command");
        }
    }

    private void parseSrv(String message) {
        String user = connection.host;
        String command = message;
        String param;
        String text;
        String tmp[];
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");


        if (command.isEmpty())

            return;

        if (command.charAt(0) == ':') {
            tmp = command.substring(1).split(" ", 2);
            if (tmp.length < 2)
                return;
            user = tmp[0];
            command = tmp[1];
            user = user.split("!", 2)[0];
        }


        tmp = command.split(" ", 2);
        command = tmp[0];
        param = tmp.length == 2 ? tmp[1]: "";

        tmp = param.split(":", 2);
        param = tmp.length >= 1 ? tmp[0].trim() : "";
        text = tmp.length == 2 ? tmp[1] : "";

        text = Html.escapeHtml(text);
        if (command.equals("PONG") || command.equals("MODE"))
            return;
        if (command.equals("353") || command.equals("366"))
            return;
        if (command.equals("PING")) {}

        else if (command.equals("PRIVMSG")) {
//            log(String.format("<font color=\"#A500A5\">%s</font>: " +
//                    "<font color=\"#009c00\">&lt;</font>" +
//                    "%s<font color=\"#009c00\">&gt;</font> " +
//                    "%s", param, user, text));
            strTime = dateFormat.format(currentTime);
            Log.i("Converted", "parseSrv: "+ text);
            log(param + user + text+ "``<<time>>"+ strTime + "``" + "received" );
        }
        else if (command.equals("JOIN")) {
            param = !param.isEmpty() ? param : text;
            if (user.equals(connection.nick)) {
                send.setEnabled(true);
                log("You have joined channel " + param);
                log("You have joined channel " + param );
                //this.channel = param;
                adapter2.add(param);
                adapter2.notifyDataSetChanged();
                //((Spinner) findViewById(R.id.channel))
                //       .setSelection(adapter2.getPosition(this.channel));
            } else {
                log(user + " has joined channel <strong>" +
                        "<font color=\"#A500A5\">" + param + "</font></strong>");
            }
        } else if (command.equals("NICK"))
            if (user.equals(connection.nick)) {
                log("Your nick name is now " + text);
                //((EditText) findViewById(R.id.message)).setHint(text);
                connection.nick = text;
            } else {
                log(user + " is now known as " + text);
            }
        else if (command.equals("NOTICE"))
            log(String.format("<font color=\"#009c9c\">-</font>" +
                    "<font color=\"#FF4500\">%s</font>" +
                    "<font color=\"#FF4500\">-</font> " +
                    "%s", user, text));
        else if (command.equals("PART"))
            if (user.equals(connection.nick)) {
                log("You have left channel " + param);
                adapter2.remove(param);
                adapter2.notifyDataSetChanged();
            } else {
                log(user + " has left channel " + param);
            }
        else if (command.equals("LIST")) {
            log(param + text);
        }
        else if (command.equals("QUIT"))
            ;
        else if (command.equals("TOPIC"))
            log("Topic for channel <font color=\"#009c9c\">" + param +
                    "</font> is <font color=\"#009c9c\">" + text + "</font>");
        else if (command.equals("332"))
            log("Topic for channel <font color=\"#009c9c\">" +
                    param.split(" ", 2)[1] +
                    "</font> is <font color=\"#009c9c\">" + text + "</font>");
        else if (!text.isEmpty())
            log("<font color=\"#555555\">* " + text + "</font>");
    }

    private void log(String message) {
        adapter2.add(message);
        adapter2.notifyDataSetChanged();
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
                connection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            parseSrv(values[0]);
        }
    }
}
