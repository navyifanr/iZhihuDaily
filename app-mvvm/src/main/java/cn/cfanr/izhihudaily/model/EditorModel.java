package cn.cfanr.izhihudaily.model;

/**
 * @author xifan
 * @time 2016/5/18
 * @desc
 */
public class EditorModel {

    /**
     * url : http://www.zhihu.com/people/deng-ruo-xu
     * bio : 好奇心日报
     * id : 82
     * avatar : http://pic2.zhimg.com/d3b31fa32_m.jpg
     * name : 邓若虚
     */

    private String url;
    private String bio;
    private int id;
    private String avatar;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
