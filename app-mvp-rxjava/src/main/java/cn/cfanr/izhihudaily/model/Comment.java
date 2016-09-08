package cn.cfanr.izhihudaily.model;

/**
 * @author xifan
 * @time 2016/5/22
 * @desc
 */
public class Comment {

    /**
     * author : 河东hellokitty
     * content : 当你指出来其实有些看起来很简单（绘画，文学，甚至包括农业种田）……
     * avatar : http://pic1.zhimg.com/415313c2bdba58e23f18a05606e9848c_im.jpg
     * time : 1463901360
     * id : 25032793
     * likes : 1
     */

    private String author;
    private String content;
    private String avatar;
    private long time;
    private int id;
    private String likes;
    private Commented reply_to;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public Commented getReply_to() {
        return reply_to;
    }

    public void setReply_to(Commented reply_to) {
        this.reply_to = reply_to;
    }
}
