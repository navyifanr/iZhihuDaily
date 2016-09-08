package cn.cfanr.izhihudaily.ui.view;

import cn.cfanr.izhihudaily.core.mvp.MvpView;

/**
 * @author xifan
 *         date: 2016/8/29
 *         desc:
 */
public interface ArticleFrgView extends MvpView {
    void hideArticleTopLayout();
    void showArticleTopImage(String imgUrl, String imgSource, String title);
    void loadUrlWithCSS(String bodyData);
    void loadUrlNoCSS(String shareUrl);
}
