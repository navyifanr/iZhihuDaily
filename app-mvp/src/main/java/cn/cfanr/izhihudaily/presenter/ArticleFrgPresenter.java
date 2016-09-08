package cn.cfanr.izhihudaily.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.ui.view.ArticleFrgView;
import cn.cfanr.izhihudaily.utils.JsonTool;

/**
 * @author xifan
 *         date: 2016/8/29
 *         desc:
 */
public class ArticleFrgPresenter extends BasePresenter<ArticleFrgView> {
    private ArticleFrgView articleFrgView;

    public ArticleFrgPresenter(@NonNull ArticleFrgView articleFrgView){
        this.articleFrgView = articleFrgView;
    }

    public void loadArticleContent(String articleId){
        String tagName= getClassName();
        String url=String.format(Api.url_article_content, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            String imgUrl=JsonTool.mapObjVal2Str(resultMap, "image");
                            if(TextUtils.isEmpty(imgUrl)){
                                articleFrgView.hideArticleTopLayout();
                            }else {
                                String imgSource = JsonTool.mapObjVal2Str(resultMap, "image_source");
                                String title = JsonTool.mapObjVal2Str(resultMap, "title");
                                articleFrgView.showArticleTopImage(imgUrl, imgSource, title);
                            }
                            String shareUrl=JsonTool.mapObjVal2Str(resultMap, "share_url");
                            String bodyData=JsonTool.mapObjVal2Str(resultMap, "body");
                            //如果body数据为空，说明是知乎日报站外资源，直接加载shareUrl；或者通过type判断，type=1站外资源（可能）
                            if(TextUtils.isEmpty(bodyData)){
                                articleFrgView.loadUrlNoCSS(shareUrl);
                            }else {
//                            mWebView.loadData(bodyData, "text/html", "UTF-8");   //乱码
                                String cssUrlStr = JsonTool.mapObjVal2Str(resultMap, "css");
                                List<String> cssUrlList = JsonTool.jsonArrayToList(cssUrlStr);
                                articleFrgView.loadUrlWithCSS(processBodyData(bodyData, cssUrlList));
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

    /**
     * 解决网页图片自适应，http://www.jianshu.com/p/d21989bea448
     */
    private String processBodyData(String bodyHTML, List<String> cssUrlList) {
        String cssUrls="";
        if(cssUrlList!=null&&cssUrlList.size()>0){
            for(String url:cssUrlList){
                if(!TextUtils.isEmpty(url)){
                    cssUrls+="<link rel=\"stylesheet\" type=\"text/css\" href=\""+url+"\">";
                }
            }
        }
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +cssUrls+
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
