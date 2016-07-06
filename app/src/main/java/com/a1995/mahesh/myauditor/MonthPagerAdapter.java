package com.a1995.mahesh.myauditor;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mahesh on 24/6/16.
 */
public class MonthPagerAdapter extends FragmentPagerAdapter {
    private int pageCount = 10;
    private Context mContext;

    public MonthPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return MonthPageFragment.newInstance();
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "This Month";
    }
}
