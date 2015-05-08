package com.csulb.speakr;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.csulb.speakr.actionlistdata.GsonAction;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddCommandFragment extends Fragment {

    private final static String TAG = "AddCommandFragment";
    public final static int SELECT_ACTION_RESULT = 42;

    private View mView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView != null)
            return mView;
        mView = inflater.inflate(R.layout.fragment_add_command, container, false);

        Button speakButton = (Button)mView.findViewById(R.id.speak_button);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechRecognizer sp = SpeechRecognizer.createSpeechRecognizer(getActivity());
                RecognitionModule listener = new RecognitionModule(getActivity());
                listener.setOnFinishListener(new OnFinishListener() {
                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onFinish(Bundle results) {
                        if (results == null)
                            return;
                        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        if (data.size() == 0)
                            return;
                        ((TextView)mView.findViewById(R.id.command_input)).setText(data.get(0).toString());
                    }
                });
                sp.setRecognitionListener(listener);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.csulb.speakr");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                sp.startListening(intent);
            }
        });

        TextView defineAction = (TextView)mView.findViewById(R.id.define_action);
        defineAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectActionActivity.class);
                startActivityForResult(intent, AddCommandFragment.SELECT_ACTION_RESULT);
            }
        });

        Button associateButton = (Button)mView.findViewById(R.id.associate_button);
        associateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = ((TextView) mView.findViewById(R.id.command_input)).getText().toString();
                String action = ((TextView) mView.findViewById(R.id.define_action)).getText().toString();
                Toast.makeText(getActivity(), "\"" + key + "\" bound with \"" + action + "\"", Toast.LENGTH_LONG).show();
                SharedPreferences prefs = getActivity().getSharedPreferences("UserActions", 0);
                GsonAction gAction = Actions.getActionFromName(action);
                String actionId = (gAction != null) ? gAction.getId() : "";
                prefs.edit().putString(key, actionId).commit();
                ((MainActivity) getActivity()).invalidateList();
            }
        });

        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddCommandFragment.SELECT_ACTION_RESULT)
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle == null)
                    return;
                String action = bundle.getString("value");
                TextView textView = (TextView)mView.findViewById(R.id.define_action);
                textView.setText(action);
            }
    }

}
