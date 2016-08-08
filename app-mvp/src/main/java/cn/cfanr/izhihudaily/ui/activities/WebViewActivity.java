package cn.cfanr.izhihudaily.ui.activities;

import android.os.Bundle;
import android.webkit.WebView;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.core.BaseBarActivity;

public class WebViewActivity extends BaseBarActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setTitle("主编简介");
        mWebView=$(R.id.web_view);
    }

    @Override
    protected void initEvent() {
        Bundle bundle=getIntent().getExtras();
        String webUrl=bundle.getString("webUrl");
        mWebView.loadUrl(webUrl);
    }
}
