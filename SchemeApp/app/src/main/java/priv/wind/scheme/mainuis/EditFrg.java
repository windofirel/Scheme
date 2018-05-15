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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;

/**
 * @author Dongbaicheng
 * @version 2018/1/3
 */

public class EditFrg extends Fragment {

    //region 全局变量
    private static final String TAG = "ben: EditFrg";
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

    private SchemeAty mAty;
    private SchemeBean mSchemeBean;
    //endregion

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.frg_edit, container, false);
        unbinder = ButterKnife.bind(this, view);

        mAty = ((SchemeAty) getActivity());
        if (mSchemeBean != null) {
            show();
        }

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

    /**
     * 保存修改的账户信息
     */
    @OnClick(R.id.fab_save)
    public void onFabClick(View view) {
        Log.i(TAG, "onFabClick: ");
        save();
    }

    @OnClick(R.id.tv_begin_time)
    public void onBeginTimeClick(View view){
        Intent intent = new Intent(mAty, TimeAty.class);
        intent.putExtra("type", SchemeAty.EDIT_BEGIN_TIME);
        intent.putExtra("edit_time", mSchemeBean.getBeginTime());
        startActivityForResult(intent, SchemeAty.EDIT_BEGIN_TIME);
    }

    @OnClick(R.id.tv_end_time)
    public void onEndTimeClick(View view){
        Intent intent = new Intent(mAty, TimeAty.class);
        intent.putExtra("type", SchemeAty.EDIT_END_TIME);
        intent.putExtra("edit_time", mSchemeBean.getEndTime());
        startActivityForResult(intent, SchemeAty.EDIT_END_TIME);
    }

    public void setBeginTime(String beginTime){
        mSchemeBean.setBeginTime(beginTime);
        mTvBeginTime.setText(mSchemeBean.getFormatBeginTime());
    }

    public void setEndTime(String endTime){
        mSchemeBean.setEndTime(endTime);
        mTvEndTime.setText(mSchemeBean.getFormatEndTime());
    }

    /**
     * 保存修改的账户信息
     */
    private void save() {
        Log.i(TAG, "save: ");
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

        mAty.mSchemeBeanDao.update(mSchemeBean);
        mAty.mAdapter.notifyDataSetChanged();
        String msg = String.format(getString(R.string.edit_success_tips), title);
        mAty.showMsg(msg);
        mAty.onBackPressed();
    }

    public void setSchemeBean(SchemeBean schemeBean) {
        mSchemeBean = schemeBean;
    }

    /**
     * 显示账户信息
     */
    private void show() {
        Log.i(TAG, "show: ");
        mEtTitle.setText(mSchemeBean.getTitle());
        mEtContent.setText(mSchemeBean.getContent());
        mTvBeginTime.setText(mSchemeBean.getFormatBeginTime());
        mTvEndTime.setText(mSchemeBean.getFormatEndTime());
    }
}
