package priv.wind.scheme.lockscreens.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;

/**
 * @author Dongbaicheng
 * @version 2018/5/14
 */

public class LockAdapter extends RecyclerView.Adapter {
    private List<SchemeBean> mSchemeBeans;

    public LockAdapter(List<SchemeBean> schemeBeans) {
        mSchemeBeans = schemeBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_scheme, parent, false);
        return new LockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LockViewHolder viewHolder = ((LockViewHolder) holder);

        SchemeBean schemeBean = mSchemeBeans.get(position);
        viewHolder.mTvTitle.setText(schemeBean.getTitle());
        viewHolder.mTvBeginTime.setText(schemeBean.getFormatBeginTime());
        viewHolder.mTvEndTime.setText(schemeBean.getFormatEndTime());
    }

    @Override
    public long getItemId(int position) {
        return mSchemeBeans.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mSchemeBeans.size();
    }

    class LockViewHolder extends RecyclerView.ViewHolder{
        TextView mTvTitle;
        TextView mTvBeginTime;
        TextView mTvEndTime;

        LockViewHolder(View itemView) {
            super(itemView);
            mTvTitle = ((TextView) itemView.findViewById(R.id.tv_title));
            mTvBeginTime = ((TextView) itemView.findViewById(R.id.tv_begin_time));
            mTvEndTime = ((TextView) itemView.findViewById(R.id.tv_end_time));
        }
    }
}
