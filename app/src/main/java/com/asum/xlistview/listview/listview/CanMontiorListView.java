package com.asum.xlistview.listview.listview;

import android.content.Context;
import android.widget.ListView;

/**
 * Created by Asum on 2016/4/21 0021.
 */
public class CanMontiorListView extends ListView {
    private ListViewScrollChangeListener listViewScrollChangeListener;

    public CanMontiorListView(Context context) {
        super(context);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listViewScrollChangeListener != null) {
            listViewScrollChangeListener.onScrollChanged(CanMontiorListView.this, l, t, oldl, oldt);
        }
    }

    public void setOnListViewScrollChangeListener(ListViewScrollChangeListener listViewScrollChangeListener) {
        this.listViewScrollChangeListener = listViewScrollChangeListener;
    }

    public interface ListViewScrollChangeListener {
        void onScrollChanged(CanMontiorListView scrollView, int x, int y, int oldx, int oldy);
    }
}
