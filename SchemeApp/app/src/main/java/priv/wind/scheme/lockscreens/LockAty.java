package priv.wind.scheme.lockscreens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;
import priv.wind.scheme.SchemeApplication;
import priv.wind.scheme.data.SchemeBeanDao;
import priv.wind.scheme.lockscreens.adapters.LockAdapter;
import priv.wind.scheme.mainuis.adapters.SchemeDivider;

public class LockAty extends AppCompatActivity {

    @BindView(R.id.rv_book)
    RecyclerView mRvBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.aty_lock);
        ButterKnife.bind(this);

        initToolbar();
        // TODO: 2018/5/14 仿照主界面，列表展示日程信息，查找出开始时间是当天的记录，但是不可编辑
        Calendar now = Calendar.getInstance();
        String time = "%" + String.valueOf(now.get(Calendar.MONTH) + 1) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + "%";

        SchemeApplication application = ((SchemeApplication) getApplication());
        SchemeBeanDao mSchemeBeanDao = application.getDaoSession().getSchemeBeanDao();
        List<SchemeBean> mSchemeBeans = mSchemeBeanDao.queryBuilder().where(SchemeBeanDao.Properties.BeginTime.like(time)).build().list();
        LockAdapter mAdapter = new LockAdapter(mSchemeBeans);

        mRvBook.addItemDecoration(new SchemeDivider(this));
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvBook.setLayoutManager(layoutManager);
        mRvBook.setAdapter(mAdapter);
    }

    /**
     * 初始化顶部工具条
     */
    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar_account));
        toolbar.setTitle("锁屏界面");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockAty.this.onBackPressed();
            }
        });
    }
}
