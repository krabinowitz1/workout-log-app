package com.example.workoutlog.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityDuringWorkoutBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseWithSets;
import com.example.workoutlog.viewmodel.ExerciseViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class DuringWorkoutActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private ActivityDuringWorkoutBinding mBinding;
    private ExerciseViewModel mExerciseViewModel;
    private String workoutName;
    private ExerciseFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_during_workout);
        workoutName = getIntent().getStringExtra("workoutName");
        setUpToolbar();
        loadViewPager();
    }

    private void setUpToolbar() {
        setSupportActionBar(mBinding.activityDuringWorkoutToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24px);

        mBinding.activityDuringWorkoutToolbar.setTitleTextColor(Color.WHITE);
        mBinding.activityDuringWorkoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadViewPager() {
        pagerAdapter = new ExerciseFragmentPagerAdapter(getSupportFragmentManager(), workoutName, this);
        mBinding.exerciseViewpager.setAdapter(pagerAdapter);

        mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModel.ExerciseViewModelFactory(getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseViewModel.getExerciseWithSetList().observe(this, new Observer<List<ExerciseWithSets>>() {
            @Override
            public void onChanged(List<ExerciseWithSets> exerciseWithSets) {
                pagerAdapter.count = exerciseWithSets.size();
                pagerAdapter.notifyDataSetChanged();

                loadRecyclerView(exerciseWithSets);
            }
        });

    }

    private void loadRecyclerView(List<ExerciseWithSets> exerciseWithSets) {
        ArrayList<Exercise> list = new ArrayList<>();
        for(ExerciseWithSets ews : exerciseWithSets) {
            list.add(ews.exercise);
        }
        SimpleExerciseListAdapter adapter = new SimpleExerciseListAdapter(list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mBinding.exerciseViewpager.setCurrentItem(position, true);
            }
        });
        mBinding.simpleExerciseList.setLayoutManager(new LinearLayoutManager(this));
        mBinding.simpleExerciseList.setAdapter(adapter);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
