package com.csulb.speakr.actions;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.csulb.speakr.Actions;

/**
 * Created by Alex on 07/05/2015.
 */
public class ActionPlanning implements Actions.UserActionListener {

    public final static String TAG = "ActionPlanning";

    @Override
    public boolean doAction(Context context) {
        ComponentName cn;
        Intent i = new Intent();
        cn = new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity");
        i.setComponent(cn);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

}
