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
public class ActionTakePicture implements Actions.UserActionListener {

    public final static String TAG = "ActionTakePicture";

    @Override
    public boolean doAction(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, "image_" + (new Date()).toString() + ".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

}
