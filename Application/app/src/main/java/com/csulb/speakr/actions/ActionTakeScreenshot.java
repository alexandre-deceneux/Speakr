package com.csulb.speakr.actions;

import android.content.Context;
import android.widget.Toast;

import com.csulb.speakr.Actions;
import java.io.OutputStream;

/**
 * Created by Alex on 07/05/2015.
 */
public class ActionTakeScreenshot implements Actions.UserActionListener {

    public final static String TAG = "ActionTakeScreenshot";

    @Override
    public boolean doAction(Context context) {
        try {
            Process sh = Runtime.getRuntime().exec("su");
            OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p " + "/sdcard/img.png").getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();
            Toast.makeText(context, "Screenshot saved.", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "You must have a rooted phone to do this.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
