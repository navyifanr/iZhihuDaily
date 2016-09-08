package cn.cfanr.izhihudaily.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.core.BaseFragment;
import cn.cfanr.izhihudaily.presenter.ArticleFrgPresenter;
import cn.cfanr.izhihudaily.ui.view.ArticleFrgView;
import cn.cfanr.izhihudaily.utils.ImageUtils;
import cn.cfanr.izhihudaily.utils.ScreenUtil;

/**
 *  @author xifan
 *  @time 2016/5/13
 *  @desc 文章详情
 */
public class ArticleFragment extends BaseFragment implements ArticleFrgView{
    private static final String ARTICLE_ID = "articleId";
    private String articleId;

    private ArticleFrgPresenter articleFrgPresenter;

    private ScrollView mScrollView;
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
    public int getLayoutResId() {
        return R.layout.fragment_article;
    }

    @Override
    protected void initPresenter() {
        articleFrgPresenter = new ArticleFrgPresenter(this);
        articleFrgPresenter.attachView(this);
    }

    @Override @SuppressLint("JavascriptInterface")
    public void initViews(View layoutView) {
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        articleFrgPresenter.loadArticleContent(articleId);
    }

    @Override
    public void hideArticleTopLayout() {
//        rlTop.setVisibility(View.GONE);   //invalid
        ViewGroup.LayoutParams param=rlTop.getLayoutParams();
        param.height=0;
        rlTop.setLayoutParams(param);
    }

    @Override
    public void showArticleTopImage(String imgUrl, String imgSource, String title) {
        tvCopyRight.setText(imgSource);
        ImageUtils.loadImage(ivImage, imgUrl);
        tvTitle.setText(title);
    }

    @Override
    public void loadUrlWithCSS(String htmlData) {
        mWebView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
    }

    @Override
    public void loadUrlNoCSS(String shareUrl) {
        mWebView.loadUrl(shareUrl);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        articleFrgPresenter.detachView();
    }
}
