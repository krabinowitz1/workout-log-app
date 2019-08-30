package com.example.workoutlog.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.workoutlog.db.WorkoutLogEntryRepository;
import com.example.workoutlog.model.LogEntry;
import com.example.workoutlog.ui.ResponseListener;

public class LogEntryViewModel extends AndroidViewModel {
    private WorkoutLogEntryRepository mRepository;

    public LogEntryViewModel(Application application) {
        super(application);
        mRepository = new WorkoutLogEntryRepository(application);
    }

    public void insertWorkoutLogEntry(LogEntry entry, ResponseListener responseListener) {
        mRepository.insert(entry);
    }
}
