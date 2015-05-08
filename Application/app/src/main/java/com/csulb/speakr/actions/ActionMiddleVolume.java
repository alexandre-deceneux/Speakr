package com.csulb.speakr.actions;
import android.content.Context;
import android.media.AudioManager;

import com.csulb.speakr.Actions;

/**
 * Created by Alex on 07/05/2015.
 */
public class ActionMiddleVolume implements Actions.UserActionListener {

    public final static String TAG = "ActionMiddleVolume";

    @Override
    public boolean doAction(Context context, String[] args) {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2, 0);
        return true;
    }

}
