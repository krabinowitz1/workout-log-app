package com.example.workoutlog.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.workoutlog.db.ExerciseRepository;
import com.example.workoutlog.model.Exercise;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository mRepository;
    private LiveData<List<Exercise>> mExerciseList;
    private String mParam;

    public ExerciseViewModel(@NonNull Application application, String param) {
        super(application);
        mRepository = new ExerciseRepository(application, param);
        mExerciseList = mRepository.getExerciseList();
        mParam = param;
    }

    public LiveData<List<Exercise>> getExerciseList() {
        return mExerciseList;
    }

    public void insertExercise(Exercise exercise) {
        mRepository.insertExercise(exercise);
    }

    public void insertExerciseList(List<Exercise> exerciseList) {

        for(Exercise e : exerciseList) {
            e.workoutName = mParam;
        }
        mRepository.insertExerciseList(exerciseList);
    }

    public void addSet(Exercise exercise) {
        mRepository.updateRepsAndWeightList(exercise.weights, exercise.reps, exercise.getId());
    }

    public void updateExerciseReps(Exercise exercise) {
        mRepository.updateRepsList(exercise.reps, exercise.getId());
    }

    public void updateExerciseWeight(Exercise exercise) {
        mRepository.updateWeightList(exercise.weights, exercise.getId());
    }

    public void updateExerciseName(Exercise exercise) {
        mRepository.updateExerciseName(exercise.name, exercise.getId());
    }
}
