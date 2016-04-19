package com.asum.xlistview.listview.item;

import android.content.Context;
import android.widget.RelativeLayout;

public abstract class XBaseRecyclerFooterView extends RelativeLayout {
	public XBaseRecyclerFooterView(Context context) {
		super(context);
	}

	public abstract void initialize();
}
