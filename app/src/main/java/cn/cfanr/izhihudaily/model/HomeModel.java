package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 * @time 2016/5/6
 * @desc 首页列表模型
 */
public class HomeModel {
    private int type;
    private String date;
    private NewsModel newsModel;
    private List<NewsModel> bannerList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NewsModel getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(NewsModel newsModel) {
        this.newsModel = newsModel;
    }

    public List<NewsModel> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<NewsModel> bannerList) {
        this.bannerList = bannerList;
    }
}
