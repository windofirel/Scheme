package priv.wind.scheme.mainuis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;
import priv.wind.scheme.SchemeApplication;
import priv.wind.scheme.alarms.AlarmHelper;
import priv.wind.scheme.data.SchemeBeanDao;
import priv.wind.scheme.lockscreens.LockService;
import priv.wind.scheme.mainuis.adapters.SchemeAdapter;

/**
 * 账户Activity
 */
public class SchemeAty extends AppCompatActivity {

    private static final String TAG = "ben: SchemeAty";
    public SchemeBeanDao mSchemeBeanDao;
    public SchemeAdapter mAdapter;
    public List<SchemeBean> mSchemeBeans;
    private MainFrg mMainFrg;
    private DetailFrg mDetailFrg;
    public GestureDetector mGestureDetector;
    public AlarmHelper mAlarmHelper;

    public static final int BEGIN_TIME = 997;
    public static final int END_TIME = 891;
    public static final int EDIT_BEGIN_TIME = 272;
    public static final int EDIT_END_TIME = 623;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_scheme);
        initToolbar();
        if (savedInstanceState == null) {
            init();
            Intent intent = new Intent(SchemeAty.this, LockService.class);
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        mSchemeBeanDao = null;
        mAdapter = null;
        mSchemeBeans = null;
        mMainFrg = null;
        mDetailFrg = null;
        super.onDestroy();
    }

    /**
     * 切换当前界面到添加账号界面
     */
    public void jump2Add() {
        Log.i(TAG, "jump2Add: ");
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(mMainFrg);
        transaction.add(R.id.frg_content, new AddFrg(), "add");
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    /**
     * 切换当前界面到账号明细界面
     */
    public void jump2Detail(SchemeBean schemeBean) {
        Log.i(TAG, "jump2Detail: ");
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(mMainFrg);
        mDetailFrg.setSchemeBean(schemeBean);
        transaction.add(R.id.frg_content, mDetailFrg);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    /**
     * 从查看账户明细切换到编辑账户信息
     *
     * @param schemeBean 账户对象
     */
    public void jump2Edit(SchemeBean schemeBean) {
        Log.i(TAG, "jump2Edit: ");
        FragmentManager manager = getSupportFragmentManager();
        //        manager.popBackStack();编辑完返回主界面
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        EditFrg editFrg = new EditFrg();
        editFrg.setSchemeBean(schemeBean);
        transaction.replace(R.id.frg_content, editFrg, "edit");
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    /**
     * 初始化账户列表数据与各子界面实例
     */
    private void init() {
        Log.i(TAG, "init: ");
        mAlarmHelper = new AlarmHelper(this);
        mGestureDetector = new GestureDetector(this, new AccountGestureListener());
        SchemeApplication application = ((SchemeApplication) getApplication());
        mSchemeBeanDao = application.getDaoSession().getSchemeBeanDao();
        mSchemeBeans = mSchemeBeanDao.queryBuilder().build().list();

        mAdapter = new SchemeAdapter(mSchemeBeans, mSchemeBeanDao);
        mAdapter.setOnItemClickListener(new SchemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jump2Detail(mSchemeBeans.get(position));
            }
        });

        mMainFrg = new MainFrg();
        mDetailFrg = new DetailFrg();
        //添加主页面
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frg_content, mMainFrg);
        transaction.commit();
    }

    /**
     * 初始化顶部工具条
     */
    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar_account));
        toolbar.setTitle(R.string.app_name);
        //        toolbar.setLogo(R.mipmap.eight_diagram_dark);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchemeAty.this.onBackPressed();
            }
        });

        //toolbar的menu点击事件的监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_item1) {
                    Intent intent = new Intent(SchemeAty.this, ConfigAty.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);//加载menu文件到布局
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == BEGIN_TIME) {
            String time = data.getStringExtra("time");
            FragmentManager manager = getSupportFragmentManager();
            AddFrg addFrg = ((AddFrg) manager.findFragmentByTag("add"));
            addFrg.setBeginTime(time);
        }

        if (resultCode == END_TIME) {
            String time = data.getStringExtra("time");
            FragmentManager manager = getSupportFragmentManager();
            AddFrg addFrg = ((AddFrg) manager.findFragmentByTag("add"));
            addFrg.setEndTime(time);
        }

        if (resultCode == EDIT_BEGIN_TIME) {
            String time = data.getStringExtra("time");
            FragmentManager manager = getSupportFragmentManager();
            EditFrg editFrg = ((EditFrg) manager.findFragmentByTag("edit"));
            editFrg.setBeginTime(time);
        }

        if (resultCode == EDIT_END_TIME) {
            String time = data.getStringExtra("time");
            FragmentManager manager = getSupportFragmentManager();
            EditFrg editFrg = ((EditFrg) manager.findFragmentByTag("edit"));
            editFrg.setEndTime(time);
        }
    }

    /**
     * 底部弹框显示提示信息
     *
     * @param message 提示信息
     */
    public void showMsg(String message) {
        Snackbar.make(this.getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 设置手势监听
     *
     * @param view fragment
     */
    public void setGestureListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    public class AccountGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i(TAG, "onScroll: ");
            if (distanceX < -20) {
                SchemeAty.this.onBackPressed();
            }

            return true;
        }
    }
}
