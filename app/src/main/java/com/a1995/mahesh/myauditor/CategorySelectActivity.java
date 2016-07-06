package com.a1995.mahesh.myauditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by mahesh on 3/7/16.
 */
public class CategorySelectActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CategorySelectActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.category_list_toolbar);
        setSupportActionBar(toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.category_list_activity_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.category_list_view_pager);
        mViewPager.setAdapter(new CategoriesPageAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
