package cn.cfanr.izhihudaily.ui.view;

import java.util.List;

import cn.cfanr.izhihudaily.model.EditorModel;
import cn.cfanr.izhihudaily.model.NewsModel;
import cn.cfanr.izhihudaily.model.ThemeModel;
import cn.cfanr.izhihudaily.core.mvp.MvpView;

/**
 * @author xifan
 *         date: 2016/8/7
 *         desc:
 */
public interface HomeView extends MvpView {
    void showHomeData(List<NewsModel> bannerList, String title, List<NewsModel> newsModelList);

    void showThemeDailyData(String description, String background, List<EditorModel> editorModelList, List<NewsModel> themeNewsList);

    void setDrawerMenu(List<ThemeModel> themeModelList);
}
