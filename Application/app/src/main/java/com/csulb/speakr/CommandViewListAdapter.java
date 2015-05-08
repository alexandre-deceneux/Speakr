package com.csulb.speakr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommandViewListAdapter extends BaseAdapter{

    private final static String TAG = "CommandViewListAdapter";

    private String              []mActionList;
    private String              []mCommandList;
    private Context             mContext;
    private OnRemoveListener    mOnRemoveListener;

    public CommandViewListAdapter(Context context, String []actionList, String[] commandList){
        mContext = context;
        mActionList = actionList;
        mCommandList = commandList;
        mOnRemoveListener = null;
    }

    public void setOnRemoveListener(OnRemoveListener onRemoveListener){
        mOnRemoveListener = onRemoveListener;
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
            view = mInflater.inflate(R.layout.adapter_command_list, null);
            final String action = mActionList[i];
            final String command = mCommandList[i];
            ((TextView)view.findViewById(R.id.action)).setText(action);
            ((TextView)view.findViewById(R.id.command)).setText(command);
            view.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRemoveListener != null)
                        mOnRemoveListener.onClick(action, command);
                }
            });
        }
        return view;
    }

    public interface OnRemoveListener{
        void    onClick(String action, String command);
    }

}
