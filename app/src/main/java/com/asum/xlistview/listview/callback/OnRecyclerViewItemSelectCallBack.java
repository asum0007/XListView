package com.asum.xlistview.listview.callback;

import com.asum.xlistview.listview.item.XBaseRecyclerViewItem;

/**
 * @Author XJW
 * @CreateTime 2016/10/17
 */

public interface OnRecyclerViewItemSelectCallBack {
    int getItemViewType(int position);

    XBaseRecyclerViewItem getView(int viewType);

    /**
     * 返回空则使用最初的数据
     *
     * @param position
     * @return
     */
    Object getData(int position);
}
