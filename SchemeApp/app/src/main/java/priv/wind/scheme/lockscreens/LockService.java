package priv.wind.scheme.lockscreens;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LockService extends Service {
    private String TAG = this.getClass().getSimpleName();
    private IBinder mBinder;

    public LockService() {
        mBinder = new LockBinder();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
        // 在此重新启动,使服务常驻内存
        startService(new Intent(this, LockService.class));
    }

    public void start(){
        IntentFilter mScreenOnFilter = new IntentFilter();
        mScreenOnFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mScreenOnFilter.addAction(Intent.ACTION_SCREEN_ON);
        LockService.this.registerReceiver(mScreenActionReceiver, mScreenOnFilter);
    }

    public void stop(){
        this.unregisterReceiver(mScreenActionReceiver);
    }


    private BroadcastReceiver mScreenActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
                Intent LockIntent = new Intent(LockService.this, LockAty.class);
                LockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LockIntent);
            } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                Log.i(TAG, "screen off");
            }
        }
    };

    public class LockBinder extends Binder {
        public LockService getService(){
            return LockService.this;
        }
    }

}
