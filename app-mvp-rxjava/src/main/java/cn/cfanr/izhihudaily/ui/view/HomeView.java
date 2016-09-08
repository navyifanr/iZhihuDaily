package cn.cfanr.izhihudaily.ui.view;

import java.util.List;

import cn.cfanr.izhihudaily.core.mvp.MvpView;
import cn.cfanr.izhihudaily.model.NewsList;
import cn.cfanr.izhihudaily.model.Theme;
import cn.cfanr.izhihudaily.model.ThemeDaily;

/**
 * @author xifan
 *         date: 2016/8/7
 *         desc:
 */
public interface HomeView extends MvpView {
    void showHomeData(NewsList newsList);

    void showThemeDailyData(ThemeDaily themeDaily);

    void setDrawerMenu(List<Theme> themeList);
}
