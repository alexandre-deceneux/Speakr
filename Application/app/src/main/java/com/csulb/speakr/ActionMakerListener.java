package com.csulb.speakr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Alex on 20/03/2015.
 */
public class ActionMakerListener implements RecognitionListener {

    Context mContext;
    Actions mActionMaker;
    OnFinishListener mOnFinishListener;

    public ActionMakerListener(Context context){
        mContext = context;
        mActionMaker = new Actions(context);
        mOnFinishListener = null;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener){
        mOnFinishListener = onFinishListener;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {
        if (mOnFinishListener != null)
            mOnFinishListener.onFinish();
    }

    @Override
    public void onResults(Bundle results) {
        SharedPreferences prefs = mContext.getSharedPreferences("UserActions", 0);
        Log.d("RESULT FINAL", "onResults " + results);
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (int i = 0; i < data.size(); i++)
        {
            String value = prefs.getString(data.get(i).toString(), null);
            if (value != null) {
                mActionMaker.executeAction(value);
                Toast.makeText(mContext, "J'ai compris: \"" + value + "\"", Toast.LENGTH_LONG).show();
                if (mOnFinishListener != null)
                    mOnFinishListener.onFinish();
                return;
            }
        }
        if (mOnFinishListener != null)
            mOnFinishListener.onFinish();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
