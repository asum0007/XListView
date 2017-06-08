package com.asum.xlistview.listview.item;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Recycler顶部基类
 *
 * @Author XJW
 * @CreateTime 2016/10/16
 */
public abstract class XBaseRecyclerHeaderView extends RelativeLayout {
    public XBaseRecyclerHeaderView(Context context) {
        super(context);
    }

    public abstract void initialize();

    public void showAgain() {
    }
}
