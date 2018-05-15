package priv.wind.scheme.mainuis.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import priv.wind.scheme.R;


/**
 * @author Dongbaicheng
 * @version 2017/12/28
 */

public class SchemeDivider extends RecyclerView.ItemDecoration {
    private float mDividerHeight;
    private Paint mPaint;

    public SchemeDivider(Context context) {
        this.mDividerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, context.getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(context.getResources().getColor(R.color.primary_text));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1){
                    outRect.set(0, 0, (int) mDividerHeight, 0);
                }
            } else {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1){
                    outRect.set(0, 0, 0, (int) mDividerHeight);
                }
            }
        }
    }


    /**
     * 画divider (orientation为vertical)
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        // recyclerView是否设置了paddingLeft和paddingRight
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            // divider的top 应该是 item的bottom 加上 marginBottom 再加上 Y方向上的位移
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(child.getTranslationY());
            // divider的bottom就是top加上divider的高度了
            final int bottom = (int) (top + mDividerHeight);
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 画divider (当orientation为horizontal)
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        // 和drawVertical差不多 left right 与 top和bottom对调一下
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin +
                    Math.round(child.getTranslationX());
            final int right = (int) (left + mDividerHeight);
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
