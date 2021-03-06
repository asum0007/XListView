package com.asum.xlistview.listview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.asum.xlistview.listview.callback.OnXBaseRecyclerCallBack;
import com.asum.xlistview.listview.item.XBaseRecyclerViewItem;

import java.util.ArrayList;
import java.util.Arrays;

public class XListViewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<?> datas;
	private Class<?> itemClass;
	private OnXBaseRecyclerCallBack callBack;

	public XListViewAdapter(Context context, OnXBaseRecyclerCallBack callBack) {
		this.context = context;
		this.callBack = callBack;
		datas = new ArrayList<>();
	}

	public void setItemClass(Class<?> itemClass) {
		this.itemClass = itemClass;
	}

	public void setSources(ArrayList<?> datas) {
		this.datas = datas;
	}

	public void removeByIndex(int index) {
		datas.remove(index);
	}

	public void removeByRange(int startIndex, int endIndex) {
		for (int i = 0; i < endIndex - startIndex; i++) {
			datas.remove(startIndex);
		}
	}

	public void removeByIndexs(int[] indexs) {
		Arrays.sort(indexs);
		for (int i = indexs.length - 1; i >= 0; i--) {
			datas.remove(indexs[i]);
		}
	}

	public void add(int index, Object data) {
		ArrayList<Object> newDatas = new ArrayList<Object>();
		for (int i = 0; i < datas.size(); i++) {
			newDatas.add(datas.get(i));
		}
		newDatas.add(index, data);
		datas = newDatas;
	}

	public void add(Object data) {
		add(datas.size(), data);
	}

	public void removeAll() {
		datas.clear();
	}

	public int getCount() {
		return datas.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			return getView(position);
		}

		((XBaseRecyclerViewItem) convertView).setCallBack(new OnXBaseRecyclerCallBack() {
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

		((XBaseRecyclerViewItem) convertView).setRecyclerId(position);
		((XBaseRecyclerViewItem) convertView).showData(datas.get(position));
		return convertView;
	}

	private View getView(int position) {
		XBaseRecyclerViewItem item = null;
		try {
			item = (XBaseRecyclerViewItem) itemClass.getConstructor(Context.class).newInstance(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		item.setRecyclerId(position);
		item.initialize();

		item.setCallBack(new OnXBaseRecyclerCallBack() {
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

		item.showData(datas.get(position));
		return item;
	}
}
