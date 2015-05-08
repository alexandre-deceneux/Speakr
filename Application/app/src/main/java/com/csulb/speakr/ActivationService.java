package com.csulb.speakr;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Alex on 20/03/2015.
 */
public class ActivationService extends Service {

    public final static String TAG = "ActivationService";

    Intent mIntent;
    private int NOTIFICATION_ID = 862821961;

    /**
     * Ajoute une notification
     */
    private void createNotification(String name, String content, PendingIntent intentContent){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker(name)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(name)
                .setContentText(content)
                .setOngoing(true)
                .setContentIntent(intentContent);
        mNotification.notify(NOTIFICATION_ID, builder.build());
    }

    public void startMicrophoneListening(){
        SpeechRecognizer sp = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        ActionMakerListener actionMakerListener = new ActionMakerListener(getApplicationContext());
        actionMakerListener.setOnFinishListener(new OnFinishListener(){
            @Override
            public void onFinish() {
                onDestroy();
            }

            @Override
            public void onFinish(Bundle results) {}
        });
        sp.setRecognitionListener(actionMakerListener);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.csulb.speakr");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        sp.startListening(intent);
        Toast.makeText(getApplicationContext(), "StartListening", Toast.LENGTH_SHORT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mIntent = intent;

        if (intent == null)
            return 0;
        Bundle extras = intent.getExtras();
        if (extras == null || extras.containsKey("start") == false || extras.getBoolean("start") == false)
            return 0;
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.micro_widget2);
        ComponentName thisWidget = new ComponentName(this, MicroWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, view);

        // Suppression de notification s'il y en a
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        createNotification("Speakr", "Service running", null);
        startMicrophoneListening();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        // Démarrer le service dans un thread
        HandlerThread thread = new HandlerThread("ServiceStartArguments", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Looper mServiceLooper = thread.getLooper();
    }

    @Override
    public void onDestroy(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.micro_widget);
        Intent serviceIntent = new Intent(getApplicationContext(), ActivationService.class);
        serviceIntent.putExtra("start", true);
        PendingIntent pendingIntent = PendingIntent.getService(
                getApplicationContext().getApplicationContext(), 0, serviceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.icon, pendingIntent);
        ComponentName thisWidget = new ComponentName(this, MicroWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, view);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
