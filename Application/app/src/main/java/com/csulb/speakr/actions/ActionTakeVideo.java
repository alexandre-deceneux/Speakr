package com.csulb.speakr.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.csulb.speakr.Actions;

import java.io.File;
import java.util.Date;

/**
 * Created by Alex on 07/05/2015.
 */
public class ActionTakeVideo implements Actions.UserActionListener  {
    public final static String TAG = "ActionTakePicture";

    @Override
    public boolean doAction(Context context, String[] args) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File videosFolder = new File(Environment.getExternalStorageDirectory(), "MyVideos");
        videosFolder.mkdirs();
        File image = new File(videosFolder, "video_" + (new Date()).toString() + ".mp4");
        Uri uriSavedVideo = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedVideo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }
}
