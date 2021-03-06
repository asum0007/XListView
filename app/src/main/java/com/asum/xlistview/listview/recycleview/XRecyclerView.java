package com.asum.xlistview.listview.recycleview;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.asum.xlistview.listview.adapter.XAnimationAdapter;
import com.asum.xlistview.listview.adapter.XRecyclerViewAdapter2;
import com.asum.xlistview.listview.callback.OnAdapterAnimCallBack;
import com.asum.xlistview.listview.callback.OnRecyclerViewItemSelectCallBack;
import com.asum.xlistview.listview.callback.OnScrolledCallBack;
import com.asum.xlistview.listview.callback.OnXBaseRecyclerCallBack;
import com.asum.xlistview.listview.callback.OnXListViewCallBack;
import com.asum.xlistview.listview.decoration.SpaceItemDecoration;
import com.asum.xlistview.listview.item.XBaseRecyclerFooterView;
import com.asum.xlistview.listview.item.XBaseRecyclerHeaderView;

import java.util.ArrayList;

/**
 * @Author XJW
 * @CreateTime 2016/10/11
 */
public class XRecyclerView extends SwipeRefreshLayout {
    private Context context;
    private XRecyclerViewAdapter2 adapter;
    private XAnimationAdapter animAdapter;
    private ScrollRecyclerView recyclerView;
    private int totalY, totalX;

    private Class<?> itemClass;
    protected boolean canPullUpToRefresh;
    private boolean scrollBarIsShow;
    private double space;
    private boolean showBottomSpace;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemAnimator animator;

    private OnXBaseRecyclerCallBack itemCallBack;
    protected OnXListViewCallBack listViewCallBack;
    private OnAdapterAnimCallBack adapterAnimCallBack;
    private OnScrolledCallBack scrolledCallBack;
    private OnRecyclerViewItemSelectCallBack viewItemSelectCallBack;

    private XBaseRecyclerHeaderView headerView;
    private XBaseRecyclerFooterView footerView;

