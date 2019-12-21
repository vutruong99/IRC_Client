package com.example.ircclient.Channels;

public class Channel {
    private String channelName;
    private String channelDescription;

    public Channel(String channelName, String channelDescription) {
        this.channelName = channelName;
        this.channelDescription = channelDescription;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }
}
