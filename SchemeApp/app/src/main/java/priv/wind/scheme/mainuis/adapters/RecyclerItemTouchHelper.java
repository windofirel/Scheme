package priv.wind.scheme.mainuis.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;


/**
 * recyclerview 拖拽与左右滑动处理帮助类
 *
 * @author Dongbaicheng
 * @version 2018/1/4
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.Callback {
    private static final String TAG = "ben: RecyclerHelper";
    private final ItemTouchHelperCallback mHelperCallback;
    public Context mContext;

    public RecyclerItemTouchHelper(ItemTouchHelperCallback helperCallback, Context context) {
        mContext = context;
        mHelperCallback = helperCallback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "getMovementFlags: ");
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.i(TAG, "onMove: ");
        mHelperCallback.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i(TAG, "onSwiped: ");
        new AlertDialog.Builder(mContext)
                .setMessage("是否删除")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHelperCallback.onItemDelete(viewHolder.getAdapterPosition());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHelperCallback.onItemRefresh(viewHolder.getAdapterPosition());
                    }
                })
                .show();
    }

//    @Override
//    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        //滑动时自己实现背景及图片
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//
//            //dX大于0时向右滑动，小于0向左滑动
//            View itemView = viewHolder.itemView;//获取滑动的view
//            Resources resources = ((SchemeAty) mContext).getApplication().getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_add_black_48dp);//获取删除指示的背景图片
//            int padding = 0;//图片绘制的padding
//            int maxDrawWidth = 2 * padding + bitmap.getWidth();//最大的绘制宽度
//            Paint paint = new Paint();
//            paint.setColor(resources.getColor(R.color.primary_dark));
//            int x = Math.round(Math.abs(dX));
//            int drawWidth = Math.min(x, maxDrawWidth);//实际的绘制宽度，取实时滑动距离x和最大绘制距离maxDrawWidth最小值
//            int itemTop = itemView.getBottom() - itemView.getHeight();//绘制的top位置
//            //向右滑动
//            if (dX > 0) {
//                //根据滑动实时绘制一个背景
//                c.drawRect(itemView.getLeft(), itemTop, drawWidth, itemView.getBottom(), paint);
//                //在背景上面绘制图片
//                if (x > padding) {//滑动距离大于padding时开始绘制图片
//                    //指定图片绘制的位置
//                    Rect rect = new Rect();//画图的位置
//                    rect.left = itemView.getLeft() + padding;
//                    rect.top = itemTop + (itemView.getBottom() - itemTop - bitmap.getHeight()) / 2;//图片居中
//                    int maxRight = rect.left + bitmap.getWidth();
//                    rect.right = Math.min(x, maxRight);
//                    rect.bottom = rect.top + bitmap.getHeight();
//                    //指定图片的绘制区域
//                    Rect rect1 = null;
//                    if (x < maxRight) {
//                        rect1 = new Rect();//不能再外面初始化，否则dx大于画图区域时，删除图片不显示
//                        rect1.left = 0;
//                        rect1.top = 0;
//                        rect1.bottom = bitmap.getHeight();
//                        rect1.right = x - padding;
//                    }
//                    c.drawBitmap(bitmap, rect1, rect, paint);
//                }
//                //绘制时需调用平移动画，否则滑动看不到反馈
//                itemView.setTranslationX(dX);
//                float alpha = 1.0f - Math.abs(dX) / (float) itemView.getWidth();
//                itemView.setAlpha(alpha);
//            } else {
//                //如果在getMovementFlags指定了向左滑动（ItemTouchHelper。START）时则绘制工作可参考向右的滑动绘制，也可直接使用下面语句交友系统自己处理
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
//        } else {
//            //拖动时有系统自己完成
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//    }

    /**
     * 这个接口是给adapter实现的，主要写界面做了相应操作对后台数据影响的逻辑
     */
    public interface ItemTouchHelperCallback {
        void onItemDelete(int position);

        void onItemRefresh(int position);

        void onMove(int fromPosition, int toPosition);
    }
}
