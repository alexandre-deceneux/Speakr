package com.csulb.speakr;

import android.content.Context;
import android.media.AudioManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 20/03/2015.
 */
public class Actions {

    Map<String, UserActionListener> mActionsList;
    Context mContext;

    public Actions(Context context){
        mContext = context;
        mActionsList = new HashMap<>();
        String[] actions = context.getResources().getStringArray(R.array.userActions);
        mActionsList.put(actions[0], new UserActionListener() {
            @Override
            public boolean doAction(Context context) {
                AudioManager audioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                return true;
            }
        });
        mActionsList.put(actions[1], new UserActionListener() {
            @Override
            public boolean doAction(Context context) {
                AudioManager audioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                return true;
            }
        });

    }

    public  boolean executeAction(String action){
        if (mActionsList.containsKey(action))
            return mActionsList.get(action).doAction(mContext);
        return false;
    }


    private abstract class UserActionListener{
        public abstract boolean doAction(Context context);
    }

}
