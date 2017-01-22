package cn.cfanr.izhihudaily.presenter;

import android.support.annotation.NonNull;

import cn.cfanr.izhihudaily.app.RetrofitManager;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.model.NewsExtra;
import cn.cfanr.izhihudaily.ui.view.ArticleView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
        RetrofitManager.builder().loadNewsExtraData(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsExtra>() {
                    @Override
                    public void call(NewsExtra newsExtra) {
                        if(newsExtra !=null){
                            articleView.setArticleExtraData(newsExtra.getComments(), newsExtra.getPopularity());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

//        String tagName=getClassMethodName();
//        String url=String.format(Api.url_article_extra_data, articleId);
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
//                        if(resultMap!=null){
//                            String commentsNum=JsonTool.mapObjVal2Str(resultMap, "comments");
//                            String likesNum=JsonTool.mapObjVal2Str(resultMap, "popularity");
//                            articleView.setArticleExtraData(commentsNum, likesNum);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }
}
