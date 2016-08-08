package cn.cfanr.izhihudaily.model;

/**
 * @author xifan
 * @time 2016/5/22
 * @desc 被评论者数据模型
 */
public class CommentedModel {

    /**
     * content : 说真的，我真欣赏不了那些所谓的艺术……
     * status : 0
     * id : 25032539
     * author : 泪吻雨
     */

    private String content;
    private int status;  //0为正常，1的时候会返回 字段error_msg: "抱歉，原点评已经被删除"，其他字段不返回
    private String id;
    private String author;
    private String error_msg;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
