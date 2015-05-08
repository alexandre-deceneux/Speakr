package com.csulb.speakr.actionlistdata;

import android.content.res.Resources;

import com.csulb.speakr.R;

import java.lang.reflect.Field;

/**
 * Created by Alex on 07/05/2015.
 */
public class ActionListSingleton {

    static private ActionListSingleton mInstance = null;
    static private GsonActionList   mData = null;

    static public ActionListSingleton getInstance(){
        if (mInstance == null){
            mInstance = new ActionListSingleton();
            mInstance.mData = new GsonActionList();
        }
        return mInstance;
    }

    public GsonActionList   getData(){
        return mInstance.mData;
    }

    public void  setData(GsonActionList data, Resources resources){
        mInstance.mData = data;
        updateStrings(resources);
    }

    private void updateStrings(Resources resources){
        GsonAction [] list = mInstance.mData.getList();
        for (GsonAction action : list){
            int nameId = getStringId(action.getName());
            if (nameId != -1)
                action.setName(resources.getString(nameId));
            int descriptionId = getStringId(action.getDescription());
            if (descriptionId != -1)
                action.setDescription(resources.getString(descriptionId));
            String [] params = action.getParams();
            for (int i = 0; i < params.length; ++i){
                int paramId = getStringId(params[i]);
                if (paramId != -1)
                    params[i] = resources.getString(paramId);
            }
        }
    }

    static private int getStringId(String resName) {
        try {
            Field idField = R.string.class.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
