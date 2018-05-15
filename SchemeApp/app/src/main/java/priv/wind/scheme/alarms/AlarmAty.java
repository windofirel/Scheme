package priv.wind.scheme.alarms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;
import priv.wind.scheme.SchemeApplication;
import priv.wind.scheme.data.SchemeBeanDao;

/**
 * 提醒界面
 */
public class AlarmAty extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_begin_time)
    TextView mTvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_alarm);
        ButterKnife.bind(this);
        initToolbar();

        long id = getIntent().getLongExtra("id", 0);
        if (id == 0){
            return;
        }
        // TODO: 2018/5/15 不知道能不能查询成功
        SchemeApplication application = ((SchemeApplication) getApplication());
        SchemeBeanDao mSchemeBeanDao = application.getDaoSession().getSchemeBeanDao();
        SchemeBean mSchemeBean = mSchemeBeanDao.queryBuilder().where(SchemeBeanDao.Properties.Id.eq(id)).build().list().get(0);

        mTvTitle.setText(mSchemeBean.getTitle());
        mTvBeginTime.setText(mSchemeBean.getFormatBeginTime());
        mTvEndTime.setText(mSchemeBean.getFormatEndTime());
        mTvContent.setText(mSchemeBean.getContent());
    }

    /**
     * 初始化顶部工具条
     */
    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar_account));
        toolbar.setTitle("日程提醒");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmAty.this.onBackPressed();
            }
        });
    }

}
