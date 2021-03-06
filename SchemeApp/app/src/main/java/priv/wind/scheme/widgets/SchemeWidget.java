package priv.wind.scheme.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import priv.wind.scheme.R;
import priv.wind.scheme.mainuis.SchemeAty;

/**
 * @author Dongbaicheng
 * @version 2018/5/6
 */

public class SchemeWidget extends AppWidgetProvider {
    public static final String CLICK_ACTION = "com.seewo.appwidgettest.action.CLICK";

    // 点击事件的广播ACTION

    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context, SchemeAty.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 200, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_add, pendingIntent);

        Intent lvIntent = new Intent(context, ListViewService.class);
        remoteViews.setRemoteAdapter(R.id.lv_dtl, lvIntent);
//        remoteViews.setEmptyView(R.id.lv_dtl, android.R.id.empty);
        ComponentName  mComponentName = new ComponentName(context, SchemeWidget.class);
        appWidgetManager.updateAppWidget(mComponentName, remoteViews);

//        for (int appWidgetId : appWidgetIds) {
//            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
//        }
    }

    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (CLICK_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "hello dog!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * 当小部件大小改变时
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }


}
