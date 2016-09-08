package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 *         date: 2016/8/17
 *         desc:
 */
public class ThemeList {

    private int limit;
    private List<?> subscribed;

    private List<Theme> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<?> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<?> subscribed) {
        this.subscribed = subscribed;
    }

    public List<Theme> getOthers() {
        return others;
    }

    public void setOthers(List<Theme> others) {
        this.others = others;
    }
}
