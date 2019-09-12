package com.example.workoutlog.ui;

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

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityDuringWorkoutBinding;
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseSetWithHint;
import com.example.workoutlog.model.ExerciseWithSetsAndHints;
import com.example.workoutlog.model.LogEntry;
import com.example.workoutlog.viewmodel.ExerciseViewModel;
import com.example.workoutlog.viewmodel.LogEntryViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class DuringWorkoutActivity extends AppCompatActivity implements View.OnFocusChangeListener, OnItemClickListener {
    private ActivityDuringWorkoutBinding mBinding;
    private ExerciseViewModel mExerciseViewModel;
    private ExerciseFragmentPagerAdapter pagerAdapter;
    private LogEntryViewModel mLogEntryViewModel;

    private ArrayList<ExercisePerformedDraft> mExercisePerformedDrafts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_during_workout);
        mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModel.ExerciseViewModelFactory(getApplication(), getIntent().getStringExtra("workoutName"))).get(ExerciseViewModel.class);
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

    private void createNewWorkoutLogEntry() {
        mLogEntryViewModel = ViewModelProviders.of(this).get(LogEntryViewModel.class);
        mLogEntryViewModel.insertWorkoutLogEntry(new LogEntry(), new ResponseListener() {
            @Override
            public void onComplete(long response) {
            }
        });


    }

    private void loadViewPager() {
        pagerAdapter = new ExerciseFragmentPagerAdapter(getSupportFragmentManager(), getIntent().getStringExtra("workoutName"));
        mBinding.exerciseViewpager.setAdapter(pagerAdapter);

        mExerciseViewModel.getExerciseWithSetsAndHintsList().observe(this, new Observer<List<ExerciseWithSetsAndHints>>() {
            @Override
            public void onChanged(List<ExerciseWithSetsAndHints> exerciseWithSetsAndHints) {
                pagerAdapter.count = exerciseWithSetsAndHints.size();
                pagerAdapter.notifyDataSetChanged();

                loadRecyclerView(exerciseWithSetsAndHints);
            }
        });
    }

    private void loadRecyclerView(List<ExerciseWithSetsAndHints> exerciseWithSetsAndHints) {
        mExercisePerformedDrafts = new ArrayList<>();
        for(int i = 0; i < exerciseWithSetsAndHints.size(); i++) {
            mExercisePerformedDrafts.add(exerciseWithSetsAndHints.get(i).exercisePerformedDraft);
            mExercisePerformedDrafts.get(i).exerciseSetWithHintList = exerciseWithSetsAndHints.get(i).exerciseSetWithHintList;
        }

        SimpleExerciseListAdapter adapter = new SimpleExerciseListAdapter(mExercisePerformedDrafts);
        adapter.setOnItemClickListener(this);

        mBinding.simpleExerciseList.setLayoutManager(new LinearLayoutManager(this));
        mBinding.simpleExerciseList.setAdapter(adapter);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onItemClick(int position) {
        if (position != mExercisePerformedDrafts.size() - 1 )
        mBinding.exerciseViewpager.setCurrentItem(position, true);

        else {
            ExercisePerformedDraft exercisePerformedDraft = new ExercisePerformedDraft("", 1);
            exercisePerformedDraft.workoutName = getIntent().getStringExtra("workoutName");
            exercisePerformedDraft.exerciseSetWithHintList.add(new ExerciseSetWithHint("", "", 1));
            mExerciseViewModel.insertExercisePerformedDraft(exercisePerformedDraft);
        }
    }
}
