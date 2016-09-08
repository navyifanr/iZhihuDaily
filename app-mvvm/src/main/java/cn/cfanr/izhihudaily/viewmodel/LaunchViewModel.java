package cn.cfanr.izhihudaily.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

/**
 * @author xifan
 *         date: 2016/8/15
 *         desc:
 */
public class LaunchViewModel implements ViewModel {

    private ObservableField<String> author;

    private Context context;

    public LaunchViewModel(Context context){
        this.context=context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
