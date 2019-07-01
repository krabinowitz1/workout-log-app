package com.example.workoutlog.ui;

import android.graphics.Color;
import android.os.Bundle;
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

public class StartWorkoutActivity extends AppCompatActivity {
    private ActivityStartWorkoutBinding binding;
    private ExerciseViewModel mExerciseViewModel;
    private String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        binding.startWorkoutExercisesList.setAdapter(adapter);
        binding.startWorkoutExercisesList.setLayoutManager(new LinearLayoutManager(this));
        mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModelFactory(getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseViewModel.getExerciseList().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exerciseList) {
                adapter.setExercises(exerciseList);
            }
        });
    }
}
