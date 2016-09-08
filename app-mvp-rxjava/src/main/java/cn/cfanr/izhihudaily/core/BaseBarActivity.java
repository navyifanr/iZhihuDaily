package cn.cfanr.izhihudaily.core;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.utils.ScreenUtil;

/**
 * @author xifan
 * @time 2016/5/19
 * @desc
 */
public abstract class BaseBarActivity extends BaseActivity {
    private Toolbar mToolbar;
    private FrameLayout mRootContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setBaseContentView(int layoutResId) {
        super.setContentView(R.layout.activity_base);
        mRootContent=$(R.id.base_content_root);
        View view = LayoutInflater.from(this).inflate(layoutResId, null);
        mRootContent.addView(view);
        initToolbar();
    }

    private void initToolbar(){
        mToolbar=$(R.id.toolbar_base);
        mToolbar.setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = new ScreenUtil().getAppBarHeight();
            mToolbar.setPadding(mToolbar.getPaddingLeft(), new ScreenUtil().getStatusBarHeight(), mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }
}
