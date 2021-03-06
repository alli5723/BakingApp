package com.omo_lanke.android.bakingapp.misc;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.omo_lanke.android.bakingapp.DetailsActivity;
import com.omo_lanke.android.bakingapp.MainActivity;
import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.misc.LinearWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = getListView(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getListView(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_linear_view);

        Intent intent = new Intent(context, LinearWidgetService.class);
        views.setRemoteAdapter(R.id.widget_listview, intent);

        Intent appIntent = new Intent(context, DetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_listview, pendingIntent);

        views.setEmptyView(R.id.widget_listview, R.id.empty_view);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions){
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

