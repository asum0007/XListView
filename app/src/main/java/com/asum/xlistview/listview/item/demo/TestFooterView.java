package com.asum.xlistview.listview.item.demo;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.asum.xlistview.listview.item.XBaseRecyclerFooterView;

import java.util.ArrayList;

public class TestFooterView extends XBaseRecyclerFooterView {
    private Context context;
    private TextView textView;

    public TestFooterView(Context context) {
        super(context);
        this.context = context;
    }

    public void initialize() {
        textView = new TextView(context);
        this.addView(textView);
        textView.setText("加载更多");
        textView.setHeight(100);
        textView.setBackgroundColor(Color.RED);
    }

    public void showData(ArrayList<?> sources) {
    }
}
