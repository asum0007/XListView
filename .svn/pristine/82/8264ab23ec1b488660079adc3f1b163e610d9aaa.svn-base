package com.asum.xlistview.listview;

import android.app.Activity;
import android.os.Bundle;

import com.asum.xlistview.R;
import com.asum.xlistview.listview.item.demo.TestFooterView;
import com.asum.xlistview.listview.item.demo.TestRecylerView;
import com.asum.xlistview.listview.listview.XListView;

public class MainActivity extends Activity {
    private XListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (XListView) findViewById(R.id.activity_test_listview);
        listView.setItemClass(TestRecylerView.class);
        listView.setFooterClass(TestFooterView.class);
        listView.setSpace(25);
        listView.setScrollBarEnable(false);
        listView.setPullDownRefreshEnable(true);
        listView.setPullUpRefreshEnable(false);
        listView.initialize();
    }
}
