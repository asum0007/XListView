# XListView
XListView

public XRecyclerView recyclerView;

//是否需要下拉刷新
recyclerView.setPullDownRefreshEnable(false);

//是否需要触底加载更多
recyclerView.setPullUpRefreshEnable(false);

//每一条Item之间的间距
recyclerView.setSpace(space);

//隐藏滚动条
recyclerView.setScrollBarEnable(false);

//布局方式
recyclerView.setLayoutManager(new LinearLayoutManager(context));

//设置条目类
recyclerView.setItemClass(TestRecyclerViewItem.class);

//初始化
recyclerView.initialize();

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
