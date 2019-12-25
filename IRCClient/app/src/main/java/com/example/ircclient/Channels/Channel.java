package com.example.ircclient.Channels;

import android.os.Parcel;
import android.os.Parcelable;

public class Channel implements Parcelable {
    private String channelName;
    private String channelDescription;

    public Channel(String channelName, String channelDescription) {
        this.channelName = channelName;
        this.channelDescription = channelDescription;
    }

    protected Channel(Parcel in) {
        channelName = in.readString();
        channelDescription = in.readString();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelName);
        dest.writeString(channelDescription);
    }
}
