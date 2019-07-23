package com.example.workoutlog.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.workoutlog.db.ExerciseRepository;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseWithSets;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository mRepository;
    private LiveData<List<ExerciseWithSets>> mExerciseWithSetList;
    private String mParam;

    public ExerciseViewModel(@NonNull Application application, String param) {
        super(application);
        mRepository = new ExerciseRepository(application, param);
        mExerciseWithSetList = mRepository.getExerciseWithSetsList();
        mParam = param;
    }


    public LiveData<List<ExerciseWithSets>> getExerciseWithSetList() {
        return mExerciseWithSetList;
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

    public void insertExerciseSet(ExerciseSet exerciseSet) {
        mRepository.insertExerciseSet(exerciseSet);
    }

    public void addSet(Exercise exercise) {
        mRepository.updateExercise(exercise);
    }

    public void updateExerciseName(Exercise exercise) {
        mRepository.updateExerciseName(exercise.name, exercise.getId());
    }

    public void updateExerciseSet(ExerciseSet exerciseSet) {
        mRepository.updateExerciseSet(exerciseSet);
    }

    public static class ExerciseViewModelFactory implements ViewModelProvider.Factory {
        private Application mApplication;
        private String mParam;

        public ExerciseViewModelFactory(Application application, String param) {
            mApplication = application;
            mParam = param;
        }
        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ExerciseViewModel(mApplication, mParam);
        }
    }

}

