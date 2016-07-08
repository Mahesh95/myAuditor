package com.a1995.mahesh.myauditor;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by mahesh on 24/6/16.
 */
public class MonthPagerAdapter extends FragmentPagerAdapter {

    List<String> mMonthList;
    private Context mContext;

    public MonthPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (mMonthList == null) {
            return MonthPageFragment.newInstance(null);
        }
        return MonthPageFragment.newInstance(mMonthList.get(position));
    }

    @Override
    public int getCount() {
        if (mMonthList == null) {
            return 1;
        } else {
            return mMonthList.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mMonthList == null) {
            return "This Month";
        } else {
            return mMonthList.get(position);
        }
    }

    public void setMonthsList(List<String> monthList) {
        mMonthList = monthList;
    }
}
