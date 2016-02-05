package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by JuanJose on 12/27/2015.
 */
public class SoccerWidget extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Perform this loop procedure for each App Widget that belongs to this provider

        Log.e("enElWidget","se ha llamado al metodo onUpdate");

        for (int appWidgetId : appWidgetIds) {
            Log.e("enElWidget","0");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            Log.e("enElWidget","1");
            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, MainActivity.class);
            Log.e("enElWidget","2");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            Log.e("enElWidget","3");
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            Log.e("enElWidget","4");
            // Set up the collection
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                Log.e("enElWidget","5.1");
                setRemoteAdapter(context, views);
            } else {
                Log.e("enElWidget","5.2");
                setRemoteAdapterV11(context, views);
            }
            Log.e("enElWidget","6");
            Intent clickIntentTemplate =  new Intent(context, MainActivity.class);
            Log.e("enElWidget","7");
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Log.e("enElWidget","8");
            views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate);
            Log.e("enElWidget","9");
            views.setEmptyView(R.id.widget_list, R.id.widget_empty);

            // Tell the AppWidgetManager to perform an update on the current app widget
            Log.e("enElWidget","10");
            appWidgetManager.updateAppWidget(appWidgetId, views);
            Log.e("enElWidget","11");
        }
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        //if (SunshineSyncAdapter.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        //}
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, SoccerWidgetProvider.class));
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list,
                new Intent(context, SoccerWidgetProvider.class));
    }

}
