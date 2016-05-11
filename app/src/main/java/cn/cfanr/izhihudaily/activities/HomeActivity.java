package cn.cfanr.izhihudaily.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.HomeAdapter;
import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.base.BaseActivity;
import cn.cfanr.izhihudaily.model.HomeModel;
import cn.cfanr.izhihudaily.model.HomeType;
import cn.cfanr.izhihudaily.model.NewsModel;
import cn.cfanr.izhihudaily.utils.DateTimeUtils;
import cn.cfanr.izhihudaily.utils.JsonTool;

public class HomeActivity extends BaseActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private List<NewsModel> bannerList=new ArrayList<>();
    private List<NewsModel> newsModelList=new ArrayList<>();
    private List<HomeModel> homeModelList=new ArrayList<>();
    private HomeAdapter mAdapter;

    private int dayNum=0;

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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        mRecyclerView=$(R.id.recyclerView);
    }

    @Override
    public void initEvent() {
        mAdapter=new HomeAdapter(this, homeModelList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        loadHomeData(0);
    }

    private void loadHomeData(int dayNum){
        String tagName=getClassMethodName();
        String url;
        if(dayNum==0){
            url=Api.url_latest_news;
        }else{
            url=String.format(Api.url_before_news, DateTimeUtils.getNDaysAgo(dayNum));
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap=JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            String title=resultMap.get("date").toString();
                            String bannerStr=resultMap.get("top_stories").toString();
                            bannerList=JsonTool.jsonToNewsModelList(bannerStr);
                            String contentStr=resultMap.get("stories").toString();
                            newsModelList=JsonTool.jsonToNewsModelList(contentStr);
                            setHomeModelData(bannerList, title, newsModelList);
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
        if(bannerList!=null){
            HomeModel bannerItem=new HomeModel();
            bannerItem.setBannerList(bannerList);
            bannerItem.setType(HomeType.BANNER_ITEM);
            homeModelList.add(bannerItem);
        }
        if(!TextUtils.isEmpty(title)){
            HomeModel titleItem=new HomeModel();
            titleItem.setDate(title);
            titleItem.setType(HomeType.TITLE_ITEM);
            homeModelList.add(titleItem);
        }
        if(newsModelList!=null) {
            int length = newsModelList.size();
            for (int index = 0; index < length; index++) {
                HomeModel newsItem = new HomeModel();
                newsItem.setNewsModel(newsModelList.get(index));
                newsItem.setType(HomeType.NEWS_ITEM);
                homeModelList.add(newsItem);
            }
        }
        mAdapter.notifyDataSetChanged();
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
            case R.id.action_settings:
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
