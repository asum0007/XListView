package com.asum.xlistview.listview.item.demo;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.asum.xlistview.listview.item.XBaseRecyclerViewItem;

public class TestRecylerView extends XBaseRecyclerViewItem {
	private Context context;
	private TextView textView;

	public TestRecylerView(Context context) {
		super(context);
		this.context = context;
	}

	public void initialize() {
		this.setBackgroundColor(Color.WHITE);
		textView = new TextView(context);
		this.addView(textView);
		textView.setTextColor(Color.BLACK);

		textView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				beClick("点击", recyclerId);
			}
		});

		textView.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				beLongClick("长点击", recyclerId);
				return true;
			}
		});
	}

	public void showData(Object source) {
		textView.setText("数据：" + source);
	}
}