    public XRecyclerView(Context context) {
        super(context);
        this.context = context;
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setItemClass(Class<?> itemClass) {
        this.itemClass = itemClass;
    }

    public void changeItemClass(Class<?> itemClass) {
        this.itemClass = itemClass;
    }

    public void setPullDownRefreshEnable(boolean canPullDownToRefresh) {
        this.setEnabled(canPullDownToRefresh);
    }

    public void setPullUpRefreshEnable(boolean canPullUpToRefresh) {
        this.canPullUpToRefresh = canPullUpToRefresh;
    }

    public void setItemCallBack(OnXBaseRecyclerCallBack itemCallBack) {
        this.itemCallBack = itemCallBack;
    }

    public void setRecyclerViewCallBack(OnXListViewCallBack listViewCallBack) {
        this.listViewCallBack = listViewCallBack;
    }

    public void setScrolledCallBack(OnScrolledCallBack scrolledCallBack) {
        this.scrolledCallBack = scrolledCallBack;
    }

    public void setAdapterAnimCallBack(OnAdapterAnimCallBack adapterAnimCallBack) {
        this.adapterAnimCallBack = adapterAnimCallBack;
    }

    public void setOnRecyclerViewItemSelectCallBack(OnRecyclerViewItemSelectCallBack viewItemSelectCallBack) {
        this.viewItemSelectCallBack = viewItemSelectCallBack;
        if (adapter != null) {
            adapter.setViewItemSelectCallBack(viewItemSelectCallBack);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setFooterView(XBaseRecyclerFooterView footerView) {
        this.footerView = footerView;
    }

    public void setHeaderView(XBaseRecyclerHeaderView headerView) {
        this.headerView = headerView;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public void setBottomSpaceEnable(boolean showBottomSpace) {
        this.showBottomSpace = showBottomSpace;
    }

    public void setScrollBarEnable(boolean scrollBarIsShow) {
        this.scrollBarIsShow = scrollBarIsShow;
    }

    public void initialize() {
        getWidgets();
        setWidgets();
        addListener();
    }

    public void initialize(XRecyclerViewAdapter2 adapter) {
        this.adapter = adapter;
        initialize();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void showData(ArrayList<?> datas) {
        adapter.setSources(datas);
        animAdapter.notifyDataSetChanged();
    }

    public void changeData(ArrayList<?> datas) {
        adapter.setSources(datas);
        animAdapter.notifyDataSetChanged();
    }

    public void changeData(int position) {
        animAdapter.notifyItemChanged(position);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        this.animator = animator;
    }

    public void addData(Object data, int index) {
        adapter.addItem(index, data);
    }

    public void delData(int index) {
        adapter.removeData(index);
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
        totalX = 0;
        totalY = 0;
    }

    private void getWidgets() {
        recyclerView = new ScrollRecyclerView(getContext());
    }

    private void setWidgets() {
        this.addView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(scrollBarIsShow);

        initializeAdapter();
        recyclerView.setAdapter(animAdapter);

        if (animator != null) {
            recyclerView.setItemAnimator(animator);
        }

        adapter.setHeadView(headerView);
        adapter.setFooterView(footerView);

        int direction = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            direction = ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            direction = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof GridLayoutManager) {
            direction = ((GridLayoutManager) layoutManager).getOrientation();
        }

        SpaceItemDecoration dec = new SpaceItemDecoration(context, direction, (int) space, Color.argb(0, 0, 0, 0), SpaceItemDecoration.Type.COLOR);
        dec.setBottomEnable(showBottomSpace);
        dec.setHaveFooter(footerView != null);
        dec.setHaveHeader(headerView != null);

        recyclerView.addItemDecoration(dec);
    }

    private void addListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            // 0：停止滚动，1：正在滚动，2：手指有抛动作
            public void onScrollStateChanged(RecyclerView recyclerView, int type) {
                super.onScrollStateChanged(recyclerView, type);
                if (type == 0) {
                    int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();

                    if (lastVisibleItem == totalItemCount - 1) {
                        if (canPullUpToRefresh) {
                            if (listViewCallBack != null && !XRecyclerView.this.isRefreshing()) {
                                XRecyclerView.this.setRefreshing(true);
                                listViewCallBack.pullUpRefresh();
                            }
                        }
                    }

                    View topView = recyclerView.getLayoutManager().getChildAt(0);//获取可视的第一个view
                    if (topView == null) {
                        return;
                    }
                    int lastTopOffset = topView.getTop();//获取与该view的顶部的偏移量
                    int lastLeftOffset = topView.getLeft();
                    int lastPosition = recyclerView.getLayoutManager().getPosition(topView);  //得到该View的数组位置

                    if (lastPosition == 0) {
                        if (-lastTopOffset < totalY) {
                            totalY = 0;
                            if (scrolledCallBack != null) {
                                scrolledCallBack.onScrolled(recyclerView, totalX, totalY);
                            }
                        }
                    }

                    if (lastPosition == 0) {
                        if (-lastLeftOffset < totalX) {
                            totalX = 0;
                            if (scrolledCallBack != null) {
                                scrolledCallBack.onScrolled(recyclerView, totalX, totalY);
                            }
                        }
                    }
                }


                if (scrolledCallBack != null) {
                    scrolledCallBack.onScrollStateChanged(recyclerView, type);
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalY += dy;
                totalX += dx;
                if (totalY < 0) {
                    totalY = 0;
                }
                if (totalX < 0) {
                    totalX = 0;
                }
                if (scrolledCallBack != null) {
                    scrolledCallBack.onScrolled(recyclerView, totalX, totalY);
                }
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

    private void initializeAdapter() {
        if (adapter == null) {
            adapter = new XRecyclerViewAdapter2(context, new OnXBaseRecyclerCallBack() {
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
        }
        adapter.setViewItemSelectCallBack(viewItemSelectCallBack);
        adapter.setItemClass(itemClass);

        animAdapter = new XAnimationAdapter(adapter, new XAnimationAdapter.XAnimAdapterCallBack() {
            public int[] fromXmls(View view) {
                if (adapterAnimCallBack != null) {
                    return adapterAnimCallBack.fromXmls(view);
                } else {
                    return null;
                }
            }

            public Animator[] fromAnimators(View view) {
                if (adapterAnimCallBack != null) {
                    return adapterAnimCallBack.fromAnimators(view);
                } else {
                    return null;
                }
            }

            public boolean firstOnly() {
                if (adapterAnimCallBack != null) {
                    return adapterAnimCallBack.firstOnly();
                } else {
                    return true;
                }
            }
        });
    }
}
