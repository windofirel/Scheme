package priv.wind.scheme.mainuis.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;
import priv.wind.scheme.data.SchemeBeanDao;

/**
 * 账户列表适配器
 *
 * @author Dongbaicheng
 * @version 2017/11/15
 */

public class SchemeAdapter extends RecyclerView.Adapter implements RecyclerItemTouchHelper.ItemTouchHelperCallback{
    private List<SchemeBean> mSchemeBeans;
    private OnItemClickListener mClickListener;
    private SchemeBeanDao mSchemeBeanDao;


    public SchemeAdapter(List<SchemeBean> schemeBeans, SchemeBeanDao schemeBeanDao) {
        mSchemeBeans = schemeBeans;
        mSchemeBeanDao = schemeBeanDao;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_scheme, parent, false);
        return new AccountViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountViewHolder viewHolder = ((AccountViewHolder) holder);

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

    @Override
    public void onItemDelete(int position) {
        SchemeBean bean = mSchemeBeans.get(position);
        mSchemeBeanDao.deleteByKey(bean.getId());
        mSchemeBeans.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemRefresh(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        SchemeBean beanFrom = mSchemeBeans.get(fromPosition);
        SchemeBean beanTo =  mSchemeBeans.get(toPosition);
        long fromId = beanFrom.getId();
        beanFrom.setId(beanTo.getId());
        beanTo.setId(fromId);
        mSchemeBeanDao.update(beanFrom);
        mSchemeBeanDao.update(beanTo);

        Collections.swap(mSchemeBeans, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTvTitle;
        TextView mTvBeginTime;
        TextView mTvEndTime;
        private OnItemClickListener mClickListener;

        AccountViewHolder(View itemView, OnItemClickListener clickListener) {
            super(itemView);
            this.mClickListener = clickListener;
            mTvTitle = ((TextView) itemView.findViewById(R.id.tv_title));
            mTvBeginTime = ((TextView) itemView.findViewById(R.id.tv_begin_time));
            mTvEndTime = ((TextView) itemView.findViewById(R.id.tv_end_time));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
