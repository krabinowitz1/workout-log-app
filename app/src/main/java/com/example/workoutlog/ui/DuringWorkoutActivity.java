package com.example.workoutlog.ui;

import android.graphics.Color;
import android.os.Bundle;
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
import com.example.workoutlog.model.ExerciseWithSetsAndHints;
import com.example.workoutlog.model.LogEntry;
import com.example.workoutlog.viewmodel.ExerciseViewModel;
import com.example.workoutlog.viewmodel.LogEntryViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class DuringWorkoutActivity extends AppCompatActivity implements View.OnFocusChangeListener, OnUpdateExerciseListener {
    private ActivityDuringWorkoutBinding mBinding;
    private ExerciseViewModel mExerciseViewModel;
    private ExerciseFragmentPagerAdapter pagerAdapter;
    private LogEntryViewModel mLogEntryViewModel;

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
        ArrayList<ExercisePerformedDraft> list = new ArrayList<>();
        for(ExerciseWithSetsAndHints ewsah : exerciseWithSetsAndHints) {
            list.add(ewsah.exercisePerformedDraft);
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

    @Override
    public void setName(int whichExercise, String data) {

    }

    @Override
    public void setReps(int whichExercise, int whichSet, String data) {

    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {

    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {

    }

    @Override
    public void addSet(int whichExercise, int position) {

    }

    @Override
    public boolean addExercise(int position) {
        return false;
    }
}
