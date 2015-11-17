package org.diaosstudio.app.android.diaostorch.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.diaosstudio.app.android.diaostorch.Constants;
import org.diaosstudio.app.android.diaostorch.R;

/**
 * Created by yzshang on 15-10-13.
 */
public class DiaosTorchProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent receiver = new Intent(context , DiaosTorchReceiver.class);
        receiver.setAction(Constants.DIAOS_WIDGET_ACTION);
        receiver.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context , 0 , receiver , 0);


        RemoteViews remoteViews = new RemoteViews(context.getPackageName() , R.layout.torch_widget);
        remoteViews.setOnClickPendingIntent(R.id.widget_image , pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds , remoteViews);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
