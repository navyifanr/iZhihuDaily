package cn.cfanr.izhihudaily.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.core.BaseActivity;
import cn.cfanr.izhihudaily.presenter.LaunchPresenter;
import cn.cfanr.izhihudaily.ui.view.LaunchView;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc 启动页
 */
public class LaunchActivity extends BaseActivity implements LaunchView{
    private LaunchPresenter launchPresenter;

    private NetworkImageView mImageView;
    private TextView tvAuthor;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initPresenter() {
        launchPresenter=new LaunchPresenter(this);
        launchPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mImageView=$(R.id.net_iv_launch_bg);
        tvAuthor = $(R.id.tv_author);
    }

    @Override
    protected void initEvent() {
        imageLoader = AppController.getInstance().getImageLoader();
        mImageView.setErrorImageResId(R.mipmap.bg_launch);
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                finish();
            }
        }, 3000);
        launchPresenter.loadLaunchData();
    }

    @Override
    public void showLaunchPage(String author, String imgUrl) {
        mImageView.setImageUrl(imgUrl, imageLoader);
        if(!TextUtils.isEmpty(author)) {
            tvAuthor.setText("By " + author);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        launchPresenter.detachView();
    }
}
