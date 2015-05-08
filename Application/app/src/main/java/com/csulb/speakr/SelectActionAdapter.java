package com.csulb.speakr;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csulb.speakr.actionlistdata.GsonAction;


public class SelectActionAdapter extends BaseAdapter {

    private final static String TAG = "CommandViewListAdapter";

    private GsonAction              []mActionList;
    private Context             mContext;
    private OnActionSelected    mOnActionSelected;

    public SelectActionAdapter(Context context, GsonAction[] actionList){
        mContext = context;
        mActionList = actionList;
        mOnActionSelected = null;
    }

    public void setOnRemoveListener(OnActionSelected onActionSelected){
        mOnActionSelected = onActionSelected;
    }

    @Override
    public int getCount() {
        return mActionList.length;
    }

    @Override
    public Object getItem(int i) {
        if (i >= mActionList.length)
            return null;
        return mActionList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (mContext == null || i >= mActionList.length)
            return null;
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.adapter_action_list, null);
        }
        final String action = mActionList[i].getName();
        TextView actionText = (TextView) view.findViewById(R.id.action);
        actionText.setText(action);
        actionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnActionSelected != null)
                    mOnActionSelected.onClick(action);
            }
        });
        return view;
    }

    public interface OnActionSelected{
        void    onClick(String action);
    }

}

