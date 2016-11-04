package cn.cfanr.izhihudaily.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
    public void showPageScaleAnim() {
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImageView.setAnimation(scaleAnim);
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
