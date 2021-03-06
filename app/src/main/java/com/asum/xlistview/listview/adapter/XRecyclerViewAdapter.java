package com.asum.xlistview.listview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.asum.xlistview.listview.callback.OnRecyclerViewItemSelectCallBack;
import com.asum.xlistview.listview.callback.OnXBaseRecyclerCallBack;
import com.asum.xlistview.listview.item.XBaseRecyclerViewItem;

import java.util.ArrayList;

/**
 * @Author XJW
 * @CreateTime 2016/10/11
 */
public class XRecyclerViewAdapter extends RecyclerView.Adapter<XRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private OnXBaseRecyclerCallBack callBack;
    private OnRecyclerViewItemSelectCallBack viewItemSelectCallBack;

    private ArrayList datas;
    private Class itemClass;

    public XRecyclerViewAdapter(Context context, OnXBaseRecyclerCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        datas = new ArrayList<>();
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

    public int getItemViewType(int position) {
        if (viewItemSelectCallBack == null) {
            return super.getItemViewType(position);
        } else {
            return viewItemSelectCallBack.getItemViewType(position);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item.setRecyclerId(position);

        if (viewItemSelectCallBack == null) {
            holder.item.showData(datas.get(position));
        } else {
            Object data = viewItemSelectCallBack.getData(position);
            holder.item.showData(data == null ? datas.get(position) : data);
        }

        holder.item.setCallBack(new OnXBaseRecyclerCallBack() {
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

    public int getItemCount() {
        return datas.size();
    }

    public void removeData(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, Object data) {
        datas.add(position, data);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private XBaseRecyclerViewItem item;

        public ViewHolder(XBaseRecyclerViewItem itemView) {
            super(itemView);
            item = itemView;
        }
    }
}
