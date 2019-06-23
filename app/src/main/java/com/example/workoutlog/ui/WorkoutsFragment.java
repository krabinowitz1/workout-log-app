package com.example.workoutlog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.workoutlog.databinding.FragmentWorkoutsListBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.Workout;
import com.example.workoutlog.viewmodel.ExerciseViewModel;
import com.example.workoutlog.viewmodel.ExerciseViewModelFactory;
import com.example.workoutlog.viewmodel.WorkoutViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WorkoutsFragment extends Fragment implements View.OnClickListener {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WorkoutViewModel mWorkoutViewModel;
    private ExerciseViewModel mExerciseViewModel;
    private List<Workout> mWorkouts;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NewWorkoutActivity.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentWorkoutsListBinding binding = FragmentWorkoutsListBinding.inflate(inflater, container, false);
        binding.setListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        final WorkoutsAdapter adapter = new WorkoutsAdapter();
        adapter.setOnItemClickListener(new WorkoutsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String workoutName = mWorkouts.get(position).getName();
                Intent intent = new Intent(getActivity(), StartWorkoutActivity.class);
                intent.putExtra("workoutName", workoutName);
                startActivity(intent);
            }
        });
        binding.recWorkouts.setLayoutManager(linearLayoutManager);
        binding.recWorkouts.setAdapter(adapter);
        mWorkoutViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);

        mWorkoutViewModel.getAllWorkouts().observe(this, new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                for(Workout w : workouts)
                    Log.d("DEBUG", w.getName());
                adapter.setWorkouts(workouts);
                mWorkouts = adapter.getWorkouts();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<Exercise> exercises = data.getParcelableArrayListExtra("exercises");
            Workout workout = new Workout(data.getStringExtra("workout"), exercises);

            mExerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModelFactory(getActivity().getApplication(), workout.getName())).get(ExerciseViewModel.class);
            mExerciseViewModel.insertExerciseList(exercises);


            mWorkoutViewModel.insert(workout);
        }
    }
}
