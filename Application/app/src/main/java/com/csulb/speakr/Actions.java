package com.csulb.speakr;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.csulb.speakr.actionlistdata.ActionListSingleton;
import com.csulb.speakr.actionlistdata.GsonAction;
import com.csulb.speakr.actionlistdata.GsonActionList;
import com.csulb.speakr.actions.ActionCall;
import com.csulb.speakr.actions.ActionMaximumVolume;
import com.csulb.speakr.actions.ActionMiddleVolume;
import com.csulb.speakr.actions.ActionMinimumVolume;
import com.csulb.speakr.actions.ActionPlanning;
import com.csulb.speakr.actions.ActionTakePicture;
import com.csulb.speakr.actions.ActionTakeScreenshot;
import com.csulb.speakr.actions.ActionTakeVideo;
import com.google.gson.Gson;

/**
 * Created by Alex on 20/03/2015.
 */
public class Actions {

    private final static String TAG = "Actions";

    Map<String, UserActionListener> mActionsList;
    Context mContext;

    public Actions(Context context){
        mContext = context;
        mActionsList = new HashMap<>();
        GsonAction [] actions = ActionListSingleton.getInstance().getData().getList();
        mActionsList.put(String.valueOf(actions[0].getId()), new ActionCall());
        mActionsList.put(String.valueOf(actions[7].getId()), new ActionTakePicture());
        mActionsList.put(String.valueOf(actions[8].getId()), new ActionTakeScreenshot());
        mActionsList.put(String.valueOf(actions[9].getId()), new ActionTakeVideo());
        mActionsList.put(String.valueOf(actions[10].getId()), new ActionPlanning());
        mActionsList.put(String.valueOf(actions[13].getId()), new ActionMaximumVolume());
        mActionsList.put(String.valueOf(actions[14].getId()), new ActionMiddleVolume());
        mActionsList.put(String.valueOf(actions[15].getId()), new ActionMinimumVolume());
    }

    public  boolean executeAction(GsonAction action, String[] args){
        if (action == null)
            Log.d(TAG, "Action null");
        else
            Log.d(TAG, "Action : " + action.getId() + "   " + action.getName());

        if (action == null)
            return false;
        Toast.makeText(mContext, "Execute: \"" + action.getName() + "\"", Toast.LENGTH_LONG).show();
        if (mActionsList.containsKey(action.getId()))
            return mActionsList.get(action.getId()).doAction(mContext, args);
        return false;
    }

    public interface UserActionListener{
        boolean doAction(Context context, String[] args);
    }

    public static GsonAction   getActionFromId(String value){
        ActionListSingleton singleton = ActionListSingleton.getInstance();
        GsonActionList list = singleton.getData();
        if (list == null || list.getList() == null)
            return null;
        for (GsonAction action : list.getList())
            if (action.getId().equals(value))
                return action;
        return null;
    }

    public static GsonAction    getActionFromName(String value){
        ActionListSingleton singleton = ActionListSingleton.getInstance();
        GsonActionList list = singleton.getData();
        for (GsonAction action : list.getList())
            if (action.getName().equals(value))
                return action;
        return null;
    }

}
