package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 * @time 2016/5/6
 * @desc 首页列表模型
 */
public class HomeModel{
    private int type;
    private String date;
    private News newsModel;
    private List<News> bannerList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<News> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<News> bannerList) {
        this.bannerList = bannerList;
    }

    public News getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(News newsModel) {
        this.newsModel = newsModel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
