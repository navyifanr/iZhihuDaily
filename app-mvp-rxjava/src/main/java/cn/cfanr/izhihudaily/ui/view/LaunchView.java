package cn.cfanr.izhihudaily.ui.view;

import cn.cfanr.izhihudaily.core.mvp.MvpView;
import cn.cfanr.izhihudaily.model.Launch;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public interface LaunchView extends MvpView{
    void showLaunchPage(Launch launch);
}
