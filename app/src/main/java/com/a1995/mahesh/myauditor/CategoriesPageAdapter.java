package com.a1995.mahesh.myauditor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mahesh on 3/7/16.
 * adapter for CategoryListActivity's pagerView
 */
public class CategoriesPageAdapter extends FragmentPagerAdapter {
    private static final int NO_OF_PAGES = 3;

    public CategoriesPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return CategoryListFragment.newInstance("Debt&Loan");
            case 1:
                return CategoryListFragment.newInstance("Expense");
            case 2:
                return CategoryListFragment.newInstance("Income");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NO_OF_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Debt&Loan";
            case 1:
                return "Expense";
            case 2:
                return "Income";
            default:
                return null;
        }
    }
}
