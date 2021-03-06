package com.asum.xlistview.listview.listview;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by Asum on 2016/4/21 0021.
 */
public class CanMontiorListView extends ListView {
    private ListViewScrollChangeListener listViewScrollChangeListener;

    public CanMontiorListView(Context context) {
        super(context);
    }
//
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (listViewScrollChangeListener != null) {
//            listViewScrollChangeListener.onScrollChanged(CanMontiorListView.this, l, t, oldl, oldt);
//        }
//    }

//    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        if (listViewScrollChangeListener != null) {
//            listViewScrollChangeListener.onScrollChanged(CanMontiorListView.this, scrollX, scrollY, 0, 0);
//        }
//    }

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        Log.e("XJW", "x" + x + "y" + y);
        if (listViewScrollChangeListener != null) {
            listViewScrollChangeListener.onScrollChanged(CanMontiorListView.this, x, y, 0, 0);
        }
    }

    //    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
//                                  boolean clampedY) {
//        this.scrollBy(0, delY / 2);
//        if (action_up) {
//            this.scrollTo(0, 0);
//        }
//    }

    public void setOnListViewScrollChangeListener(final ListViewScrollChangeListener listViewScrollChangeListener) {
        this.listViewScrollChangeListener = listViewScrollChangeListener;
    }

//    public int getScrollH() {
//        View c = this.getChildAt(0);
//        if (c == null) {
//            return 0;
//        }
//        int firstVisiblePosition = this.getFirstVisiblePosition();
//        int top = c.getTop();
//        return -top + firstVisiblePosition * c.getHeight();
//    }

    public interface ListViewScrollChangeListener {
        void onScrollChanged(CanMontiorListView scrollView, int x, int y, int oldx, int oldy);

        void onScrollY(int y);
    }
}
