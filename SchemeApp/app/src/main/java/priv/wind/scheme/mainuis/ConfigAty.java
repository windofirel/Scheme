package priv.wind.scheme.mainuis;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.wind.scheme.R;
import priv.wind.scheme.lockscreens.LockService;

public class ConfigAty extends AppCompatActivity {
    @BindView(R.id.swt_lock)
    Switch mSwtLock;

    private ServiceConnection mConnection;
    private LockService mService;
    private static boolean mFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_config);
        ButterKnife.bind(this);
        initToolbar();
        mSwtLock.setChecked(mFlag);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LockService.LockBinder serviceBinder = ((LockService.LockBinder) service);
                mService = serviceBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(ConfigAty.this, LockService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        mSwtLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mService.start();
                    mFlag = true;
                } else {
                    mService.stop();
                    mFlag = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    /**
     * 初始化顶部工具条
     */
    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar_account));
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigAty.this.onBackPressed();
            }
        });
    }
}
