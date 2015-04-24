package com.csulb.speakr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Démarre le service
 */
public class ServiceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, ActivationService.class);
        context.startService(startServiceIntent);
    }

}
