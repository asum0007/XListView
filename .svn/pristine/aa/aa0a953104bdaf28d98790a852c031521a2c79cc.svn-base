package com.asum.xlistview.listview.item.demo;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.asum.xlistview.listview.item.XBaseRecyclerHeaderView;

import java.util.ArrayList;

/**
 * @Author XJW
 * @CreateTime 2016/10/16
 */

public class TestHeaderView extends XBaseRecyclerHeaderView {
    private Context context;
    private TextView textView;

    public TestHeaderView(Context context) {
        super(context);
        this.context = context;
    }

    public void initialize() {
        textView = new TextView(context);
        this.addView(textView);
        textView.setText("顶部");
        textView.setHeight(100);
        textView.setBackgroundColor(Color.RED);
    }

    public void showData(ArrayList<?> sources) {
    }
}
