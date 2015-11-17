package org.diaosstudio.app.android.diaostorch.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.diaosstudio.app.android.diaostorch.Constants;
import org.diaosstudio.app.android.diaostorch.R;
import org.diaosstudio.app.android.diaostorch.functions.Light;

/**
 * Created by yzshang on 15-10-13.
 */
public class DiaosTorchReceiver extends BroadcastReceiver {

    private static boolean isTorchOn = false;
    private static Light torch = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName() , R.layout.torch_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        ComponentName componentName = new ComponentName(context , DiaosTorchProvider.class);
        if(isTorchOn){
            remoteViews.setImageViewResource(R.id.widget_image , R.drawable.w_led_on);
        }else {
            remoteViews.setImageViewResource(R.id.widget_image , R.drawable.w_led_off);
        }

        Intent receiverIntent = new Intent(context , DiaosTorchReceiver.class);
        receiverIntent.setAction(Constants.DIAOS_WIDGET_ACTION);
        receiverIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetManager.getAppWidgetIds(componentName));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context , 0 , receiverIntent , 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_image, pendingIntent);

        appWidgetManager.updateAppWidget(componentName , remoteViews);

        if(isTorchOn){
            torch.turnOff();
            isTorchOn = false;
        }else{
            if(torch == null){
                torch = new Light(context);
            }
            torch.turnOn();
            isTorchOn = true;
        }
    }
}
