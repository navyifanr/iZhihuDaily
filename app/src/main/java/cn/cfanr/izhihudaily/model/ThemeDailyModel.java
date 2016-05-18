package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 * @time 2016/5/18
 * @desc
 */
public class ThemeDailyModel {
    public static int THEME_DAILY_HEADER=0;
    public static int THEME_DAILY_NEWS=1;
    private int type;
    private String description;
    private String background;
    private List<EditorModel> editorModelList;
    private NewsModel newsModel;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public NewsModel getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(NewsModel newsModel) {
        this.newsModel = newsModel;
    }

    public List<EditorModel> getEditorModelList() {
        return editorModelList;
    }

    public void setEditorModelList(List<EditorModel> editorModelList) {
        this.editorModelList = editorModelList;
    }
}
