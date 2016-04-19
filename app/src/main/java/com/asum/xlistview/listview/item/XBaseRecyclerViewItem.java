package com.asum.xlistview.listview.item;

import android.content.Context;
import android.widget.RelativeLayout;

import com.asum.xlistview.listview.callback.OnXBaseRecyclerCallBack;

/**
 * XListView的子项必须继承的基类
 * 
 * @author Asum
 * 
 */
public abstract class XBaseRecyclerViewItem extends RelativeLayout {
	protected int recyclerId;
	private OnXBaseRecyclerCallBack callBack;

	public XBaseRecyclerViewItem(Context context) {
		super(context);
	}

	/**
	 * 设置当前条目对应Listview的位置
	 * 
	 * @param recyclerId
	 */
	public void setRecyclerId(int recyclerId) {
		this.recyclerId = recyclerId;
	}

	/**
	 * 用于和XListView通信，不要覆盖此方法
	 * 
	 * @param callBack
	 */
	public void setCallBack(OnXBaseRecyclerCallBack callBack) {
		this.callBack = callBack;
	}

	/**
	 * 子项控件的响应事件如果需要通知到XListView，在监听点击事件的地方调用此方法
	 * 
	 * @param data
	 * @param flag
	 */
	protected void beClick(Object data, int flag) {
		if (callBack != null) {
			callBack.onClick(data, flag);
		}
	}

	/**
	 * 子项控件的响应事件如果需要通知到XListView，在监听点击事件的地方调用此方法
	 * 
	 * @param data
	 * @param flag
	 */
	protected void beLongClick(Object data, int flag) {
		if (callBack != null) {
			callBack.onLongClick(data, flag);
		}
	}

	/**
	 * 初始化
	 */
	public abstract void initialize();

	/**
	 * 展示数据
	 * 
	 * @param source
	 *            数据源
	 */
	public abstract void showData(Object source);
}
