package com.example.workoutlog.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseWithSets;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Exercise> mExercises;
    String workoutName;
    int count;

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

    public void setExercises(List<ExerciseWithSets> exercises) {
        for(ExerciseWithSets e : exercises) {
            Exercise exercise = e.exercise;
            exercise.exerciseSetList = e.exerciseSetList;
            mExercises.add(exercise);
        }
    }
}
