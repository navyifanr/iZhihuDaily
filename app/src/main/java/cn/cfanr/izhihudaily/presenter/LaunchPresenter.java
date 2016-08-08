package cn.cfanr.izhihudaily.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.utils.ScreenUtil;
import cn.cfanr.izhihudaily.ui.view.LaunchView;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public class LaunchPresenter extends BasePresenter<LaunchView> {

    public void loadLaunchData(){
        String tagName=getClassMethodName();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Api.url_launch+getLaunchImgSize(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String author= JsonTool.getObjString(response, "text");
                        String imgUrl=JsonTool.getObjString(response, "img");
                        getMvpView().showLaunchPage(author, imgUrl);
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
        ScreenUtil screenUtil=new ScreenUtil();
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
