package com.example.workoutlog.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.workoutlog.model.Exercise;

import java.util.List;

public class ExerciseRepository {
    private ExerciseDao mExerciseDao;
    private LiveData<List<Exercise>> mExerciseList;

    public ExerciseRepository(Application application, String mParam) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        mExerciseDao = db.exerciseDao();
        mExerciseList = mExerciseDao.getExerciseList(mParam);
    }

    public LiveData<List<Exercise>> getExerciseList() {
        return mExerciseList;
    }

    public void insertExerciseList(List<Exercise> exerciseList) {
        new insertAsyncTask(mExerciseDao).execute(exerciseList);
    }

    private static class insertAsyncTask extends AsyncTask<List<Exercise>, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<Exercise>... lists) {
            mAsyncTaskDao.insertExerciseList(lists[0]);
            return null;
        }
    }

}
