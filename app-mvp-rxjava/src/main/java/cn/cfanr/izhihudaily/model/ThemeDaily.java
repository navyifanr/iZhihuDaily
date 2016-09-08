package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 *         date: 2016/8/17
 *         desc:
 */
public class ThemeDaily {

    /**
     * stories : [{}]
     * description : 好设计需要打磨和研习，我们分享灵感和路径
     * background : http://p3.zhimg.com/ff/15/ff150eef63a48f0d1dafb77e62610a9f.jpg
     * color : 62140
     * name : 设计日报
     * image : http://p2.zhimg.com/98/dd/98dd8dcec0186ffba8d8e298255765e7.jpg
     * editors : [{}]
     * image_source :
     */

    private String description;
    private String background;
    private int color;
    private String name;
    private String image;
    private String image_source;
    private List<News> stories;
    private List<Editor> editors;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public List<News> getStories() {
        return stories;
    }

    public void setStories(List<News> stories) {
        this.stories = stories;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
}
