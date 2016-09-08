package cn.cfanr.izhihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author xifan
 * @time 2016/5/13
 * @desc
 */
public class ArticlePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public ArticlePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}