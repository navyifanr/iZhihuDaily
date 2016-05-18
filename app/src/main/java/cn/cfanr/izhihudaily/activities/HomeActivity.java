package cn.cfanr.izhihudaily.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.CommonAdapter;
import cn.cfanr.izhihudaily.adapter.HomeAdapter;
import cn.cfanr.izhihudaily.adapter.ThemeDailyAdapter;
import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.base.BaseActivity;
import cn.cfanr.izhihudaily.model.EditorModel;
import cn.cfanr.izhihudaily.model.HomeModel;
import cn.cfanr.izhihudaily.model.HomeType;
import cn.cfanr.izhihudaily.model.NewsModel;
import cn.cfanr.izhihudaily.model.ThemeDailyModel;
import cn.cfanr.izhihudaily.model.ThemeModel;
import cn.cfanr.izhihudaily.utils.DateTimeUtils;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.utils.ScreenUtil;
import cn.cfanr.izhihudaily.view.NoScrollListView;
import cn.cfanr.izhihudaily.view.viewholder.CommonViewHolder;

public class HomeActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private ScrollView mScrollView;
    private LinearLayout llHome;
    private NoScrollListView mListView;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private List<NewsModel> bannerList=new ArrayList<>();
    private List<NewsModel> newsModelList=new ArrayList<>();
    private List<HomeModel> homeModelList=new ArrayList<>();
    private ArrayList<String> articleIdList=new ArrayList<>();
    private HomeAdapter mAdapter;

    private List<ThemeModel> themeModelList=new ArrayList<>();
    private CommonAdapter commonAdapter;

    private List<EditorModel> editorModelList=new ArrayList<>();
    private List<ThemeDailyModel> themeDailyModelList=new ArrayList<>();
    private List<NewsModel> themeNewsList=new ArrayList<>();
    private ThemeDailyAdapter themeDailyAdapter;
    private ArrayList<String> themeArticleIdList=new ArrayList<>();

    private int dayNum=0;  //首页n天前的数据
    private int nonNewsListNum=0;  //不是文章列表的item数量
    private String lastNewsId="0";  //最后一条文章的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        mToolbar=$(R.id.toolbar);
        mToolbar.setTitle("首页");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = new ScreenUtil().getAppBarHeight();
            mToolbar.setPadding(mToolbar.getPaddingLeft(), new ScreenUtil().getStatusBarHeight(), mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
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
    }

    @Override
    public void initEvent() {
        mAdapter=new HomeAdapter(this, homeModelList);
        themeDailyAdapter=new ThemeDailyAdapter(getActivity(), themeDailyModelList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        loadHomeData(-1, 0, lastNewsId);
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("position", position-nonNewsListNum);
                intent.putStringArrayListExtra("articleIdList", articleIdList);
                startActivity(intent);
            }
        });
        themeDailyAdapter.setOnItemClickListener(new ThemeDailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("position", position-1);
                intent.putStringArrayListExtra("articleIdList", themeArticleIdList);
                startActivity(intent);
            }
        });

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectItem(-1, -1);
            }
        });
        loadDrawerList();
    }

    private void setDrawerMenu() {
        mListView.setAdapter(commonAdapter=new CommonAdapter<ThemeModel>(getActivity(), themeModelList, R.layout.item_drawer_theme) {
            @Override
            public void convert(CommonViewHolder holder, ThemeModel themeModel, int position) {
                holder.setText(R.id.tv_item_drawer_theme_title, themeModel.getName());
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int themeId=themeModelList.get(position).getId();
                setSelectItem(position, themeId);
            }
        });
    }

    private void setSelectItem(int position, int themeId) {
        if(position==-1){
            setTitle("首页");
        }else {
            // 高亮被选择的item, 更新标题, 并关闭drawer
            mListView.setItemChecked(position, true);
            setTitle(themeModelList.get(position).getName());
        }
        mDrawerLayout.closeDrawers();
        loadHomeData(themeId, 0, "0");
    }

    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * 当lastNewsId=""时，加载的是首页数据；当dayNum=-1时，加载的是主题项的数据
     * @param themeId -1表示首页，其他为主题项
     * @param dayNum 【首页】n天前的数据
     * @param lastNewsId  主题项的文章列表最后一条数据的id
     */
    private void loadHomeData(final int themeId, int dayNum, String lastNewsId){
        String tagName=getClassMethodName();
        String url;
        if(themeId==-1){
            if(dayNum==0){
                url=Api.url_latest_news;
            }else{
                url=String.format(Api.url_before_news, DateTimeUtils.getNDaysAgo(dayNum));
            }
        }else{
            if(TextUtils.equals(lastNewsId, "0")){
                url=String.format(Api.url_theme_list_data, themeId);
            }else{
                url=String.format(Api.url_theme_list_data, themeId)+"/before/"+lastNewsId;
            }
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap=JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            if(themeId==-1) {  //首页
                                String title = JsonTool.mapObjVal2Str(resultMap, "date");
                                String bannerStr = JsonTool.mapObjVal2Str(resultMap, "top_stories");
                                bannerList = JsonTool.jsonToObjList(bannerStr, NewsModel[].class);
                                String contentStr = JsonTool.mapObjVal2Str(resultMap, "stories");
                                newsModelList = JsonTool.jsonToObjList(contentStr, NewsModel[].class);
                                setHomeModelData(bannerList, title, newsModelList);
                            }else{  //主题项
                                String description=JsonTool.mapObjVal2Str(resultMap, "description");
                                String background=JsonTool.mapObjVal2Str(resultMap, "background");
                                String editorsStr=JsonTool.mapObjVal2Str(resultMap, "editors");
                                editorModelList=JsonTool.jsonToObjList(editorsStr, EditorModel[].class);
                                String themeNewsStr=JsonTool.mapObjVal2Str(resultMap, "stories");
                                themeNewsList=JsonTool.jsonToObjList(themeNewsStr, NewsModel[].class);
                                setThemeDailyData(description, background, editorModelList, themeNewsList);
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }

    private void setHomeModelData(List<NewsModel> bannerList, String title, List<NewsModel> newsModelList) {
        if(dayNum==0&&homeModelList!=null&&homeModelList.size()>0){
            homeModelList.clear();
            articleIdList.clear();
        }
        if(bannerList!=null){
            HomeModel bannerItem=new HomeModel();
            bannerItem.setBannerList(bannerList);
            bannerItem.setType(HomeType.BANNER_ITEM);
            homeModelList.add(bannerItem);
            nonNewsListNum++;
        }
        if(!TextUtils.isEmpty(title)){
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
                newsItem.setNewsModel(newsModelList.get(index));
                newsItem.setType(HomeType.NEWS_ITEM);
                homeModelList.add(newsItem);
                articleIdList.add(newsModelList.get(index).getId());
            }
        }
        mAdapter.notifyDataSetChanged();
        mRecyclerView.removeAllViewsInLayout();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setThemeDailyData(String description, String background, List<EditorModel> editorModelList, List<NewsModel> themeNewsList) {
        if(TextUtils.equals(lastNewsId, "0")&&themeDailyModelList!=null&&themeDailyModelList.size()>0){
            themeDailyModelList.clear();
            themeArticleIdList.clear();
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
        mRecyclerView.removeAllViewsInLayout();
        mRecyclerView.setAdapter(themeDailyAdapter);
    }

    public void loadDrawerList(){
        String tagName=getClassMethodName();
        String url=Api.url_themes_list;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap=JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            String themeStr=JsonTool.mapObjVal2Str(resultMap, "others");
                            themeModelList=JsonTool.jsonToObjList(themeStr, ThemeModel[].class);
                            if(themeModelList!=null){
                                setDrawerMenu();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
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
}
