package cn.cfanr.izhihudaily.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.CommonAdapter;
import cn.cfanr.izhihudaily.adapter.HomeAdapter;
import cn.cfanr.izhihudaily.adapter.ThemeDailyAdapter;
import cn.cfanr.izhihudaily.core.BaseActivity;
import cn.cfanr.izhihudaily.model.Editor;
import cn.cfanr.izhihudaily.model.HomeModel;
import cn.cfanr.izhihudaily.model.News;
import cn.cfanr.izhihudaily.model.NewsList;
import cn.cfanr.izhihudaily.model.Theme;
import cn.cfanr.izhihudaily.model.ThemeDaily;
import cn.cfanr.izhihudaily.model.ThemeDailyModel;
import cn.cfanr.izhihudaily.model.HomeType;
import cn.cfanr.izhihudaily.presenter.HomePresenter;
import cn.cfanr.izhihudaily.ui.view.HomeView;
import cn.cfanr.izhihudaily.ui.viewholder.CommonViewHolder;
import cn.cfanr.izhihudaily.utils.DateTimeUtils;
import cn.cfanr.izhihudaily.utils.ScreenUtil;
import cn.cfanr.izhihudaily.widget.NoScrollListView;
import cn.cfanr.izhihudaily.widget.RecyclerView.EndlessRecyclerOnScrollListener;

public class HomeActivity extends BaseActivity implements HomeView{
    private HomePresenter homePresenter;

    private DrawerLayout mDrawerLayout;
    private ScrollView mScrollView;
    private LinearLayout llHome;
    private NoScrollListView mListView;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private List<HomeModel> homeModelList=new ArrayList<>();
    private ArrayList<String> articleIdList=new ArrayList<>();
    private HomeAdapter mAdapter;

    private List<Theme> themeList =new ArrayList<>();

    private List<ThemeDailyModel> themeDailyModelList=new ArrayList<>();
    private ThemeDailyAdapter themeDailyAdapter;
    private ArrayList<String> themeArticleIdList=new ArrayList<>();

    private int themeId=-1;  //-1表示首页，其他为主题项
    private int dayNum=0;  //【首页】n天前的数据，0为当天
    private int nonNewsListNum=0;  //【首页】不是文章列表的item数量
    private String lastNewsId="0";  //【主题项】最后一条文章的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initPresenter() {
        homePresenter=new HomePresenter(this);
        homePresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mToolbar=$(R.id.toolbar);
        mToolbar.setTitle("首页");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = new ScreenUtil().getAppBarHeight();
            mToolbar.setPadding(mToolbar.getPaddingLeft(), new ScreenUtil().getStatusBarHeight(),
                    mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout=$(R.id.drawer_layout_id);
        mScrollView=$(R.id.scroll_view_drawer);
        mScrollView.setFocusable(true);
        mScrollView.requestLayout();
        llHome=$(R.id.ll_drawer_content_home);
        mListView=$(R.id.lv_drawer_content_list);
        mRecyclerView=$(R.id.recyclerView);
        mSwipeRefreshLayout=$(R.id.swipe_refresh_home);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
    }

