package com.csulb.speakr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.csulb.speakr.actionlistdata.GsonAction;

import java.util.Map;
import java.util.Set;

/**
 * Created by Alex on 24/04/2015.
 */
public class CommandViewFragment extends Fragment {

    private final static String TAG = "CommandViewFragment";

    private View mView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView != null)
            return mView;
        mView = inflater.inflate(R.layout.fragment_view, container, false);
        invalidateList();
        ((MainActivity)getActivity()).setInvalidateListListener(new MainActivity.InvalidateListListener() {
            @Override
            public void invalideList() {
                CommandViewFragment.this.invalidateList();
            }
        });
        return mView;
    }

    public void invalidateList(){
        SharedPreferences prefs = getActivity().getSharedPreferences("UserActions", 0);
        Map<String, ?> questionsAnswersBase = prefs.getAll();
        Set<String> keys = questionsAnswersBase.keySet();
        String  []actions = new String[keys.size()];
        String  []commands = new String[keys.size()];
        int i = 0;
        for (String key : keys){
            GsonAction gAction = Actions.getActionFromId(questionsAnswersBase.get(key).toString());
            actions[i] = (gAction != null) ? gAction.getName() : "";
            commands[i] = key;
            ++i;
        }
        final ListView    list = (ListView)mView.findViewById(R.id.list);
        CommandViewListAdapter adapter = new CommandViewListAdapter(getActivity(), actions, commands);
        adapter.setOnRemoveListener(mRemoveListner);
        list.setAdapter(adapter);
    }

    CommandViewListAdapter.OnRemoveListener mRemoveListner = new CommandViewListAdapter.OnRemoveListener() {
        @Override
        public void onClick(String action, String command) {
            final String commandStr = command;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.command_removal));
            builder.setMessage(getResources().getString(R.string.command_removal_hint1) +
                    action +
                    getResources().getString(R.string.command_removal_hint2));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences prefs = getActivity().getSharedPreferences("UserActions", 0);
                    prefs.edit().remove(commandStr).commit();
                    invalidateList();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    };

}
