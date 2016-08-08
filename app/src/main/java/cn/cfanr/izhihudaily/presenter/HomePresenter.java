package cn.cfanr.izhihudaily.presenter;

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
import cn.cfanr.izhihudaily.model.EditorModel;
import cn.cfanr.izhihudaily.model.NewsModel;
import cn.cfanr.izhihudaily.model.ThemeModel;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.utils.DateTimeUtils;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.ui.view.HomeView;

/**
 * @author xifan
 *         date: 2016/8/5
 *         desc:
 */
public class HomePresenter extends BasePresenter<HomeView>{

    private List<NewsModel> bannerList=new ArrayList<>();
    private List<NewsModel> newsModelList=new ArrayList<>();

    private List<ThemeModel> themeModelList=new ArrayList<>();

    private List<EditorModel> editorModelList=new ArrayList<>();
    private List<NewsModel> themeNewsList=new ArrayList<>();

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
                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            if(themeId==-1) {  //首页
                                String title = JsonTool.mapObjVal2Str(resultMap, "date");
                                String bannerStr = JsonTool.mapObjVal2Str(resultMap, "top_stories");
                                bannerList = JsonTool.jsonToObjList(bannerStr, NewsModel[].class);
                                String contentStr = JsonTool.mapObjVal2Str(resultMap, "stories");
                                newsModelList = JsonTool.jsonToObjList(contentStr, NewsModel[].class);
                                getMvpView().showHomeData(bannerList, title, newsModelList);
                            }else{  //主题项
                                String description=JsonTool.mapObjVal2Str(resultMap, "description");
                                String background=JsonTool.mapObjVal2Str(resultMap, "background");
                                String editorsStr=JsonTool.mapObjVal2Str(resultMap, "editors");
                                editorModelList=JsonTool.jsonToObjList(editorsStr, EditorModel[].class);
                                String themeNewsStr=JsonTool.mapObjVal2Str(resultMap, "stories");
                                themeNewsList=JsonTool.jsonToObjList(themeNewsStr, NewsModel[].class);
                                getMvpView().showThemeDailyData(description, background, editorModelList, themeNewsList);
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
                                getMvpView().setDrawerMenu(themeModelList);
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
