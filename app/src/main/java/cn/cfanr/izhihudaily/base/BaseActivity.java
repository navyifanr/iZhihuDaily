package cn.cfanr.izhihudaily.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initView();
        initEvent();
    }

    protected abstract int getLayoutResId();

    public abstract void initView();

    public abstract void initEvent();

    public <T extends View>T $(@IdRes int resId){
        return (T) super.findViewById(resId);
    }

    public Activity getActivity(){
        return this;
    }

    public String getClassMethodName(){
        return this.getClass().getName()+Thread.currentThread().getStackTrace()[1].getMethodName();
    }

}
