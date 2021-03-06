package com.asum.xlistview.listview.callback;

import android.support.v7.widget.RecyclerView;

/**
 * 滚动监听
 *
 * @Author XJW
 * @CreateTime 2016/10/17
 */
public interface OnScrolledCallBack {
    void onScrolled(RecyclerView recyclerView, int dx, int dy);

    void onScrollStateChanged(RecyclerView recyclerView, int type);
}
