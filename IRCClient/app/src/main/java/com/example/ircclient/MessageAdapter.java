package com.example.ircclient;

import android.content.Context;
import android.support.v7.app.AlertController;
import android.support.v7.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<BaseMessage> mMessageList;

    public MessageAdapter(Context context, List<BaseMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }
}
