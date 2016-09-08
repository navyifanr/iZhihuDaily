package cn.cfanr.izhihudaily.model;

/**
 * @author xifan
 * @time 2016/5/17
 * @desc
 */
public class ThemeModel {

    /**
     * color : 1615359
     * thumbnail : http://pic4.zhimg.com/aa94e197491fb9c44d384c4747773810.jpg
     * description : 商业世界变化越来越快，就是这些家伙干的
     * id : 5
     * name : 大公司日报
     */

    private int color;
    private String thumbnail;
    private String description;
    private int id;
    private String name;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
