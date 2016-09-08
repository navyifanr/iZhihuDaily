package cn.cfanr.izhihudaily.core.mvp;

import cn.cfanr.izhihudaily.app.AppController;

public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        AppController.getInstance().cancelPendingRequests(this.getClass().getSimpleName());
        this.mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    public String getClassName(){
        return this.getClass().getSimpleName();
    }
}