    @Override
    protected void initEvent() {
        mAdapter=new HomeAdapter(this, homeModelList);
        themeDailyAdapter=new ThemeDailyAdapter(getActivity(), themeDailyModelList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        homePresenter.loadHomeData(themeId, dayNum, lastNewsId);
//        homePresenter.loadHomeNewsData(dayNum);
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String articleId) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("articleId", articleId);
                intent.putStringArrayListExtra("articleIdList", articleIdList);
                startActivity(intent);
            }
        });
        themeDailyAdapter.setOnItemClickListener(new ThemeDailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                String articleId=themeArticleIdList.get(position-1);
                intent.putExtra("articleId", articleId);
                intent.putStringArrayListExtra("articleIdList", themeArticleIdList);
                startActivity(intent);
            }
        });

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentType(true);
                setSelectItem(-1, themeId);
            }
        });
        homePresenter.loadDrawerList();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dayNum=0;
                        lastNewsId="0";
                        if(themeId==-1){
//                            homePresenter.loadHomeNewsData(dayNum);
                        }else{
//                            homePresenter.loadThemeDailyNews(themeId, lastNewsId);
                        }
                        homePresenter.loadHomeData(themeId, 0, "0");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                if(themeId==-1){
                    dayNum++;
//                    homePresenter.loadHomeNewsData(dayNum);
                }else{
                    int length=themeArticleIdList.size();
                    lastNewsId=themeArticleIdList.get(length-1);
//                    homePresenter.loadThemeDailyNews(themeId, lastNewsId);
                }
                homePresenter.loadHomeData(themeId, dayNum, lastNewsId);
            }

            @Override
            public void onChangeTitle(int firstVisibleItemPosition) {
                super.onChangeTitle(firstVisibleItemPosition);
                if(themeId==-1){  //【首页】才需要改title
                    String title=homeModelList.get(firstVisibleItemPosition).getDate();
                    setTitle(title);
                }
            }
        });

        setDoubleClickBarToTop(mToolbar, mRecyclerView);
    }

    private void setSelectItem(int position, int themeId) {
        if(position==-1){
            setTitle("首页");
//            homePresenter.loadHomeNewsData(0);
        }else {
            // 高亮被选择的item, 更新标题, 并关闭drawer
            mListView.setItemChecked(position, true);
            setTitle(themeList.get(position).getName());
//            homePresenter.loadThemeDailyNews(themeId, "0");
        }
        mDrawerLayout.closeDrawers();
        homePresenter.loadHomeData(themeId, 0, "0");
    }

    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    public void setContentType(boolean isHomeType){
        if(isHomeType){
            themeId=-1;
        }
        dayNum=0;
        lastNewsId="0";
    }

    @Override
    public void showHomeData(NewsList newsList) {
        if(dayNum==0&&homeModelList!=null&&homeModelList.size()>0){
            homeModelList.clear();
            articleIdList.clear();
            mRecyclerView.setAdapter(mAdapter);
        }
        List<News> bannerList=newsList.getTop_stories();
        String title=newsList.getDate();
        List<News> newsModelList=newsList.getStories();
        if(bannerList!=null){
            HomeModel bannerItem=new HomeModel();
            bannerItem.setDate("首页");
            bannerItem.setBannerList(bannerList);
            bannerItem.setType(HomeType.BANNER_ITEM);
            homeModelList.add(bannerItem);
            nonNewsListNum++;
        }
        if(!TextUtils.isEmpty(title)){
            if(dayNum==0){
                title="今日热闻";
            }else{
                title=DateTimeUtils.convertDateTxt(title);
            }
            HomeModel titleItem=new HomeModel();
            titleItem.setDate(title);
            titleItem.setType(HomeType.TITLE_ITEM);
            homeModelList.add(titleItem);
            nonNewsListNum++;
        }
        if(newsModelList!=null) {
            int length = newsModelList.size();
            for (int index = 0; index < length; index++) {
                HomeModel newsItem = new HomeModel();
                newsItem.setDate(title);  //作为识别属于哪个title的标识
                newsItem.setNewsModel(newsModelList.get(index));
                newsItem.setType(HomeType.NEWS_ITEM);
                homeModelList.add(newsItem);
                articleIdList.add(newsModelList.get(index).getId());
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showThemeDailyData(ThemeDaily themeDaily) {
        String description=themeDaily.getDescription();
        String background=themeDaily.getBackground();
        List< Editor> editorModelList=themeDaily.getEditors();
        List<News> themeNewsList=themeDaily.getStories();
        if(TextUtils.equals(lastNewsId, "0")){       //为0表示第一次加载
            if(themeDailyModelList!=null&&themeDailyModelList.size()>0) {
                themeDailyModelList.clear();
                themeArticleIdList.clear();
            }
            mRecyclerView.setAdapter(themeDailyAdapter);  //只有第一次加载才设置适配器
        }
        if(!TextUtils.isEmpty(background)&&!TextUtils.isEmpty(description)){
            ThemeDailyModel headerItem=new ThemeDailyModel();
            headerItem.setType(ThemeDailyModel.THEME_DAILY_HEADER);
            headerItem.setBackground(background);
            headerItem.setDescription(description);
            headerItem.setEditorModelList(editorModelList);
            themeDailyModelList.add(headerItem);
        }
        if(themeNewsList!=null){
            int length=themeNewsList.size();
            for(int index=0; index<length; index++){
                ThemeDailyModel newsItem=new ThemeDailyModel();
                newsItem.setType(ThemeDailyModel.THEME_DAILY_NEWS);
                newsItem.setNewsModel(themeNewsList.get(index));
                themeDailyModelList.add(newsItem);
                themeArticleIdList.add(themeNewsList.get(index).getId());
            }
        }
        themeDailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void setDrawerMenu(final List<Theme> themeList) {
        this.themeList =themeList;
        mListView.setAdapter(new CommonAdapter<Theme>(getActivity(), themeList, R.layout.item_drawer_theme) {
            @Override
            public void convert(CommonViewHolder holder, Theme theme, int position) {
                holder.setText(R.id.tv_item_drawer_theme_title, theme.getName());
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentType(false);
                themeId=themeList.get(position).getId();
                setSelectItem(position, themeId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_settings:
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }
}
