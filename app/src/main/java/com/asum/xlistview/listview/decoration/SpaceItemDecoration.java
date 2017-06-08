package com.asum.xlistview.listview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.asum.xlistview.listview.item.XBaseRecyclerFooterView;
import com.asum.xlistview.listview.item.XBaseRecyclerHeaderView;
import com.asum.xlistview.listview.item.XBaseRecyclerViewItem;

/**
 * @Author XJW
 * @CreateTime 2016/10/13
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    public enum Type {
        COLOR,
        RESOURCE_ID;
    }

    private Paint paint;
    private Drawable divider;
    private int dividerHeight = 2;
    private int orientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private boolean hideLastOne;
    private boolean haveHeader, haveFooter;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 2px高度的透明分割线
     *
     * @param context
     * @param orientation 列表方向
     */
    public SpaceItemDecoration(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            this.orientation = LinearLayoutManager.VERTICAL;
        } else {
            this.orientation = orientation;
        }
    }

    /**
     * 2px高度使用资源ID的分隔线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public SpaceItemDecoration(Context context, int orientation, int drawableId) {
        this(context, orientation);
        divider = ContextCompat.getDrawable(context, drawableId);
        dividerHeight = divider.getIntrinsicHeight();
    }

    /**
     * 自定义高度和颜色（资源ID）的分隔线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param resId         资源
     */
    public SpaceItemDecoration(Context context, int orientation, int dividerHeight, int resId, Type type) {
        this(context, orientation);
        this.dividerHeight = dividerHeight;

        if (type.equals(Type.COLOR)) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(resId);
        } else {
            divider = ContextCompat.getDrawable(context, resId);
        }
    }

    /**
     * 最后一条是否添加间距
     *
     * @param showBottom
     */
    public void setBottomEnable(boolean showBottom) {
        this.hideLastOne = showBottom;
    }

    public void setHaveHeader(boolean haveHeader) {
        this.haveHeader = haveHeader;
    }

    public void setHaveFooter(boolean haveFooter) {
        this.haveFooter = haveFooter;
    }

    //获取分割线尺寸
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        final int allCount = state.getItemCount();

        if (view instanceof XBaseRecyclerHeaderView && haveHeader) {
            outRect.set(0, 0, 0, 0);
        } else if (view instanceof XBaseRecyclerFooterView && haveFooter) {
            outRect.set(0, 0, 0, 0);
        } else {
            int itemId = ((XBaseRecyclerViewItem) view).getRecyclerId();

            if (haveHeader && itemId == allCount - 2 - (haveFooter ? 1 : 0) && !hideLastOne) {
                outRect.set(0, 0, 0, 0);
            } else if (!haveHeader && itemId == allCount - 1 && !hideLastOne) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, dividerHeight);
            }
        }
    }

    //绘制分割线
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int childSize = parent.getChildCount();
        final boolean lastViewIsOther, firstViewIsOther;
        if (childSize > 0) {
            final View lastView = parent.getChildAt(childSize - 1);
            final View firstView = parent.getChildAt(0);

            if (lastView instanceof XBaseRecyclerViewItem) {
                lastViewIsOther = false;
            } else {
                lastViewIsOther = true;
            }

            if (firstView instanceof XBaseRecyclerViewItem) {
                firstViewIsOther = false;
            } else {
                firstViewIsOther = true;
            }
        } else {
            lastViewIsOther = false;
            firstViewIsOther = false;
        }

        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent, childSize, lastViewIsOther, firstViewIsOther);
        } else {
            drawVertical(c, parent, childSize, lastViewIsOther);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent, int childSize, boolean lastViewIsOther, boolean firstViewIsOther) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();

        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom;

            if ((firstViewIsOther && i == 0) || (lastViewIsOther && !hideLastOne && i == childSize - 2 - (firstViewIsOther ? 1 : 0))) {
                bottom = top;
            } else {
                bottom = top + dividerHeight;
            }

            if (divider != null) {
//                divider.setBounds(left, top, right, bottom);
//                divider.draw(canvas);
            }
            if (paint != null) {
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent, int childSize, boolean lastViewIsOther) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();

        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + dividerHeight;

            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
            if (paint != null) {
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }
    }
}
