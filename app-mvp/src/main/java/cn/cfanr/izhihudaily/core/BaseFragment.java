package cn.cfanr.izhihudaily.core;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xifan
 */
public abstract class BaseFragment extends Fragment {

    private View layoutView;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutResId(), container, false);
        initPresenter();
        initViews(layoutView);
        return layoutView;
    }

    public abstract int getLayoutResId();

    protected abstract void initPresenter();

    public abstract void initViews(View layoutView);

    @SuppressWarnings("unchecked")
    public <T extends View>T $(View layoutView, @IdRes int resId){
        return (T) layoutView.findViewById(resId);
    }
}
