package cn.cfanr.izhihudaily.ui.view;

import java.util.List;

import cn.cfanr.izhihudaily.core.mvp.MvpView;
import cn.cfanr.izhihudaily.model.Comment;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public interface CommentView extends MvpView {
    void setLongCommentsData(List<Comment> longCommentList);

    void setShortCommentsData(List<Comment> shortCommentList);
}
