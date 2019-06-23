package com.example.workoutlog.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_workout);
        loadRecyclerView();
    }

    private void loadRecyclerView() {
        final WorkoutRoutineAdapter adapter = new WorkoutRoutineAdapter(new ArrayList<Exercise>());
        binding.startWorkoutExercisesList.setAdapter(adapter);
        binding.startWorkoutExercisesList.setLayoutManager(new LinearLayoutManager(this));
        String workoutName = getIntent().getStringExtra("workoutName");
        mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModelFactory(getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseViewModel.getExerciseList().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exerciseList) {
                adapter.setExercises(exerciseList);
            }
        });
    }
}
