package com.csulb.speakr;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Alex on 20/03/2015.
 */
public class MicroWidget extends AppWidgetProvider {

    public final static String TAG = "MicroWidget";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.micro_widget);
            Intent serviceIntent = new Intent(context, ActivationService.class);
            serviceIntent.putExtra("start", true);
            PendingIntent pendingIntent = PendingIntent.getService(
                    context.getApplicationContext(), 0, serviceIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.icon, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
