package priv.wind.scheme.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * 闹钟广播接收
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        long id = intent.getLongExtra("id", 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                Ringtone r = RingtoneManager.getRingtone(context,notification);
                r.play();
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r.stop();
            }
        }).start();
        Intent alarmIntent = new Intent(context, AlarmAty.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtra("id", id);
        context.startActivity(alarmIntent);
    }
}
