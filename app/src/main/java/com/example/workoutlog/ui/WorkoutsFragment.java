package com.example.workoutlog.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.workoutlog.model.Workout;
import com.example.workoutlog.viewmodel.WorkoutViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WorkoutsFragment extends Fragment implements View.OnClickListener {
    private static final int NEW_WORKOUT_ACTIVITY_REQUEST_CODE = 1;
    private FragmentWorkoutsListBinding binding;
    private WorkoutViewModel mWorkoutViewModel;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NewWorkoutActivity.class);
        startActivityForResult(intent, NEW_WORKOUT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentWorkoutsListBinding.inflate(inflater, container, false);
        binding.setListener(this);
        loadRecyclerView();

        return binding.getRoot();
    }

    private void loadRecyclerView() {
        final WorkoutsAdapter adapter = new WorkoutsAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String workoutName = adapter.getWorkouts().get(position).getName();
                Intent intent = new Intent(getActivity(), StartWorkoutActivity.class);
                intent.putExtra("workoutName", workoutName);
                startActivity(intent);
            }
        });

        binding.recWorkouts.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recWorkouts.setAdapter(adapter);

        mWorkoutViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        mWorkoutViewModel.getAllWorkouts().observe(this, new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                adapter.setWorkouts(workouts);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_WORKOUT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Workout workout = new Workout(data.getStringExtra("workoutName"), data.getStringExtra("workoutDescription"));

            mWorkoutViewModel.insert(workout);
        }
    }
}
