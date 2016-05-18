package cn.cfanr.izhihudaily.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.ArticlePagerAdapter;
import cn.cfanr.izhihudaily.base.BaseActivity;
import cn.cfanr.izhihudaily.fragment.ArticleFragment;
import cn.cfanr.izhihudaily.utils.ScreenUtil;

public class ArticleActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ArticlePagerAdapter mAdapter;
    private List<Fragment> fragmentList=new ArrayList<>();
    private int position=0;
    private ArrayList<String> articleIdList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article;
    }

    @Override
    public void initView() {
        mToolbar=$(R.id.toolbar_article);
        mToolbar.setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = new ScreenUtil().getAppBarHeight();
            mToolbar.setPadding(mToolbar.getPaddingLeft(), new ScreenUtil().getStatusBarHeight(), mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager=$(R.id.view_pager_article);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
    }

    @Override
    public void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        position=bundle.getInt("position",0);
        articleIdList=bundle.getStringArrayList("articleIdList");
        int length=articleIdList.size();
        for(int index=0;index<length;index++){
            ArticleFragment articleFragment=ArticleFragment.newInstance(articleIdList.get(index));
            fragmentList.add(articleFragment);
        }
        mAdapter=new ArticlePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                break;
            case R.id.action_collect:
                break;
            case R.id.action_comment:
                break;
            case R.id.action_like:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.setCurrentItem(position, true);
        }
    }
}
