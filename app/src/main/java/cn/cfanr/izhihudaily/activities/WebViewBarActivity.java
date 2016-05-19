package cn.cfanr.izhihudaily.activities;

import android.os.Bundle;
import android.webkit.WebView;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.base.BaseBarActivity;

public class WebViewBarActivity extends BaseBarActivity {
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
    public void initView() {
        setTitle("主编简介");
        mWebView=$(R.id.web_view);
    }

    @Override
    public void initEvent() {
        Bundle bundle=getIntent().getExtras();
        String webUrl=bundle.getString("webUrl");
        mWebView.loadUrl(webUrl);
    }
}
