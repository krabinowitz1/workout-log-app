package com.example.workoutlog.db;

import android.app.Application;
import android.os.AsyncTask;

import com.example.workoutlog.model.LogEntry;

public class WorkoutLogEntryRepository {
    private LogEntryDao mLogEntryDao;

    public WorkoutLogEntryRepository(Application application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        mLogEntryDao = db.workoutLogEntryDao();
    }

    public void insert(LogEntry logEntry) {
        new insertAsyncTask(mLogEntryDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<LogEntry, Void, Void> {
        private LogEntryDao logEntryDao;
        insertAsyncTask(LogEntryDao dao) {
            logEntryDao = dao;
        }

        @Override
        protected Void doInBackground(LogEntry... workoutLogEntries) {
            logEntryDao.insertWorkoutLogEntry(workoutLogEntries[0]);
            return null;
        }
    }
}
