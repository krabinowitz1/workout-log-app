package com.example.workoutlog.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.workoutlog.db.ExerciseRepository;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExercisePerformed;
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseSetWithHint;
import com.example.workoutlog.model.ExerciseWithSets;
import com.example.workoutlog.model.ExerciseWithSetsAndHints;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository mRepository;
    private LiveData<List<ExerciseWithSets>> mExerciseWithSetList;
    private LiveData<List<ExerciseWithSetsAndHints>> mExerciseWithSetsAndHintsList;
    private String mParam;

    public ExerciseViewModel(@NonNull Application application, String param) {
        super(application);
        mRepository = new ExerciseRepository(application, param);
        mExerciseWithSetList = mRepository.getExerciseWithSetsList();
        mExerciseWithSetsAndHintsList = mRepository.getExerciseWithSetsAndHintsList();
        mParam = param;
    }


    public LiveData<List<ExerciseWithSets>> getExerciseWithSetList() {
        return mExerciseWithSetList;
    }

    public LiveData<List<ExerciseWithSetsAndHints>> getExerciseWithSetsAndHintsList() {
        return mExerciseWithSetsAndHintsList;
    }

    public void insertExercise(Exercise exercise) {
        mRepository.insertExercise(exercise);
    }

    public void insertExercisePerformedDraft(ExercisePerformedDraft exercisePerformedDraft) {
        mRepository.insertExercisePerformedDraft(exercisePerformedDraft);
    }

    public void insertExercisePerformedDraftList(List<ExercisePerformedDraft> exercisePerformedDraftList) {
        for(ExercisePerformedDraft epd : exercisePerformedDraftList) {
            epd.setWorkoutName(mParam);
        }

        mRepository.insertExercisePerformedDraftList(exercisePerformedDraftList);
    }

    public void insertExerciseList(List<Exercise> exerciseList) {
        for(Exercise e : exerciseList) {
            e.setWorkoutName(mParam);
        }

        mRepository.insertExerciseList(exerciseList);
    }

    public void insertExercisePerformed(ExercisePerformed exerciseLogEntry) {

    }

    public void insertExercisePerformedList(ExercisePerformed exerciseLogEntryList) {

    }

    public void insertExerciseSet(ExerciseSet exerciseSet) {
        mRepository.insertExerciseSet(exerciseSet);
    }

    public void insertExerciseSetWithHint(ExerciseSetWithHint exerciseSetWithHint) {
        mRepository.insertExerciseSetWithHint(exerciseSetWithHint);
    }

    public void updateExerciseNumSet(Exercise exercise) {
        mRepository.updateExercise(exercise);
    }

    public void updateExercisePerformedDraftNumSet(ExercisePerformedDraft exercisePerformedDraft) {
        mRepository.updateExercisePerformedDraftNumSet(exercisePerformedDraft.getNumSets(), exercisePerformedDraft.getId());
    }

    public void updateExerciseName(Exercise exercise) {
        mRepository.updateExerciseName(exercise.getName(), exercise.getId());
    }

    public void updateExerciseSet(ExerciseSet exerciseSet) {
        mRepository.updateExerciseSet(exerciseSet);
    }

    public void updateExerciseSetWithHint(ExerciseSetWithHint exerciseSetWithHint) {
        mRepository.updateExerciseSetWithHint(exerciseSetWithHint);
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

