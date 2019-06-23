package com.example.workoutlog.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.workoutlog.model.Workout;

import java.util.List;

public class WorkoutRepository {
    private WorkoutDao mWorkoutDao;
    private LiveData<List<Workout>> mAllWorkouts;

    public WorkoutRepository(Application application) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        mWorkoutDao = db.workoutDao();
        mAllWorkouts = mWorkoutDao.getAllWorkouts();
    }

    public LiveData<List<Workout>> getAllWorkouts() {
        return mAllWorkouts;
    }

    public Workout getWorkout(String name) {
        return mWorkoutDao.getWorkout(name);
    }

    public void insert(Workout workout) {
        new insertAsyncTask(mWorkoutDao).execute(workout);
    }

    private static class insertAsyncTask extends AsyncTask<Workout, Void, Void> {
        private WorkoutDao mAsyncTaskDao;

        insertAsyncTask(WorkoutDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Workout... params) {
            mAsyncTaskDao.insertWorkout(params[0]);
            return null;
        }
    }
}
