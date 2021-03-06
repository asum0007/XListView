package com.asum.xlistview.listview.listview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.asum.xlistview.listview.adapter.XListViewAdapter;
import com.asum.xlistview.listview.callback.OnXBaseRecyclerCallBack;
import com.asum.xlistview.listview.callback.OnXListViewCallBack;
import com.asum.xlistview.listview.item.XBaseRecyclerFooterView;
import com.asum.xlistview.listview.item.XBaseRecyclerHeaderView;

import java.util.ArrayList;

/**
 * 基于Material Design 以及 SwipeRefreshLayout + ListView 的可下拉和底部刷新 ListView
 *
 * @author Asum
 */
public class XListView extends SwipeRefreshLayout {
    protected Context context;
    protected CanMontiorListView listView;
    protected XListViewAdapter adapter;

    private Class<?> itemClass;
    private Class<?> footerClass, headClass;
    private XBaseRecyclerHeaderView headerView;
    private XBaseRecyclerFooterView footerView;
    private int space;
    private boolean scrollBarIsShow;
    protected boolean canPullUpToRefresh;

    private OnXBaseRecyclerCallBack itemCallBack;
    protected OnXListViewCallBack listViewCallBack;
    private CanMontiorListView.ListViewScrollChangeListener listViewScrollChangeListener;

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public XListView(Context context) {
        super(context);
        this.context = context;
    }

    public void setItemClass(Class<?> itemClass) {
        this.itemClass = itemClass;
    }

    public void changeItemClass(Class<?> itemClass) {
        this.itemClass = itemClass;
        if (adapter != null) {
            initializeAdapter();
            listView.setAdapter(adapter);
        }
    }

    public void setFooterClass(Class<?> footerClass) {
        this.footerClass = footerClass;
    }

    public void setHeaderClass(Class<?> headClass) {
        this.headClass = headClass;
    }

    public void setFooterView(XBaseRecyclerFooterView footerView) {
        this.footerView = footerView;
    }

    public void setHeaderView(XBaseRecyclerHeaderView headerView) {
        this.headerView = headerView;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setScrollBarEnable(boolean scrollBarIsShow) {
        this.scrollBarIsShow = scrollBarIsShow;
    }

    public void setItemCallBack(OnXBaseRecyclerCallBack itemCallBack) {
        this.itemCallBack = itemCallBack;
    }

    public void setListViewCallBack(OnXListViewCallBack listViewCallBack) {
        this.listViewCallBack = listViewCallBack;
    }

    public void setOnScrollListener(CanMontiorListView.ListViewScrollChangeListener listViewScrollChangeListener) {
        this.listViewScrollChangeListener = listViewScrollChangeListener;
    }

    public CanMontiorListView getListView() {
        return listView;
    }

    public void setPullDownRefreshEnable(boolean canPullDownToRefresh) {
        this.setEnabled(canPullDownToRefresh);
    }

    public void setPullUpRefreshEnable(boolean canPullUpToRefresh) {
        this.canPullUpToRefresh = canPullUpToRefresh;
    }

    public void initialize() {
        getWidgets();
        setWidgets();
        addListener();
    }

    public void showData(ArrayList<?> datas) {
        adapter.setSources(datas);
        adapter.notifyDataSetChanged();
    }

    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    public void removeByIndex(int index) {
        adapter.removeByIndex(index);
        adapter.notifyDataSetChanged();
    }

    public void removeByRange(int startIndex, int endIndex) {
        adapter.removeByRange(startIndex, endIndex);
        adapter.notifyDataSetChanged();
    }

    public void removeByIndexs(int... indexs) {
        adapter.removeByIndexs(indexs);
        adapter.notifyDataSetChanged();
    }

    public void add(int index, Object data) {
        adapter.add(index, data);
        adapter.notifyDataSetChanged();
    }

    public void add(Object data) {
        adapter.add(data);
        adapter.notifyDataSetChanged();
    }

    public void removeAll() {
        adapter.removeAll();
        adapter.notifyDataSetChanged();
    }

    private void getWidgets() {
        listView = new CanMontiorListView(context);
    }

    private void setWidgets() {
        initializeAdapter();

        this.addView(listView);
        if (footerClass != null) {
            try {
                XBaseRecyclerFooterView footerView = (XBaseRecyclerFooterView) footerClass.getConstructor(Context.class).newInstance(context);
                listView.addFooterView(footerView);
                footerView.initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (footerView != null) {
            listView.addFooterView(footerView);
        }

        if (headClass != null) {
            try {
                XBaseRecyclerHeaderView headView = (XBaseRecyclerHeaderView) headClass.getConstructor(Context.class).newInstance(context);
                listView.addHeaderView(headView);
                headView.initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (headerView != null) {
            listView.addHeaderView(headerView);
        }

        listView.setDivider(new ColorDrawable(0x00000000));
        listView.setDividerHeight((int) space);
        listView.setVerticalScrollBarEnabled(scrollBarIsShow);

        listView.setAdapter(adapter);
    }

    private void initializeAdapter() {
        adapter = new XListViewAdapter(context, new OnXBaseRecyclerCallBack() {
            public void onLongClick(Object data, int flag) {
                if (itemCallBack != null) {
                    itemCallBack.onLongClick(data, flag);
                }
            }

            public void onClick(Object data, int flag) {
                if (itemCallBack != null) {
                    itemCallBack.onClick(data, flag);
                }
            }
        });
        adapter.setItemClass(itemClass);
    }

    private void addListener() {
        listView.setOnScrollListener(new OnScrollListener() {
            // 0：停止滚动，1：正在滚动，2：手指有抛动作
            public void onScrollStateChanged(AbsListView arg0, int type) {
                if (type == 0) {
                    int itemCount = adapter.getCount() + listView.getFooterViewsCount() + listView.getHeaderViewsCount();
                    if (listView.getLastVisiblePosition() == itemCount - 1) {
                        if (canPullUpToRefresh) {
                            if (listViewCallBack != null && !XListView.this.isRefreshing()) {
                                XListView.this.setRefreshing(true);
                                listViewCallBack.pullUpRefresh();
                            }
                        }
                    }
                }
            }

            public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listViewScrollChangeListener != null) {
                    View c = arg0.getChildAt(0);
                    if (c == null) {
                        return;
                    }
                    int firstVisiblePosition = arg0.getFirstVisiblePosition();
                    int top = c.getTop();
                    listViewScrollChangeListener.onScrollY(-top + firstVisiblePosition * c.getHeight());
                }
            }
        });

        listView.setOnListViewScrollChangeListener(new CanMontiorListView.ListViewScrollChangeListener() {
            public void onScrollChanged(CanMontiorListView scrollView, int x, int y, int oldx, int oldy) {
                if (listViewScrollChangeListener != null) {
                    listViewScrollChangeListener.onScrollChanged(scrollView, x, y, oldx, oldy);
                }
            }

            public void onScrollY(int y) {

            }
        });

        this.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                if (listViewCallBack != null) {
                    listViewCallBack.pullDownRefresh();
                }
            }
        });
    }
}
