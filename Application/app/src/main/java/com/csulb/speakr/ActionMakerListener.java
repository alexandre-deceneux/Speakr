package com.csulb.speakr;

import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import com.csulb.speakr.actionlistdata.ActionListSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 20/03/2015.
 */
public class ActionMakerListener implements RecognitionListener {

    private final static String TAG = "ActionMakerListener";

    Context mContext;
    Actions mActionMaker;
    OnFinishListener mOnFinishListener;

    public ActionMakerListener(Context context) {
        mContext = context;
        mActionMaker = new Actions(context);
        mOnFinishListener = null;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
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
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        Set<String> keys = prefs.getAll().keySet();
        for (String action : keys) {
            Log.d(TAG, "Key: " + action);
            String[] ret = parseArguments(action, data);
            if (ret != null) {
                Log.d(TAG, "BIEN JOUE JB !!!!!!");
                for (String test : ret)
                    Log.d(TAG, "  -->" + test);
                //mActionMaker.executeAction("");
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

    /**
     * Get the parameters order of an action
     *
     * @param action Action to parse
     * @return Parameters order of an action.
     */
    static public Integer[] getParamsOrder(String action) {
        ArrayList<Integer> paramsOrder = new ArrayList<Integer>();
        String[] words = action.split(" ");
        for (String word : words) {
            if (word.length() > 2 && word.charAt(0) == '(' && word.charAt(word.length() - 1) == ')') {
                try {
                    paramsOrder.add(Integer.valueOf(word.substring(1, word.length() - 1)));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return paramsOrder.toArray(new Integer[paramsOrder.size()]);
    }


    public String generateArgumentsList(HashMap<Integer, String> hmap) {
        String argumentsList = "";
        Map<Integer, String> map = new TreeMap<Integer, String>(hmap);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            argumentsList += me.getValue().toString() + ";";
        }
        return argumentsList;
    }

    /**
     * Parse les parametres d'une commande en fonction d'une action.
     *
     * @param pattern Action a comparer.
     * @param data    Commandes possibles
     * @return Liste des parametres trouves. Null si la commande ne correspond pas a l'action.
     */
    public String[] parseArguments(String pattern, ArrayList data) {
        Pattern patternParenthesis = Pattern.compile("\\(([^\\)]+)\\)");
        String[] patternArray = pattern.split(" ");
        HashMap<Integer, String> hmap = new HashMap<Integer, String>();


        for (int i = 0, len = data.size(); i < len; i++) {
            String[] dataMatch = data.get(i).toString().split("\\s+");

            if (dataMatch.length == patternArray.length) {
                int j = 0;
                for (int patternLength = patternArray.length; j < patternLength; j++) {
                    Matcher matcher = patternParenthesis.matcher(patternArray[j]);
                    System.out.println(patternArray[j] + " - " + dataMatch[j]);
                    if (matcher.find()) {
                        hmap.put(Integer.parseInt(matcher.group(1)), dataMatch[j]);
                    } else if (!patternArray[j].equals(dataMatch[j])) {
                        System.out.println(">>>>>>" + patternArray[j] + " - " + dataMatch[j]);
                        hmap.clear();
                        break;
                    }
                }
                if (j == patternArray.length) {
                    String p = generateArgumentsList(hmap);
                    System.out.println(p);
                    return p.split(";");
                }
            }
        }

//        Integer []paramsOrder = getParamsOrder(pattern);
//        if (paramsOrder == null){
//            Log.e(TAG, "param order nicke");
//            return null;
//        }
//        for (int i = 0; i < data.size(); i++) {
//            String line = data.get(i).toString();
//            String delimKeyWords = "\\(\\d+\\)";
//            String [] keyWords = pattern.split(delimKeyWords);
//            String paramsDelim = "";
//            for (int i2 = 0; i2 < keyWords.length; ++i2) {
//                if (i2 != 0)
//                    paramsDelim += "\\s+";
//                else
//                    paramsDelim += "(.*)";
//                paramsDelim += keyWords[i2];
//                if (i2 != keyWords.length - 1)
//                    paramsDelim += "\\s+(.*)";
//                else
//                    paramsDelim += "(.*)";
//            }
//            String match = "";
//            for (int i2 = 0; i2 < paramsOrder.length; ++i2){
//                match += "$" + (i + 2);
//                if (i2 != paramsOrder.length - 1)
//                    match += ";";
//            }
//            String paramsLine = line.replaceAll(paramsDelim, match);
//            String[] params = paramsLine.split(";");
//
//            if (params.length == paramsOrder.length) {  ////PAS MIT DANS l'ORDRE !!
//                Log.d(TAG, "----> WIIIIIIIIIIIIIIIIIIINER !");
//                Log.d(TAG, "line : " + line);
//                Log.d(TAG, "paramsDelim : " + paramsDelim);
//                Log.d(TAG, "match : " + match);
//                Log.d(TAG, "Param line : " + paramsLine);
//                for (String test : params)
//                    Log.d(TAG, "   :" + test);
//                Log.d(TAG, "<----");
//                return params;
//            }
//        }
        return null;
    }
}
