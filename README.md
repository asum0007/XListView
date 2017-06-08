# XListView
XListView

XListView是基于SwipeRefreshLayout和RecyclerView实现的免Adapter，可下拉刷新和上拉加载更多的"ListView"

声明
```Java
public XRecyclerView recyclerView;
```

是否需要下拉刷新（默认false）
```Java
recyclerView.setPullDownRefreshEnable(false);
```

是否需要触底加载更多（默认false）
```Java
recyclerView.setPullUpRefreshEnable(false);
```

每一条Item之间的间距（默认0）
```Java
recyclerView.setSpace(space);
```

是否显示最后一条的间隔（默认false）
```Java
recyclerView.setBottomSpaceEnable(false);
```

隐藏滚动条（默认true）
```Java
recyclerView.setScrollBarEnable(false);
```

布局方式（必须调用的方法）
```Java
recyclerView.setLayoutManager(new LinearLayoutManager(context));
```

设置条目类（必须调用的方法）
```Java
recyclerView.setItemClass(TestRecyclerViewItem.class);
```

更换条目类
```Java
recyclerView.changeItemClass(Test2RecyclerViewItem.class);
```

设置顶部
```Java
recyclerView.setHeaderView(new TestHeaderView(context));
```

设置底部
```Java
recyclerView.setFooterView(new TestFooterView(context));
```

设置滚动时的动画（来源地都实现，则为两者结合的动画）
```Java
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
```

下拉上拉监听
```Java
recyclerView.setRecyclerViewCallBack(new OnXListViewCallBack() {
    public void pullUpRefresh() {
        Log.i("XJW","下拉刷新");
    }

    public void pullDownRefresh() {
        Log.i("XJW","上拉加载更多");
    }
});
```

条目点击监听
```Java
recyclerView.setItemCallBack(new OnXBaseRecyclerCallBack() {
    public void onClick(Object data, int flag) {
        Log.i("XJW", "点击" + flag);
    }

    public void onLongClick(Object data, int flag) {
        Log.i("XJW", "长点击" + flag);
    }
});
```

滚动监听
```Java
recyclerView.setScrolledCallBack(new OnScrolledCallBack() {
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

    }

    public void onScrollStateChanged(RecyclerView recyclerView, int type) {

    }
});
```

初始化（必须调用的方法）
```Java
recyclerView.initialize();
```

顶部View
```Java
public class TestHeaderView extends XBaseRecyclerHeaderView{
    private TextView testTextView;
    
    public TestHeaderView(Context context) {
        super(context);
    }
    
    public void initialize() {
        testTextView = new TextView(context);
        this.addView(testTextView);
        testTextView.setText("这是顶部");
    }
}
```

底部View
```Java
public class TestFooterView extends XBaseRecyclerFooterView{
    private TextView testTextView;
    
    public TestFooterView(Context context) {
        super(context);
    }
    
    public void initialize() {
        testTextView = new TextView(context);
        this.addView(testTextView);
        testTextView.setText("这是底部");
    }
}
```

展示数据
```Java
ArrayList<String> datas = new ArrayList<>();
for (int i = 0; i < 4; i++) {
    datas.add("数据" + i);
}
recyclerView.showData(datas);
```

添加数据
```Java
recyclerView.addData("添加的数据", 1);
```

移除数据
```Java
recyclerView.delData(1);
```

内容条目
```Java
public class TestRecyclerViewItem extends XBaseRecyclerViewItem {
    private Context context;
    private TextView testTextView;
    
    public TestRecyclerViewItem(Context context) {
        super(context);
        this.context = context;
    }

    public void initialize() {
        testTextView = new TextView(context);
        this.addView(testTextView);
      
        testTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                beClick("点击传递此数据", 0);
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
		beLongClick("长点击传递此数据", 1);
		return true;
	    }
	});
    }
    
    public void showData(Object source) {
      testTextView.setText((String)source);
    }
}
```
