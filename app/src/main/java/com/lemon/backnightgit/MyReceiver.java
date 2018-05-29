package com.lemon.backnightgit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lemon.backnightgit.ui.BaseWebActivity;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            Bundle extras = intent.getExtras();
            Intent intent1 = new Intent(context, BaseWebActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("linkUrl", extras.getString(JPushInterface.EXTRA_MESSAGE));
            intent.putExtra("title", extras.getString(JPushInterface.EXTRA_MESSAGE));
            context.startActivity(intent1);
        }
    }
}
