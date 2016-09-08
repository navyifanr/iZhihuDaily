package cn.cfanr.izhihudaily.presenter;

import android.support.annotation.NonNull;

import cn.cfanr.izhihudaily.app.RetrofitManager;
import cn.cfanr.izhihudaily.core.mvp.BasePresenter;
import cn.cfanr.izhihudaily.model.CommentList;
import cn.cfanr.izhihudaily.ui.view.CommentView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public class CommentPresenter extends BasePresenter<CommentView> {
    private CommentView commentView;

    public CommentPresenter(@NonNull CommentView commentView){
        this.commentView=commentView;
    }

    public void loadLongCommentData(String articleId){
        RetrofitManager.builder().loadLongCommentData(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CommentList>() {
                    @Override
                    public void call(CommentList commentList) {
                        commentView.setLongCommentsData(commentList.getComments());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public void loadShortCommentData(String articleId){
        RetrofitManager.builder().loadShortCommentData(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CommentList>() {
                    @Override
                    public void call(CommentList commentList) {
                        commentView.setShortCommentsData(commentList.getComments());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
