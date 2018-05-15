package priv.wind.scheme.mainuis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;


/**
 * @author Dongbaicheng
 * @version 2017/12/28
 */

public class AddFrg extends Fragment {

    //region 全局变量
    private static final String TAG = "ben: AddFrg";
    @BindView(R.id.ll_top)
    LinearLayout mLlTop;
    @BindView(R.id.tv_divider)
    TextView mTvDivider;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.tv_begin_time)
    TextView mTvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.fab_save)
    FloatingActionButton mFabSave;
    Unbinder unbinder;
    @BindView(R.id.swt_alarm)
    Switch mSwtAlarm;

    private SchemeAty mAty;
    private SchemeBean mSchemeBean;

    //endregion

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.frg_add, container, false);
        unbinder = ButterKnife.bind(this, view);
        mSchemeBean = new SchemeBean();
        mAty = ((SchemeAty) getActivity());

        Calendar now = Calendar.getInstance();
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(now.get(Calendar.MINUTE));
        String time = String.format("%s-%s-%s-%s", month, day, hour, minute);

        mSchemeBean.setBeginTime(time);
        mSchemeBean.setEndTime(time);

//        mEtTitle.setText("超市买东西");
//        mEtContent.setText("去天天买洗衣液、牛奶");
        mTvBeginTime.setText(mSchemeBean.getFormatBeginTime());
        mTvEndTime.setText(mSchemeBean.getFormatEndTime());

        mAty.setGestureListener(view);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: ");
        super.onDestroyView();
        mAty = null;
        unbinder.unbind();
    }

    @OnClick(R.id.fab_save)
    public void onFabClick(View view) {
        Log.i(TAG, "onFabClick: ");
        saveData();
    }

    @OnClick(R.id.tv_begin_time)
    public void onBeginTimeClick(View view) {
        Intent intent = new Intent(mAty, TimeAty.class);
        intent.putExtra("type", SchemeAty.BEGIN_TIME);
        startActivityForResult(intent, SchemeAty.BEGIN_TIME);
    }

    @OnClick(R.id.tv_end_time)
    public void onEndTimeClick(View view) {
        Intent intent = new Intent(mAty, TimeAty.class);
        intent.putExtra("type", SchemeAty.END_TIME);
        startActivityForResult(intent, SchemeAty.END_TIME);
    }

    public void setBeginTime(String beginTime) {
        mSchemeBean.setBeginTime(beginTime);
        mTvBeginTime.setText(mSchemeBean.getFormatBeginTime());
    }

    public void setEndTime(String endTime) {
        mSchemeBean.setEndTime(endTime);
        mTvEndTime.setText(mSchemeBean.getFormatEndTime());
    }

    /**
     * 保存账户信息
     */
    private void saveData() {
        boolean errorFlag = false;
        String title = mEtTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            mEtTitle.setError("请输入标题");
            errorFlag = true;
        }

        if (errorFlag) {
            return;
        }
        String content = mEtContent.getText().toString().trim();
        mSchemeBean.setTitle(title);
        mSchemeBean.setContent(content);
        mAty.mSchemeBeanDao.insert(mSchemeBean);
        //添加闹钟
        if (mSwtAlarm.isChecked()) {
            mAty.mAlarmHelper.setAlarm(mSchemeBean.getBeginTime(), mSchemeBean.getId());
        }

        mAty.mSchemeBeans.add(mSchemeBean);
        mAty.mAdapter.notifyDataSetChanged();
        mAty.showMsg(String.format("添加日程 '%s' 信息成功", title));
        mAty.onBackPressed();
    }

    /**
     * 清空界面数据
     */
    private void clear() {
        mEtTitle.setText(null);
        mEtContent.setText(null);
        mTvBeginTime.setText(null);
        mTvEndTime.setText(null);
    }
}
