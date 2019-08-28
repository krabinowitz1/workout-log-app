package com.example.workoutlog.db;

import android.app.Application;
import android.os.AsyncTask;

import com.example.workoutlog.model.WorkoutLogEntry;

public class WorkoutLogEntryRepository {
    private WorkoutLogEntryDao mWorkoutLogEntryDao;

    public WorkoutLogEntryRepository(Application application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        mWorkoutLogEntryDao = db.workoutLogEntryDao();
    }

    public void insert(WorkoutLogEntry workoutLogEntry) {
        new insertAsyncTask(mWorkoutLogEntryDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<WorkoutLogEntry, Void, Void> {
        private WorkoutLogEntryDao workoutLogEntryDao;
        insertAsyncTask(WorkoutLogEntryDao dao) {
            workoutLogEntryDao = dao;
        }

        @Override
        protected Void doInBackground(WorkoutLogEntry... workoutLogEntries) {
            workoutLogEntryDao.insertWorkoutLogEntry(workoutLogEntries[0]);
            return null;
        }
    }
}
