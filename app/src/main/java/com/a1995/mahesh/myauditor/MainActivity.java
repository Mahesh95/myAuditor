package com.a1995.mahesh.myauditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MonthPagerAdapter mAdapter;
    private List<String> monthList;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MonthPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        updateUI();
        viewPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void updateUI() {
        monthList = DatabaseLab.get(getApplicationContext()).getMonthsList();
        mAdapter.setMonthsList(monthList);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}

