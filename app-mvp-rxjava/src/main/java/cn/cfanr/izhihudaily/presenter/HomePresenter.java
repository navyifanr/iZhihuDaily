package cn.cfanr.izhihudaily.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.app.RetrofitManager;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.model.NewsList;
import cn.cfanr.izhihudaily.model.Theme;
import cn.cfanr.izhihudaily.model.ThemeDaily;
import cn.cfanr.izhihudaily.ui.view.HomeView;
import cn.cfanr.izhihudaily.utils.DateTimeUtils;
import cn.cfanr.izhihudaily.utils.JsonTool;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author xifan
 *         date: 2016/8/5
 *         desc:
 */
public class HomePresenter extends BasePresenter<HomeView>{
    private HomeView homeView;

//    private List<NewsModel> bannerList=new ArrayList<>();
//    private List<NewsModel> newsModelList=new ArrayList<>();

    private List<Theme> themeList =new ArrayList<>();

//    private List<EditorModel> editorModelList=new ArrayList<>();
//    private List<NewsModel> themeNewsList=new ArrayList<>();

    public HomePresenter(@NonNull HomeView homeView){
        this.homeView=homeView;
    }
    /**
     * 当lastNewsId=""时，加载的是首页数据；当dayNum=-1时，加载的是主题项的数据
     * @param themeId -1表示首页，其他为主题项
     * @param dayNum 【首页】n天前的数据
     * @param lastNewsId  主题项的文章列表最后一条数据的id
     */
    public void loadHomeData(final int themeId, int dayNum, String lastNewsId){
        String tagName=getClassMethodName();
        String url;
        if(themeId==-1){
            if(dayNum==0){
                url= Api.url_latest_news;
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
                        if(themeId==-1){
                            NewsList newsList=JsonTool.jsonToObj(response.toString(), NewsList.class);
                            homeView.showHomeData(newsList);
                        }else{
                            ThemeDaily themeDaily=JsonTool.jsonToObj(response.toString(), ThemeDaily.class);
                            homeView.showThemeDailyData(themeDaily);
                        }

//                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
//                        if(resultMap!=null){
//                            if(themeId==-1) {  //首页
//                                String title = JsonTool.mapObjVal2Str(resultMap, "date");
//                                String bannerStr = JsonTool.mapObjVal2Str(resultMap, "top_stories");
//                                bannerList = JsonTool.jsonToObjList(bannerStr, NewsModel[].class);
//                                String contentStr = JsonTool.mapObjVal2Str(resultMap, "stories");
//                                newsModelList = JsonTool.jsonToObjList(contentStr, NewsModel[].class);
////                                homeView.showHomeData(bannerList, title, newsModelList);
//                            }else{  //主题项
//                                String description=JsonTool.mapObjVal2Str(resultMap, "description");
//                                String background=JsonTool.mapObjVal2Str(resultMap, "background");
//                                String editorsStr=JsonTool.mapObjVal2Str(resultMap, "editors");
//                                editorModelList=JsonTool.jsonToObjList(editorsStr, EditorModel[].class);
//                                String themeNewsStr=JsonTool.mapObjVal2Str(resultMap, "stories");
//                                themeNewsList=JsonTool.jsonToObjList(themeNewsStr, NewsModel[].class);
////                                homeView.showThemeDailyData(description, background, editorModelList, themeNewsList);
//                            }
//                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }

    public void loadHomeNewsData(int dayNum){
        RetrofitManager.builder().loadHomeNews(dayNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        homeView.showHomeData(newsList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void loadThemeDailyNews(int themeId, String lastNewsId){
        RetrofitManager.builder().loadThemeDaily(themeId, lastNewsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ThemeDaily>() {
                    @Override
                    public void call(ThemeDaily themeDaily) {
                        homeView.showThemeDailyData(themeDaily);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
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
                            themeList =JsonTool.jsonToObjList(themeStr, Theme[].class);
                            if(themeList !=null){
                                homeView.setDrawerMenu(themeList);
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
}
