package com.example.workoutlog.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.viewpager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewpager, true);
    }

    public static class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        private static final int PAGE_COUNT = 2;
        private static String[] tabTitles;

        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            tabTitles = new String[]{"Workouts", "Logs"};
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

}