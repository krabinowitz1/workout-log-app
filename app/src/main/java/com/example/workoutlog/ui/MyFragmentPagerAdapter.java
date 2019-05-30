package com.example.workoutlog.ui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.workoutlog.R;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private final int PAGE_COUNT = 2;
    private String tabTitles[];

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = new String[]{context.getString(R.string.tab_item1), context.getString(R.string.tab_item2)};
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? new WorkoutsFragment() : new LogsFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
