package cn.cfanr.izhihudaily.presenter;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.model.CommentModel;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.ui.view.CommentView;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public class CommentPresenter extends BasePresenter<CommentView> {
    private CommentView commentView;
    private List<CommentModel> longCommentList=new ArrayList<>();
    private List<CommentModel> shortCommentList=new ArrayList<>();

    public CommentPresenter(@NonNull CommentView commentView){
        this.commentView=commentView;
    }

    public void loadLongCommentData(String articleId){
        String tagName= getClassName();
        String url=String.format(Api.url_article_long_comments, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
                        String content=JsonTool.mapObjVal2Str(resultMap, "comments");
                        longCommentList=JsonTool.jsonToObjList(content, CommentModel[].class);
                        commentView.setLongCommentsData(longCommentList);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }

    public void loadShortCommentData(String articleId){
        String tagName= getClassName();
        String url=String.format(Api.url_article_short_comments, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap=JsonTool.parseJson2Map(response.toString());
                        String content=JsonTool.mapObjVal2Str(resultMap, "comments");
                        shortCommentList=JsonTool.jsonToObjList(content, CommentModel[].class);
                        commentView.setShortCommentsData(shortCommentList);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }
}
