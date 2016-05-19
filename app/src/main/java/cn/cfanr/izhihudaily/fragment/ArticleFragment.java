package cn.cfanr.izhihudaily.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.utils.ImageUtils;
import cn.cfanr.izhihudaily.utils.JsonTool;

/**
 *  @author xifan
 *  @time 2016/5/13
 *  @desc 文章详情
 */
public class ArticleFragment extends Fragment {
    private static final String ARTICLE_ID = "articleId";
    private String articleId;

    private View layoutView;
    private RelativeLayout rlTop;
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvCopyRight;
    private WebView mWebView;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(String articleId) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            articleId = getArguments().getString(ARTICLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView=inflater.inflate(R.layout.fragment_article, container, false);
        return layoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(layoutView);
        loadArticleContent(articleId);
    }

    @SuppressLint("JavascriptInterface")
    private void initView(View layoutView) {
        rlTop=$(layoutView, R.id.rl_article_top_img);
        ivImage=$(layoutView, R.id.iv_article_image);
        tvCopyRight=$(layoutView, R.id.tv_article_img_copyright);
        tvTitle=$(layoutView, R.id.tv_article_title);
        mWebView=$(layoutView, R.id.web_view_article);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAppCachePath(getActivity().getApplicationContext().getDir("cache", 0).getPath());
        mWebView.getSettings().setCacheMode(1);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.addJavascriptInterface(this, "ZhihuDaily");

        rlTop.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams param=rlTop.getLayoutParams();
                int width=rlTop.getWidth();
                int height=width*9/16;
                param.height=height;
                rlTop.setLayoutParams(param);
                ViewGroup.LayoutParams webParam=mWebView.getLayoutParams();
                webParam.width=width;
                mWebView.setLayoutParams(webParam);
            }
        });
    }

    private void loadArticleContent(String articleId){
        String tagName=getClassMethodName();
        String url=String.format(Api.url_article_content, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            String imgUrl=JsonTool.mapObjVal2Str(resultMap, "image");
                            if(TextUtils.isEmpty(imgUrl)){
//                                rlTop.setVisibility(View.GONE);
                                ViewGroup.LayoutParams param=rlTop.getLayoutParams();
                                param.height=0;
                                rlTop.setLayoutParams(param);
                            }else {
                                ImageUtils.loadImage(ivImage, imgUrl);
                                String imgSource = JsonTool.mapObjVal2Str(resultMap, "image_source");
                                tvCopyRight.setText(imgSource);
                                String title = JsonTool.mapObjVal2Str(resultMap, "title");
                                tvTitle.setText(title);
                            }
                            String bodyData=JsonTool.mapObjVal2Str(resultMap, "body");
//                            mWebView.loadData(bodyData, "text/html", "UTF-8");
                            mWebView.loadDataWithBaseURL(null, getHtmlData(bodyData), "text/html", "UTF-8", null);
                            String shareUrl=JsonTool.mapObjVal2Str(resultMap, "share_url");
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
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private <T extends View> T $(View view, @IdRes int resId){
        return (T) view.findViewById(resId);
    }

    public String getClassMethodName(){
        try {
            return this.getClass().getName()+"."+Thread.currentThread().getStackTrace()[3].getMethodName();
        }catch (Exception e){

        }
        return this.getClass().getName()+"."+Thread.currentThread().getStackTrace()[0].getMethodName();
    }
}
