package cn.cfanr.izhihudaily.view.RecyclerView;

import android.view.View;

/**
 * @author xifan
 * @time 2016/5/11
 * @desc RecyclerView/ListView/GridView 滑动加载下一页时的回调接口
 */
public interface OnListLoadNextPageListener {
    /**
     * 开始加载下一页
     * @param view 当前RecyclerView/ListView/GridView
     */
    public void onLoadNextPage(View view);
}
