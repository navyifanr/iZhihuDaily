package cn.cfanr.izhihudaily.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.base.BaseActivity;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.utils.ScreenUtil;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc 启动页
 */
public class LaunchActivity extends BaseActivity {
    private NetworkImageView mImageView;
    private ScreenUtil screenUtil;
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
    public void initView() {
        mImageView=$(R.id.net_iv_launch_bg);
    }

    @Override
    public void initEvent() {
        imageLoader = AppController.getInstance().getImageLoader();
        mImageView.setDefaultImageResId(R.mipmap.splash_background);
        mImageView.setErrorImageResId(R.mipmap.ic_launcher);
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), MainActivity.class));
                finish();
            }
        }, 3000);
        loadLaunchData();
    }

    private void loadLaunchData(){
        String tagName=getClassMethodName();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Api.url_launch+getLaunchImgSize(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String author=JsonTool.getObjString(response, "text");
                        String imgUrl=JsonTool.getObjString(response, "img");
                        mImageView.setImageUrl(imgUrl, imageLoader);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }

    private String getLaunchImgSize(){
        String imgSize="1080*1776";
        screenUtil=new ScreenUtil();
        int screenWidth=screenUtil.getScreenWidth();
        if(screenWidth<=320){
            imgSize="320*432";
        }else if(screenWidth<=480){
            imgSize="480*728";
        }else if(screenWidth<=720){
            imgSize="720*1184";
        }else if(screenWidth<=1080){
            imgSize="1080*1776";
        }
        return imgSize;
    }
}
