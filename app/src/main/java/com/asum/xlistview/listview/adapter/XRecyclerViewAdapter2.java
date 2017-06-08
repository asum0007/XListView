package com.asum.xlistview.listview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.asum.xlistview.listview.callback.OnRecyclerViewItemSelectCallBack;
import com.asum.xlistview.listview.callback.OnXBaseRecyclerCallBack;
import com.asum.xlistview.listview.item.XBaseRecyclerFooterView;
import com.asum.xlistview.listview.item.XBaseRecyclerHeaderView;
import com.asum.xlistview.listview.item.XBaseRecyclerViewItem;

import java.util.ArrayList;

/**
 * @Author XJW
 * @CreateTime 2017/5/31
 */
public class XRecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int HEAD_VIEW = 100001;
    public final static int FOOTER_VIEW = 100002;
    public final static int NORMAL_VIEW = 100003;

    private Context context;
    private OnXBaseRecyclerCallBack callBack;
    private OnRecyclerViewItemSelectCallBack viewItemSelectCallBack;

    private XBaseRecyclerHeaderView headView;
    private XBaseRecyclerFooterView footerView;

    private ArrayList datas;
    private Class itemClass;

    public XRecyclerViewAdapter2(Context context, OnXBaseRecyclerCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        datas = new ArrayList<>();
    }

    public void setHeadView(XBaseRecyclerHeaderView headView) {
        this.headView = headView;
    }

    public void setFooterView(XBaseRecyclerFooterView footerView) {
        this.footerView = footerView;
    }

    public int getItemCount() {
        return datas.size() + (headView == null ? 0 : 1) + (footerView == null ? 0 : 1);
    }

    public void removeData(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, Object data) {
        datas.add(position, data);
        notifyItemInserted(position);
    }

    public void setViewItemSelectCallBack(OnRecyclerViewItemSelectCallBack viewItemSelectCallBack) {
        this.viewItemSelectCallBack = viewItemSelectCallBack;
    }

    public void setItemClass(Class itemClass) {
        this.itemClass = itemClass;
    }

    public void setSources(ArrayList datas) {
        this.datas = datas;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_VIEW) {
            return new ViewHolder(headView);
        } else if (viewType == FOOTER_VIEW) {
            return new ViewHolder(footerView);
        } else {
            if (viewItemSelectCallBack == null) {
                XBaseRecyclerViewItem item = null;
                try {
                    item = (XBaseRecyclerViewItem) itemClass.getConstructor(Context.class).newInstance(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                item.initialize();

                return new ViewHolder(item);
            } else {
                return new ViewHolder(viewItemSelectCallBack.getView(viewType));
            }
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        XRecyclerViewAdapter2.ViewHolder inheritHolder = (ViewHolder) holder;

        if (headView != null && position == 0) {
            inheritHolder.headerView.showAgain();
        } else if (headView == null && footerView != null && position == datas.size()) {
            inheritHolder.footerView.showAgain();
        } else if (headView != null && footerView != null && position == datas.size() + 1) {
            inheritHolder.footerView.showAgain();
        } else {
            position = position - ((headView == null) ? 0 : 1);
            inheritHolder.item.setRecyclerId(position);

            if (viewItemSelectCallBack == null) {
                inheritHolder.item.showData(datas.get(position));
            } else {
                Object data = viewItemSelectCallBack.getData(position);
                inheritHolder.item.showData(data == null ? datas.get(position) : data);
            }

            inheritHolder.item.setCallBack(new OnXBaseRecyclerCallBack() {
                public void onLongClick(Object data, int flag) {
                    if (callBack != null) {
                        callBack.onLongClick(data, flag);
                    }
                }

                public void onClick(Object data, int flag) {
                    if (callBack != null) {
                        callBack.onClick(data, flag);
                    }
                }
            });
        }
    }

    public int getItemViewType(int position) {
        if (viewItemSelectCallBack == null) {
            if (headView != null && position == 0) {
                return HEAD_VIEW;
            } else if (headView == null && footerView != null && position == datas.size()) {
                return FOOTER_VIEW;
            } else if (headView != null && footerView != null && position == datas.size() + 1) {
                return FOOTER_VIEW;
            } else {
                return NORMAL_VIEW;
            }
        } else {
            return viewItemSelectCallBack.getItemViewType(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private XBaseRecyclerViewItem item;
        private XBaseRecyclerHeaderView headerView;
        private XBaseRecyclerFooterView footerView;

        public ViewHolder(XBaseRecyclerViewItem itemView) {
            super(itemView);
            item = itemView;
        }

        public ViewHolder(XBaseRecyclerHeaderView headerView) {
            super(headerView);
            this.headerView = headerView;
        }

        public ViewHolder(XBaseRecyclerFooterView footerView) {
            super(footerView);
            this.footerView = footerView;
        }
    }
}
