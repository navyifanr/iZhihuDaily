package cn.cfanr.izhihudaily.activities;

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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.ArticlePagerAdapter;
import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.base.BaseActivity;
import cn.cfanr.izhihudaily.fragment.ArticleFragment;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.utils.ScreenUtil;
import cn.cfanr.izhihudaily.utils.ToastUtils;

public class ArticleActivity extends BaseActivity {
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
    public void initView() {
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
//        mViewPager.post(new Runnable() {
//            @Override
//            public void run() {
//                int statusBarHeight=new ScreenUtil(getActivity()).getStatusBarHeight();
//                ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();
//                params.setMargins(0, statusBarHeight, 0, 0);
//                mViewPager.setLayoutParams(params);
//            }
//        });
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
        articleId=articleIdList.get(position);
        int length=articleIdList.size();
        for(int index=0;index<length;index++){
            ArticleFragment articleFragment=ArticleFragment.newInstance(articleIdList.get(index));
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

        loadArticleExtraData(articleId);

        OnMenuItemClickListener onMenuItemClickListener=new OnMenuItemClickListener();
        ivShare.setOnClickListener(onMenuItemClickListener);
        ivCollect.setOnClickListener(onMenuItemClickListener);
        ivComment.setOnClickListener(onMenuItemClickListener);
        ivLike.setOnClickListener(onMenuItemClickListener);
    }

    private void loadArticleExtraData(String articleId){
        String tagName=getClassMethodName();
        String url=String.format(Api.url_article_extra_data, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
                        if(resultMap!=null){
                            commentsNum=JsonTool.mapObjVal2Str(resultMap, "comments");
                            String likesNum=JsonTool.mapObjVal2Str(resultMap, "popularity");
                            if(!TextUtils.isEmpty(commentsNum)&&!TextUtils.equals(commentsNum, "0")){
                                tvComment.setText(commentsNum);
                            }
                            if(!TextUtils.isEmpty(likesNum)&&!TextUtils.equals(likesNum, "0")){
                                tvLike.setText(likesNum);
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
