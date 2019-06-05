package com.example.workoutlog.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.workoutlog.db.WorkoutRepository;
import com.example.workoutlog.model.Workout;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {
    private WorkoutRepository mRepository;
    private LiveData<List<Workout>> mAllWorkouts;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WorkoutRepository(application);
        mAllWorkouts = mRepository.getAllWorkouts();
    }

    public LiveData<List<Workout>> getAllWorkouts() {
        return mAllWorkouts;
    }

    public Workout getWorkoutWithExercises(String name) {
        return mRepository.getWorkout(name);
    }

    public void insert(Workout workout) {
        mRepository.insert(workout);
    }
}
