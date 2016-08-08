package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 * @time 2016/5/6
 * @desc
 */
public class NewsModel {

    /**
     * title : 一种用纱线完成的，像《核舟记》那么精巧的雕刻
     * ga_prefix : 050609
     * images : ["http://pic2.zhimg.com/9cabab4acd373708f63f0f03a1f9b8bd.jpg"]
     * multipic : true
     * type : 0
     * id : 8261406
     */

    private String title;
    private String ga_prefix;
    private boolean multipic;
    private int type;
    private String id;
    private List<String> images;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
