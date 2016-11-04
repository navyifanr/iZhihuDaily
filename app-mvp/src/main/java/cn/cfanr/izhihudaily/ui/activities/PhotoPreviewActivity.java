package cn.cfanr.izhihudaily.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.PhotoAdapter;
import cn.cfanr.izhihudaily.app.Constant;
import cn.cfanr.izhihudaily.core.BaseBarActivity;
import cn.cfanr.izhihudaily.utils.DownLoadImageUtils;
import cn.cfanr.izhihudaily.utils.ToastUtils;
import cn.cfanr.izhihudaily.widget.PhotoViewPager;

public class PhotoPreviewActivity extends BaseBarActivity{
    private PhotoViewPager mViewPager;

    private ArrayList<String> imgUrlList= new ArrayList<>();
    private String imgUrl;
    private PhotoAdapter mAdapter;
    private int currentIndex = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo_preview;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        mViewPager = $(R.id.photo_view_pager);

        imgUrlList = getIntent().getExtras().getStringArrayList(Constant.IMG_URL_ALL);
        imgUrl = getIntent().getExtras().getString(Constant.IMG_URL);
        mAdapter = new PhotoAdapter(this, imgUrlList);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        int position = imgUrlList.indexOf(imgUrl);
        mViewPager.setCurrentItem(position);
        final int size = imgUrlList.size();
        if(size > 0){
            setTitle((position+1)+"/"+size);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                setTitle((currentIndex+1)+"/"+size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                DownLoadImageUtils.downLoad(this, imgUrlList.get(currentIndex));
                ToastUtils.show("图片保存成功~");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
