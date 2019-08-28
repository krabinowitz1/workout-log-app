package com.example.workoutlog.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ExerciseFragmentPagerAdapter extends FragmentPagerAdapter {
    private String workoutName;
    public int count;

    public ExerciseFragmentPagerAdapter(FragmentManager fragmentManager, String data) {
        super(fragmentManager);
        workoutName = data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ExerciseFragment.newInstance(workoutName, position);
    }

    @Override
    public int getCount() {
        return count;
    }
}
