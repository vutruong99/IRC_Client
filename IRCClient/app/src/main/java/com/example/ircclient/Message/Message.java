package com.example.ircclient.Message;

public class Message {
    private String content;
    private String time;

    public String getContent() {
        return content;
    }

    public Message(String content, String time) {
        this.content = content;
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

