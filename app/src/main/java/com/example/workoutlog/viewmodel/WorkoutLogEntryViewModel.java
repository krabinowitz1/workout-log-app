package com.example.workoutlog.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.workoutlog.db.WorkoutLogEntryRepository;
import com.example.workoutlog.model.WorkoutLogEntry;
import com.example.workoutlog.ui.ResponseListener;

public class WorkoutLogEntryViewModel extends AndroidViewModel {
    private WorkoutLogEntryRepository mRepository;

    public WorkoutLogEntryViewModel(Application application) {
        super(application);
        mRepository = new WorkoutLogEntryRepository(application);
    }

    public void insertWorkoutLogEntry(WorkoutLogEntry entry, ResponseListener responseListener) {
        mRepository.insert(entry);
    }
}
