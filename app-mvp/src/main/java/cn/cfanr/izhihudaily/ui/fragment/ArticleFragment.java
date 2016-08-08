package cn.cfanr.izhihudaily.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Build;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.utils.ImageUtils;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.utils.ScreenUtil;

/**
 *  @author xifan
 *  @time 2016/5/13
 *  @desc 文章详情
 */
public class ArticleFragment extends Fragment {
    private static final String ARTICLE_ID = "articleId";
    private String articleId;

    private ScrollView mScrollView;
    private View layoutView;
    private View viewBlank;
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
        mScrollView=$(layoutView, R.id.scroll_view_article);
        mScrollView.scrollTo(0, 0);
        viewBlank=$(layoutView, R.id.view_bar);
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
//        mWebView.setWebViewClient(new WebViewClient());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //有设置透明状态栏时(4.4以上)，空白bar的高度是actionbar+statusBar
            viewBlank.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams params = viewBlank.getLayoutParams();
                    int appBarHeight=new ScreenUtil(getActivity()).getAppBarHeight();
                    params.height=appBarHeight;
                    viewBlank.setLayoutParams(params);
                }
            });
        }

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
//                                rlTop.setVisibility(View.GONE);  //
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
                            String shareUrl=JsonTool.mapObjVal2Str(resultMap, "share_url");
                            String bodyData=JsonTool.mapObjVal2Str(resultMap, "body");
                            //如果body数据为空，说明是知乎日报站外资源，直接加载shareUrl；或者通过type判断，type=1站外资源（可能）
                            if(TextUtils.isEmpty(bodyData)){
                                mWebView.loadUrl(shareUrl);
                            }else {
//                            mWebView.loadData(bodyData, "text/html", "UTF-8");   //乱码
                                String cssUrlStr = JsonTool.mapObjVal2Str(resultMap, "css");
                                List<String> cssUrlList = JsonTool.jsonArrayToList(cssUrlStr);
                                mWebView.loadDataWithBaseURL(null, getHtmlData(bodyData, cssUrlList), "text/html", "UTF-8", null);
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
    private String getHtmlData(String bodyHTML, List<String> cssUrlList) {
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
