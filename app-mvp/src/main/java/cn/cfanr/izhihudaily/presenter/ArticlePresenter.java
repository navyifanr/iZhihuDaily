package cn.cfanr.izhihudaily.presenter;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.ui.view.ArticleView;
import cn.cfanr.izhihudaily.utils.JsonTool;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public class ArticlePresenter extends BasePresenter<ArticleView> {
    private ArticleView articleView;

    public ArticlePresenter(@NonNull ArticleView articleView){
        this.articleView = articleView;
    }

    public void loadArticleExtraData(String articleId){
        String tagName=getClassMethodName();
        String url=String.format(Api.url_article_extra_data, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            String commentsNum=JsonTool.mapObjVal2Str(resultMap, "comments");
                            String likesNum=JsonTool.mapObjVal2Str(resultMap, "popularity");
                            articleView.setArticleExtraData(commentsNum, likesNum);
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
