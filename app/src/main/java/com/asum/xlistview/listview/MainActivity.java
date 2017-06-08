package com.asum.xlistview.listview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.asum.xlistview.R;
import com.asum.xlistview.listview.callback.OnAdapterAnimCallBack;
import com.asum.xlistview.listview.callback.OnRecyclerViewItemSelectCallBack;
import com.asum.xlistview.listview.callback.OnScrolledCallBack;
import com.asum.xlistview.listview.callback.OnXBaseRecyclerCallBack;
import com.asum.xlistview.listview.callback.OnXListViewCallBack;
import com.asum.xlistview.listview.item.XBaseRecyclerViewItem;
import com.asum.xlistview.listview.item.demo.TestFooterView;
import com.asum.xlistview.listview.item.demo.TestHeaderView;
import com.asum.xlistview.listview.item.demo.TestRecylerView;
import com.asum.xlistview.listview.listview.XListView;
import com.asum.xlistview.listview.recycleview.XRecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private XListView listView;
    private XRecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datas.add(i);
        }

        listView = (XListView) findViewById(R.id.activity_test_listview);
        listView.setItemClass(TestRecylerView.class);
        listView.setFooterClass(TestFooterView.class);
        listView.setSpace(25);
        listView.setScrollBarEnable(false);
        listView.setPullDownRefreshEnable(true);
        listView.setPullUpRefreshEnable(false);
        listView.initialize();
        listView.setVisibility(View.GONE);

        //底部
        TestFooterView testFooterView = new TestFooterView(this);
        testFooterView.initialize();

        //头部
        TestHeaderView testHeadView = new TestHeaderView(this);
        testHeadView.initialize();

        recyclerView = (XRecyclerView) findViewById(R.id.activity_test_recyclerview);

        //是否显示拖动条
        recyclerView.setScrollBarEnable(false);

        //是否可以下拉刷新
        recyclerView.setPullDownRefreshEnable(false);

        //是否可以上拉刷新
        recyclerView.setPullUpRefreshEnable(false);

        //设置内容布局，线性横向或纵向，网格布局，瀑布流布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //条目类
        recyclerView.setItemClass(TestRecylerView.class);

        //设置头部
        recyclerView.setHeaderView(testHeadView);

        //设置底部
//        recyclerView.setFooterView(testFooterView);

        //设置间隔
        recyclerView.setSpace(25);

        //是否显示最后一条的间隔
        recyclerView.setBottomSpaceEnable(false);

        //设置添加删除动画
//        recyclerView.setItemAnimator(new SlideInDownAnimator(new DecelerateInterpolator()));

        //设置滚动时的动画（来源地都实现，则为两者结合的动画）
        recyclerView.setAdapterAnimCallBack(new OnAdapterAnimCallBack() {
            //动画来自资源文件
            public int[] fromXmls(View view) {
                return null;
            }

            //动画来自Animator集合
            public Animator[] fromAnimators(View view) {
                Animator[] animators = {
                        ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f),
                        ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f)
                };
                return animators;
            }

            //是否只显示一次动画
            public boolean firstOnly() {
                return true;
            }
        });

        //初始化
        recyclerView.initialize();

        //展示数据
        recyclerView.showData(datas);

        //下拉上拉监听
        recyclerView.setRecyclerViewCallBack(new OnXListViewCallBack() {
            public void pullUpRefresh() {

            }

            public void pullDownRefresh() {

            }
        });

        //实时改变显示的条目
        recyclerView.setOnRecyclerViewItemSelectCallBack(new OnRecyclerViewItemSelectCallBack() {
            public int getItemViewType(int position) {
                return 0;
            }

            public XBaseRecyclerViewItem getView(int viewType) {
                return null;
            }

            public Object getData(int position) {
                return null;
            }
        });

        //条目点击监听
        recyclerView.setItemCallBack(new OnXBaseRecyclerCallBack() {
            public void onClick(Object data, int flag) {
                Log.i("XJW", "点击" + flag);
            }

            public void onLongClick(Object data, int flag) {
                Log.i("XJW", "长点击" + flag);
            }
        });

        //滚动监听
        recyclerView.setScrolledCallBack(new OnScrolledCallBack() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            public void onScrollStateChanged(RecyclerView recyclerView, int type) {

            }
        });

        //添加数据
        recyclerView.addData(999, 1);

        //移除数据
        recyclerView.delData(1);

        findViewById(R.id.activity_add).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recyclerView.addData(999, 1);
                recyclerView.addData(999, 3);
                recyclerView.addData(999, 5);
            }
        });

        findViewById(R.id.activity_del).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recyclerView.delData(1);
            }
        });
    }
}
