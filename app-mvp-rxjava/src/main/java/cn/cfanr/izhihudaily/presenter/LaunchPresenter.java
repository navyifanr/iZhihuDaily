package cn.cfanr.izhihudaily.presenter;

import android.support.annotation.NonNull;

import cn.cfanr.izhihudaily.app.RetrofitManager;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.model.Launch;
import cn.cfanr.izhihudaily.ui.view.LaunchView;
import cn.cfanr.izhihudaily.utils.ScreenUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public class LaunchPresenter extends BasePresenter<LaunchView> {
    private LaunchView launchView;

    public LaunchPresenter(@NonNull LaunchView launchView){
        this.launchView=launchView;
    }

    public void loadLaunchData(){
        String size=getLaunchImgSize();
        RetrofitManager.builder().loadLaunch(size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Launch>() {
                    @Override
                    public void call(Launch launch) {
                        launchView.showLaunchPage(launch);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
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
