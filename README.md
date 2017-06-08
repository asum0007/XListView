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

这是顶部
```Java
recyclerView.setHeaderView(new TestHeaderView(context));
```

设置底部
```Java
recyclerView.setFooterView(new TestFooterView(context));
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
    }
    
    public void showData(Object source) {
      testTextView.setText((String)source);
    }
}
```
