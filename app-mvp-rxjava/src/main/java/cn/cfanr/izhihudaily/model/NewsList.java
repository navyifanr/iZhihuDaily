package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 *         date: 2016/8/16
 *         desc:
 */
public class NewsList {
    private String date;
    private List<News> stories;
    private List<News> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<News> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<News> top_stories) {
        this.top_stories = top_stories;
    }

    public List<News> getStories() {
        return stories;
    }

    public void setStories(List<News> stories) {
        this.stories = stories;
    }
}
