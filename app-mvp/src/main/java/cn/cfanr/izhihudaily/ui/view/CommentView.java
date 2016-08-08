package cn.cfanr.izhihudaily.ui.view;

import java.util.List;

import cn.cfanr.izhihudaily.model.CommentModel;
import cn.cfanr.izhihudaily.core.mvp.MvpView;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public interface CommentView extends MvpView {
    void setLongCommentsData(List<CommentModel> longCommentList);

    void setShortCommentsData(List<CommentModel> shortCommentList);
}
