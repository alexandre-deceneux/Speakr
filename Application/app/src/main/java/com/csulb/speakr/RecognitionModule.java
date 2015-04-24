package com.csulb.speakr;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;

public class RecognitionModule implements RecognitionListener {

    Context mContext;
    OnFinishListener mOnFinishListener;

    public RecognitionModule(Context context){
        mContext = context;
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
            mOnFinishListener.onFinish(null);
    }

    @Override
    public void onResults(Bundle results) {
        if (mOnFinishListener != null)
            mOnFinishListener.onFinish(results);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
