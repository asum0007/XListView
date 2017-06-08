package com.asum.xlistview.listview.item;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Recycler底部基类
 *
 * @Author XJW
 * @CreateTime 2016/10/16
 */
public abstract class XBaseRecyclerFooterView extends RelativeLayout {
    public XBaseRecyclerFooterView(Context context) {
        super(context);
    }

    public abstract void initialize();

    public void showAgain() {
    }
}
