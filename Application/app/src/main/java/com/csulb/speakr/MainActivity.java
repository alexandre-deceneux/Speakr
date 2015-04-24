package com.csulb.speakr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button validateEntryButton = (Button)findViewById(R.id.validateEntryButton);
        validateEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = ((TextView) findViewById(R.id.textEntry)).getText().toString();
                String action = ((Spinner) findViewById(R.id.actionsList)).getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "\"" + key + "\" bind with \"" + action + "\"", Toast.LENGTH_LONG).show();
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("UserActions", 0);
                prefs.edit().putString(key, action).commit();
            }
        });

        Button speakButton = (Button)findViewById(R.id.speakButton);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechRecognizer sp = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
                sp.setRecognitionListener(new ActionMakerListener(getApplicationContext()));
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.csulb.speakr");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                sp.startListening(intent);
            }
        });

        Button recordButton = (Button)findViewById(R.id.startButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechRecognizer sp = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
                RecognitionModule listener = new RecognitionModule(MainActivity.this);
                listener.setOnFinishListener(new OnFinishListener() {
                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onFinish(Bundle results) {
                        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        ((TextView) findViewById(R.id.textEntry)).setText(data.get(0).toString());
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
}
