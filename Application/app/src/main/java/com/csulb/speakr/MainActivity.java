package com.csulb.speakr;

import android.content.res.AssetManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.csulb.speakr.actionlistdata.ActionListSingleton;
import com.csulb.speakr.actionlistdata.GsonAction;
import com.csulb.speakr.actionlistdata.GsonActionList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends FragmentActivity {

    private final static String TAG = "MainActivity";

    private InvalidateListListener mInvalidateListListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSingleton();
        invalidateList();

        TabHost mTabHost = (TabHost)findViewById(R.id.tabHost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("TAB 1").setIndicator(getResources().getString(R.string.list)).setContent(R.id.view));
        mTabHost.addTab(mTabHost.newTabSpec("TAB 2").setIndicator(getResources().getString(R.string.add)).setContent(R.id.add));
    }

    private void    loadSingleton(){
        ActionListSingleton singleton = ActionListSingleton.getInstance();
        AssetManager assetManager = getAssets();
        try {
            InputStream ims = assetManager.open("action_list.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ims));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                fileContent.append(line);
            Gson gson = new Gson();
            GsonActionList dataIn = gson.fromJson(fileContent.toString().replace(" ", ""), GsonActionList.class);
            singleton.setData(dataIn, getResources());
        } catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void invalidateList(){
        if (mInvalidateListListener != null)
            mInvalidateListListener.invalideList();
    }

    public void setInvalidateListListener(InvalidateListListener listener){
        mInvalidateListListener = listener;
    }

    interface InvalidateListListener{
        void    invalideList();
    }
}
