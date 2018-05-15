package priv.wind.scheme.mainuis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class DetailFrg extends Fragment {

    //region 全局变量
    private static final String TAG = "ben: DetailFrg";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_begin_time)
    TextView mTvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.fab_edit)
    FloatingActionButton mFabEdit;
    Unbinder unbinder;

    private SchemeBean mSchemeBean;
    private SchemeAty mAty;
    //endregion

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.frg_detail, container, false);
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

    @OnClick(R.id.fab_edit)
    public void onViewClicked() {
        Log.i(TAG, "onViewClicked: fab_edit");
        //跳转到编辑账户信息
        mAty.jump2Edit(mSchemeBean);
    }

    /**
     * 显示账户信息
     */
    private void show() {
        Log.i(TAG, "show: ");
        mTvTitle.setText(mSchemeBean.getTitle());
        mTvBeginTime.setText(mSchemeBean.getFormatBeginTime());
        mTvEndTime.setText(mSchemeBean.getFormatEndTime());
        mTvContent.setText(mSchemeBean.getContent());
    }

    public SchemeBean getSchemeBean() {
        return mSchemeBean;
    }

    public void setSchemeBean(SchemeBean schemeBean) {
        mSchemeBean = schemeBean;
    }
}
