package cn.cfanr.izhihudaily.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.ArticlePagerAdapter;
import cn.cfanr.izhihudaily.core.BaseActivity;
import cn.cfanr.izhihudaily.ui.fragment.ArticleFragment;
import cn.cfanr.izhihudaily.presenter.ArticleActPresenter;
import cn.cfanr.izhihudaily.utils.ScreenUtil;
import cn.cfanr.izhihudaily.utils.ToastUtils;
import cn.cfanr.izhihudaily.ui.view.ArticleActView;

public class ArticleActivity extends BaseActivity implements ArticleActView {
    private ArticleActPresenter articleActPresenter;

    private Toolbar mToolbar;
    private ImageView ivShare, ivCollect, ivComment, ivLike;
    private TextView tvComment, tvLike;

    private ViewPager mViewPager;
    private ArticlePagerAdapter mAdapter;
    private List<Fragment> fragmentList=new ArrayList<>();
    private int position=0;
    private ArrayList<String> articleIdList=new ArrayList<>();

    private String articleId;
    private String commentsNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupMenu();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article;
    }

    @Override
    protected void initPresenter() {
        articleActPresenter =new ArticleActPresenter(this);
        articleActPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mToolbar=$(R.id.toolbar_article);
        mToolbar.setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = new ScreenUtil().getAppBarHeight();  //注意使用的是AppBar的高度
            mToolbar.setPadding(mToolbar.getPaddingLeft(), new ScreenUtil().getStatusBarHeight(), mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager=$(R.id.view_pager_article);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
    }

    @Override
    protected void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        articleIdList=bundle.getStringArrayList("articleIdList");
        articleId=bundle.getString("articleId", articleId);
        int length=articleIdList.size();
        for(int index=0;index<length;index++){
            String tmpArticleId=articleIdList.get(index);
            if(TextUtils.equals(tmpArticleId, articleId)){
                position=index;
            }
            ArticleFragment articleFragment=ArticleFragment.newInstance(tmpArticleId);
            fragmentList.add(articleFragment);
        }
        mAdapter=new ArticlePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position, false);
    }

    private void setupMenu() {
        ivShare=$(R.id.iv_article_share);
        ivCollect=$(R.id.iv_article_collect);
        ivComment=$(R.id.iv_article_comment);
        tvComment=$(R.id.tv_article_comment);
        ivLike=$(R.id.iv_article_like);
        tvLike=$(R.id.tv_article_like);

        articleActPresenter.loadArticleExtraData(articleId);

        OnMenuItemClickListener onMenuItemClickListener=new OnMenuItemClickListener();
        ivShare.setOnClickListener(onMenuItemClickListener);
        ivCollect.setOnClickListener(onMenuItemClickListener);
        ivComment.setOnClickListener(onMenuItemClickListener);
        ivLike.setOnClickListener(onMenuItemClickListener);
    }

    @Override
    public void setArticleExtraData(String commentsNum, String likesNum) {
        this.commentsNum=commentsNum;
        if(!TextUtils.isEmpty(commentsNum)&&!TextUtils.equals(commentsNum, "0")){
            tvComment.setText(commentsNum);
        }
        if(!TextUtils.isEmpty(likesNum)&&!TextUtils.equals(likesNum, "0")){
            tvLike.setText(likesNum);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        articleActPresenter.detachView();
    }

    class OnMenuItemClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_article_share:
                    break;
                case R.id.iv_article_collect:
                    break;
                case R.id.iv_article_comment:
                    if(TextUtils.equals(commentsNum, "0")){
                        ToastUtils.show("暂无评论！");
                        return;
                    }
                    Intent intent=new Intent(getActivity(), CommentActivity.class);
                    intent.putExtra("commentsNum", commentsNum);
                    intent.putExtra("articleId", articleId);
                    startActivity(intent);
                    break;
                case R.id.iv_article_like:
                    break;
            }
        }
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
