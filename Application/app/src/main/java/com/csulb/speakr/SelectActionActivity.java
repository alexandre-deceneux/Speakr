package com.csulb.speakr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.csulb.speakr.actionlistdata.ActionListSingleton;
import com.csulb.speakr.actionlistdata.GsonAction;

public class SelectActionActivity extends FragmentActivity {

    private final static String TAG = "SelectActionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        GsonAction[] actions = ActionListSingleton.getInstance().getData().getList();
        SelectActionAdapter adapter = new SelectActionAdapter(this, actions);
        adapter.setOnRemoveListener(new SelectActionAdapter.OnActionSelected() {
            @Override
            public void onClick(String action) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("value", action);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        ListView list = (ListView)findViewById(R.id.action_list);
        list.setAdapter(adapter);
    }

}
