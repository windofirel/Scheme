package priv.wind.scheme.mainuis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import priv.wind.scheme.R;
import priv.wind.scheme.mainuis.adapters.SchemeDivider;
import priv.wind.scheme.mainuis.adapters.RecyclerItemTouchHelper;

/**
 * 主界面
 *
 * @author Dongbaicheng
 * @version 2017/12/28
 */

public class MainFrg extends Fragment {

    private static final String TAG = "ben: MainFrg";
    @BindView(R.id.rv_book)
    RecyclerView mRvBook;
    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    Unbinder unbinder;

    private SchemeAty mAty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.frg_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAty = ((SchemeAty) getActivity());

        mRvBook.addItemDecoration(new SchemeDivider(getContext()));
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvBook.setLayoutManager(layoutManager);

        mRvBook.setAdapter(mAty.mAdapter);
        ItemTouchHelper.Callback callback = new RecyclerItemTouchHelper(mAty.mAdapter, mAty);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRvBook);
        return view;
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: ");
        super.onDestroyView();
        unbinder.unbind();
        mAty = null;
    }

    @OnClick(R.id.fab_add)
    public void onViewClicked() {
        Log.i(TAG, "onViewClicked: fab_add");
        //添加新的账号信息 跳转到添加activity
        mAty.jump2Add();

//        mAty.mAlarmHelper.test();
    }
}
