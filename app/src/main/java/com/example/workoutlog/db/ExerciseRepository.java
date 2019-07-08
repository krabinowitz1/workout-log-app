package com.example.workoutlog.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

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

    public void insertExercise(Exercise exercise) {
        new insertExerciseAsyncTask(mExerciseDao).execute(exercise);
    }

    public void insertExerciseList(List<Exercise> exerciseList) {
        new insertExercisesAsyncTask(mExerciseDao).execute(exerciseList);
    }

    public void updateRepsList(List<String> repsList, String id) {
        new updateRepsAsyncTask(mExerciseDao, id).execute(repsList);
    }

    public void updateWeightList(List<String> weightList, String id) {
        new updateWeightAsyncTask(mExerciseDao, id).execute(weightList);
    }

    public void updateExerciseName(String name, String id) {
        new updateNameAsyncTask(mExerciseDao, id).execute(name);
    }

    public void updateRepsAndWeightList(List<String> weightList, List<String> repsList, String id) {
        new updateRepsAsyncTask(mExerciseDao, id).execute(repsList);
        new updateWeightAsyncTask(mExerciseDao, id).execute(weightList);
    }

    private static class insertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertExerciseAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            mAsyncTaskDao.insertExercise(exercises[0]);
            return null;
        }
    }

    private static class insertExercisesAsyncTask extends AsyncTask<List<Exercise>, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertExercisesAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<Exercise>... lists) {
            mAsyncTaskDao.insertExerciseList(lists[0]);
            return null;
        }
    }

    private static class updateRepsAsyncTask extends AsyncTask<List<String>, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private String id;

        updateRepsAsyncTask(ExerciseDao dao, String id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Void doInBackground(List<String>... lists) {
            mAsyncTaskDao.updateRepsList(lists[0], id);
            return null;
        }
    }

    private static class updateWeightAsyncTask extends AsyncTask<List<String>, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private String id;

        updateWeightAsyncTask(ExerciseDao dao, String id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Void doInBackground(List<String>... lists) {
            mAsyncTaskDao.updateWeightList(lists[0], id);
            return null;
        }
    }

    private static class updateRepsAndWeightAsyncTask extends AsyncTask<List<String>, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private String id;
        updateRepsAndWeightAsyncTask(ExerciseDao dao, String id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }
        @Override
        protected Void doInBackground(List<String>... lists) {
            mAsyncTaskDao.updateRepsList(lists[0], id);
            //mAsyncTaskDao.updateWeightList(lists[1], id);
            return null;
        }
    }

    private static class updateNameAsyncTask extends AsyncTask<String, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private String id;

        updateNameAsyncTask(ExerciseDao dao, String id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao.updateName(strings[0], id);
            return null;
        }
    }
}