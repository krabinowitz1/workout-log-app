package com.example.workoutlog.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityStartWorkoutBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseWithSets;
import com.example.workoutlog.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class StartWorkoutActivity extends AppCompatActivity implements OnUpdateExerciseListener {
    private ActivityStartWorkoutBinding binding;
    private ExerciseViewModel mExerciseViewModel;
    private String workoutName;

    private boolean shouldNotifyAdapter;

    private ExerciseAdapter mExerciseAdapter;
    private ArrayList<Integer> mViewTypeList;
    private ArrayList<Integer> mTopSectionPositions;
    private ArrayList<Exercise> mExerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShouldNotifyAdapter(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_workout);
        setUpToolbar();
        loadRecyclerView();
    }

    private void setUpToolbar() {
        setSupportActionBar(binding.activityStartWorkoutToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24px);
        workoutName = getIntent().getStringExtra("workoutName");
        getSupportActionBar().setTitle(workoutName);

        binding.activityStartWorkoutToolbar.setTitleTextColor(Color.WHITE);
        binding.activityStartWorkoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartWorkoutActivity.this, DuringWorkoutActivity.class);
                intent.putExtra("workoutName", workoutName);
                startActivity(intent);
            }
        });
    }

    private void loadRecyclerView() {
        binding.startWorkoutExercisesList.setLayoutManager(new LinearLayoutManager(this));
        binding.startWorkoutExercisesList.setHasFixedSize(true);

        mViewTypeList = new ArrayList<>();
        mExerciseList = new ArrayList<>();
        mTopSectionPositions = new ArrayList<>();

        mExerciseAdapter = new ExerciseAdapter(mViewTypeList, this, mTopSectionPositions);
        binding.startWorkoutExercisesList.setAdapter(mExerciseAdapter);

        mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModel.ExerciseViewModelFactory(getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseViewModel.getExerciseWithSetList().observe(this, new Observer<List<ExerciseWithSets>>() {
            @Override
            public void onChanged(List<ExerciseWithSets> exerciseWithSets) {
                if (mViewTypeList.size() == 0)
                    fillListsWithData(exerciseWithSets);

                mExerciseList.clear();

                for(int i = 0; i < exerciseWithSets.size(); i++) {
                    mExerciseList.add(exerciseWithSets.get(i).exercise);
                    mExerciseList.get(i).exerciseSetList = exerciseWithSets.get(i).exerciseSetList;
                }

                mExerciseAdapter.setExercises(mExerciseList);
                if (shouldNotifyAdapter)
                    mExerciseAdapter.notifyDataSetChanged();
            }
        });
    }

    private void fillListsWithData(List<ExerciseWithSets> exerciseWithSets) {
        int viewTypePosition = 0;

        for (int i = 0; i < exerciseWithSets.size(); i++) {
            mViewTypeList.add(ExerciseAdapter.TopSectionViewHolder.VIEW_TYPE);
            mTopSectionPositions.add(viewTypePosition++);

            for (int j = 0; j < exerciseWithSets.get(i).exerciseSetList.size(); j++) {
                mViewTypeList.add(ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
                viewTypePosition++;
            }

            mViewTypeList.add(ExerciseAdapter.BottomSectionViewHolder.VIEW_TYPE);
            viewTypePosition++;
        }

        mViewTypeList.add(ExerciseAdapter.FooterViewHolder.VIEW_TYPE);
    }

    private void setShouldNotifyAdapter(boolean flag) {
        shouldNotifyAdapter = flag;
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();

        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    @Override
    public void setName(int whichExercise, String data) {
        setShouldNotifyAdapter(false);

        Exercise exercise = mExerciseList.get(whichExercise);
        exercise.name = data;
        mExerciseViewModel.updateExerciseName(exercise);
    }

    @Override
    public void setReps(int whichExercise, int whichSet, String data) {
        setShouldNotifyAdapter(false);

        Exercise exercise = mExerciseList.get(whichExercise);
        exercise.exerciseSetList.get(whichSet).reps = data;

        mExerciseViewModel.updateExerciseSet(exercise.exerciseSetList.get(whichSet));
    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {
        setShouldNotifyAdapter(false);

        Exercise exercise = mExerciseList.get(whichExercise);
        exercise.exerciseSetList.get(whichSet).weight = data;


        mExerciseViewModel.updateExerciseSet(exercise.exerciseSetList.get(whichSet));
    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {
        ArrayList<String> exerciseNames = new ArrayList<>();
        for(int i = 0; i < mExerciseList.size(); i++) {
            String s = mExerciseList.get(i).name;
            if(s.equals("") )
                exerciseNames.add("unnamed exercise");

            else
                exerciseNames.add(s);
        }
        String currentExerciseName = exerciseNames.get(whichExercise);
        exerciseNames.remove(whichExercise);
        closeKeyboard();
        FragmentManager fm = getSupportFragmentManager();
        SupersetDialogFragment supersetDialog = SupersetDialogFragment.newInstance(currentExerciseName, exerciseNames);
        supersetDialog.show(fm, "fragment_superset");
    }

    @Override
    public void addSet(int whichExercise, int position) {
        setShouldNotifyAdapter(true);

        Exercise exercise = mExerciseList.get(whichExercise);
        exercise.numSets++;
        mExerciseViewModel.addSet(exercise);

        ExerciseSet exerciseSet = new ExerciseSet(" ", " ", exercise.numSets);
        exerciseSet.exerciseId = exercise.getId();
        exercise.exerciseSetList.add(exerciseSet);

        for(int i = whichExercise + 1; i < mTopSectionPositions.size(); i++) {
            mTopSectionPositions.set(i, mTopSectionPositions.get(i) + 1);
        }

        mViewTypeList.add(position, ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
        mExerciseViewModel.insertExerciseSet(exerciseSet);
        binding.startWorkoutExercisesList.scrollToPosition(position + 1);
    }

    @Override
    public boolean addExercise(int position) {
        setShouldNotifyAdapter(true);

        Exercise exercise = new Exercise("");
        exercise.workoutName = workoutName;
        exercise.exerciseSetList.add(new ExerciseSet(" ", " ", exercise.numSets));
        mExerciseList.add(exercise);

        mTopSectionPositions.add(position);

        mViewTypeList.add(mViewTypeList.size() - 1, ExerciseAdapter.TopSectionViewHolder.VIEW_TYPE);
        mViewTypeList.add(mViewTypeList.size() - 1, ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
        mViewTypeList.add(mViewTypeList.size() - 1, ExerciseAdapter.BottomSectionViewHolder.VIEW_TYPE);

        mExerciseViewModel.insertExercise(exercise);
        binding.startWorkoutExercisesList.scrollToPosition(mViewTypeList.size() - 1);

        return false;
    }
}