# XListView
XListView

public XRecyclerView recyclerView;

//是否需要下拉刷新（默认false）
recyclerView.setPullDownRefreshEnable(false);

//是否需要触底加载更多（默认false）
recyclerView.setPullUpRefreshEnable(false);

//每一条Item之间的间距（默认0）
recyclerView.setSpace(space);

//隐藏滚动条（默认true）
recyclerView.setScrollBarEnable(false);

//布局方式（必须调用的方法）
recyclerView.setLayoutManager(new LinearLayoutManager(context));

//设置条目类（必须调用的方法）
recyclerView.setItemClass(TestRecyclerViewItem.class);

//这是顶部
recyclerView.setHeaderView(new TestHeaderView(context));

//设置底部
recyclerView.setFooterView(new TestFooterView(context));

//初始化（必须调用的方法）
recyclerView.initialize();

//顶部View
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

//底部View
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

//内容条目
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
