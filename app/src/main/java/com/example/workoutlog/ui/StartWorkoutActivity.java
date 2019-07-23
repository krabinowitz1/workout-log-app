package com.example.workoutlog.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    private List<Exercise> exercises;

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
                startActivity(intent);
            }
        });
    }

    private void loadRecyclerView() {
        final WorkoutRoutineAdapter adapter = new WorkoutRoutineAdapter(new ArrayList<Exercise>());
        adapter.setOnUpdateExerciseListener(StartWorkoutActivity.this);


        binding.startWorkoutExercisesList.setAdapter(adapter);
        binding.startWorkoutExercisesList.setLayoutManager(new LinearLayoutManager(this));

        mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModel.ExerciseViewModelFactory(getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseViewModel.getExerciseWithSetList().observe(this, new Observer<List<ExerciseWithSets>>() {
            @Override
            public void onChanged(List<ExerciseWithSets> exerciseWithSets) {
                if(shouldNotifyAdapter) {
                    adapter.setExercises(exerciseWithSets);
                }

                exercises = adapter.getExercises();
            }
        });
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


        Exercise exercise = exercises.get(whichExercise);
        exercise.name = data;
        mExerciseViewModel.updateExerciseName(exercise);
    }

    @Override
    public void setReps(int whichExercise, int whichSet, String data) {
        setShouldNotifyAdapter(false);


        Exercise exercise = exercises.get(whichExercise);
        exercise.exerciseSetList.get(whichSet).reps = data;
        mExerciseViewModel.updateExerciseSet(exercise.exerciseSetList.get(whichSet));
    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {
        setShouldNotifyAdapter(false);


        Exercise exercise = exercises.get(whichExercise);
        exercise.exerciseSetList.get(whichSet).weight = data;
        mExerciseViewModel.updateExerciseSet(exercise.exerciseSetList.get(whichSet));
    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {
        ArrayList<String> exerciseNames = new ArrayList<>();
        for(int i = 0; i < exercises.size(); i++) {
            String s = exercises.get(i).name;
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
    public void addSet(int whichExercise) {
        setShouldNotifyAdapter(true);


        Exercise exercise = exercises.get(whichExercise);
        exercise.numSets++;
        mExerciseViewModel.addSet(exercise);


        ExerciseSet exerciseSet = new ExerciseSet(" ", " ", exercise.numSets);
        exerciseSet.exerciseId = exercise.getId();
        exercise.exerciseSetList.add(exerciseSet);


        mExerciseViewModel.insertExerciseSet(exerciseSet);
    }

    @Override
    public boolean addExercise() {
        setShouldNotifyAdapter(true);


        Exercise exercise = new Exercise("");
        exercise.workoutName = workoutName;
        exercise.exerciseSetList.add(new ExerciseSet(" ", " ", exercise.numSets));
        mExerciseViewModel.insertExercise(exercise);

        return false;
    }
}