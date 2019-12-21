package com.example.ircclient;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Connection {

    public String host = "irc.freenode.net";
    int port = 6667;
    public String nick;
    public String channel;
    String realname;
    String username;

    BufferedReader in;
    PrintWriter out;

    MessageCallback listener;
    boolean running;

    public Connection(String host, int port, String nick, String channel,
                      MessageCallback listener) {
        this.host = host;
        this.port = port;
        this.nick = nick;
        this.channel = channel;
        this.listener = listener;

        running = false;
    }

    public void start() throws IOException {
        running = true;

        Socket socket = new Socket(host, port);
        try {
            out = new PrintWriter(new BufferedWriter
                    (new OutputStreamWriter
                            (socket.getOutputStream())),
                    true);
            in = new BufferedReader(new InputStreamReader
                    (socket.getInputStream()));

            send("NICK " + nick);
            send("USER " + username + " 0 * :" + realname);
            send("JOIN " + channel);

            while (running) {
                String message = in.readLine();
                if (message != null)
                    listener.rcv(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            in.close();
            socket.close();
        }
    }

    public void stop() {
        running = false;
    }

    public void send(String message) {
        out.println(message);
    }

    public interface MessageCallback {
        void rcv(String message);
    }
}
