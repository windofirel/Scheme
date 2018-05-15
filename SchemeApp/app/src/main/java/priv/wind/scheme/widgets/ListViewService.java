package priv.wind.scheme.widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import priv.wind.scheme.Beans.SchemeBean;
import priv.wind.scheme.R;
import priv.wind.scheme.SchemeApplication;
import priv.wind.scheme.data.SchemeBeanDao;

/**
 * @author Dongbaicheng
 * @version 2018/5/15
 */

public class ListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return  new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;

        private List<SchemeBean> mList = new ArrayList<>();

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
            Calendar now = Calendar.getInstance();
            String time = "%" + String.valueOf(now.get(Calendar.MONTH) + 1) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + "%";

            SchemeApplication application = ((SchemeApplication) getApplication());
            SchemeBeanDao mSchemeBeanDao = application.getDaoSession().getSchemeBeanDao();
            mList = mSchemeBeanDao.queryBuilder().where(SchemeBeanDao.Properties.BeginTime.like(time)).build().list();
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            mList.clear();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item);
            views.setTextViewText(R.id.tv_time, "item:" + mList.get(position).getFormatBeginTime());
            views.setTextViewText(R.id.tv_title, "item:" + mList.get(position).getTitle());

//            Bundle extras = new Bundle();
//            extras.putInt(ListViewService.INITENT_DATA, position);
//            Intent changeIntent = new Intent();
//            changeIntent.setAction(SchemeWidget.CHANGE_IMAGE);
//            changeIntent.putExtras(extras);

            /* android.R.layout.simple_list_item_1 --- id --- text1
             * listview的item click：将 changeIntent 发送，
             * changeIntent 它默认的就有action 是provider中使用 setPendingIntentTemplate 设置的action*/
//            views.setOnClickFillInIntent(android.R.id.text1, changeIntent);
            return views;
        }

        /* 在更新界面的时候如果耗时就会显示 正在加载... 的默认字样，但是你可以更改这个界面
         * 如果返回null 显示默认界面
         * 否则 加载自定义的，返回RemoteViews
         */
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
