package cn.cfanr.izhihudaily.widget.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @author xifan
 * @time 2016/5/11
 * @desc RecyclerView设置Header/Footer所用到的工具类
 */
public class RecyclerViewUtils {
    /**
     * 设置HeaderView
     *
     * @param recyclerView
     * @param view
     */
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof ExpandRecyclerViewAdapter)) {
            return;
        }

        ExpandRecyclerViewAdapter headerAndFooterAdapter = (ExpandRecyclerViewAdapter) outerAdapter;
        if (headerAndFooterAdapter.getHeaderViewsCount() == 0) {
            headerAndFooterAdapter.addHeaderView(view);
        }
    }

    /**
     * 设置FooterView
     *
     * @param recyclerView
     * @param view
     */
    public static void setFooterView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof ExpandRecyclerViewAdapter)) {
            return;
        }

        ExpandRecyclerViewAdapter headerAndFooterAdapter = (ExpandRecyclerViewAdapter) outerAdapter;
        if (headerAndFooterAdapter.getFooterViewsCount() == 0) {
            headerAndFooterAdapter.addFooterView(view);
        }
    }

    /**
     * 移除FooterView
     *
     * @param recyclerView
     */
    public static void removeFooterView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof ExpandRecyclerViewAdapter) {

            int footerViewCounter = ((ExpandRecyclerViewAdapter) outerAdapter).getFooterViewsCount();
            if (footerViewCounter > 0) {
                View footerView = ((ExpandRecyclerViewAdapter) outerAdapter).getFooterView();
                ((ExpandRecyclerViewAdapter) outerAdapter).removeFooterView(footerView);
            }
        }
    }

    /**
     * 移除HeaderView
     *
     * @param recyclerView
     */
    public static void removeHeaderView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof ExpandRecyclerViewAdapter) {

            int headerViewCounter = ((ExpandRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                View headerView = ((ExpandRecyclerViewAdapter) outerAdapter).getHeaderView();
                ((ExpandRecyclerViewAdapter) outerAdapter).removeFooterView(headerView);
            }
        }
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getLayoutPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof ExpandRecyclerViewAdapter) {

            int headerViewCounter = ((ExpandRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }

        return holder.getLayoutPosition();
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getAdapterPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof ExpandRecyclerViewAdapter) {

            int headerViewCounter = ((ExpandRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - headerViewCounter;
            }
        }

        return holder.getAdapterPosition();
    }
}