package com.example.workoutlog.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ExerciseFragmentPagerAdapter extends FragmentPagerAdapter {
    private String workoutName;
    public int count;

    View.OnFocusChangeListener listener;

    public ExerciseFragmentPagerAdapter(FragmentManager fragmentManager, String data, View.OnFocusChangeListener listener) {
        super(fragmentManager);
        workoutName = data;

        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ExerciseFragment.newInstance(workoutName, position, listener);
    }

    @Override
    public int getCount() {
        return count;
    }
}
