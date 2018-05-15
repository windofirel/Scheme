package priv.wind.scheme.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

/**
 * 闹钟助手
 *
 * @author Dongbaicheng
 * @version 2018/5/15
 */

public class AlarmHelper {
    private Context mContext;
    private AlarmManager mAlarmManager;

    public AlarmHelper(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public void test(){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.setAction("223");
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        //        intent.putExtra("id", id);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }else {
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }
    }

    /**
     * 设置闹钟
     *
     * @param time 时间
     */
    public void setAlarm(String time, long id) {
        String[] temps = time.split("-");
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        now.set(Calendar.MONTH, Integer.parseInt(temps[0]) - 1);
        now.set(Calendar.DAY_OF_MONTH, Integer.parseInt(temps[1]));
        now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(temps[2]));
        now.set(Calendar.HOUR, Integer.parseInt(temps[2])%12);
        now.set(Calendar.MINUTE, Integer.parseInt(temps[3]));
        now.set(Calendar.SECOND, 0);

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.setAction(time);
        intent.putExtra("id", id);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        // 设置闹钟重复时间

        long times = now.getTimeInMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, times, pi);
        }else {
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, times, pi);
        }
    }

    /**
     * 取消闹钟
     * @param time
     */
    public void cancelAlarm(String time){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.setAction(time);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        mAlarmManager.cancel(pi);
    }
}
