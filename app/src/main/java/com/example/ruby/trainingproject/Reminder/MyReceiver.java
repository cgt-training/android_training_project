package com.example.ruby.trainingproject.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ruby on 26/6/18.
 */

public class MyReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        if(extras != null){
            Log.d("Tag","is string empty "+extras.getString("k1"));
        }

        Intent i=new Intent(context, RemainderMessage.class);
        i.putExtras(extras);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
