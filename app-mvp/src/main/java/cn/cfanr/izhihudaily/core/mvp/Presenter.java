package cn.cfanr.izhihudaily.core.mvp;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
