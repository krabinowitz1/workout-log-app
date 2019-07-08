package com.example.workoutlog.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityStartWorkoutBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.viewmodel.ExerciseViewModel;
import com.example.workoutlog.viewmodel.ExerciseViewModelFactory;

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
    }

    private void loadRecyclerView() {
        final WorkoutRoutineAdapter adapter = new WorkoutRoutineAdapter(new ArrayList<Exercise>());

        adapter.setOnUpdateExerciseListener(StartWorkoutActivity.this);


        binding.startWorkoutExercisesList.setAdapter(adapter);
        binding.startWorkoutExercisesList.setLayoutManager(new LinearLayoutManager(this));
        mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModelFactory(getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseViewModel.getExerciseList().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exerciseList) {
                if(shouldNotifyAdapter)
                    adapter.setExercises(exerciseList);

                exercises = exerciseList;
            }
        });
    }

    private void setShouldNotifyAdapter(boolean flag) {
        shouldNotifyAdapter = flag;
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
        exercise.reps.set(whichSet, data);
        mExerciseViewModel.updateExerciseReps(exercise);
    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {
        setShouldNotifyAdapter(false);

        Exercise exercise = exercises.get(whichExercise);
        exercise.weights.set(whichSet, data);
        mExerciseViewModel.updateExerciseWeight(exercise);
    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {

    }

    @Override
    public void addSet(int whichExercise) {
        setShouldNotifyAdapter(false);

        Log.d("KEVIN", "Name: " + exercises.get(whichExercise).name);
        Exercise exercise = exercises.get(whichExercise);
        Log.d("KEVIN", "Name: " + exercise.name);

        exercise.reps.add(" ");
        exercise.weights.add(" ");

        exercise.sets++;
        mExerciseViewModel.updateExerciseReps(exercise);
        mExerciseViewModel.updateExerciseWeight(exercise);
    }

    @Override
    public boolean addExercise() {
        setShouldNotifyAdapter(true);

        Exercise exercise = new Exercise("");
        exercise.workoutName = workoutName;
        mExerciseViewModel.insertExercise(exercise);

        return false;
    }
}