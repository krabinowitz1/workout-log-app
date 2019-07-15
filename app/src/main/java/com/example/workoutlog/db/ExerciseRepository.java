package com.example.workoutlog.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseWithSets;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExerciseRepository {
    private ExerciseDao mExerciseDao;
    private LiveData<List<Exercise>> mExerciseList;
    private LiveData<List<ExerciseWithSets>> mExerciseWithSetList;

    public interface AsyncResponse {
        void processFinish(List<ExerciseWithSets> output);
    }

    public ExerciseRepository(Application application, String mParam) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        mExerciseDao = db.exerciseDao();
        mExerciseList = mExerciseDao.getExerciseList(mParam);
        mExerciseWithSetList = mExerciseDao.getExercisesWithSets(mParam);
    }

    public LiveData<List<ExerciseWithSets>> getExerciseWithSetsList() {
        return mExerciseWithSetList;
    }

    public void insertExercise(Exercise exercise) {
        new insertExerciseAsyncTask(mExerciseDao).execute(exercise);
    }

    public void insertExerciseList(List<Exercise> exerciseList) {
        new insertExercisesAsyncTask(mExerciseDao).execute(exerciseList);
    }

    public void updateRepsList(List<String> repsList, long id) {
        new updateRepsAsyncTask(mExerciseDao, id).execute(repsList);
    }

    public void updateWeightList(List<String> weightList, long id) {
        new updateWeightAsyncTask(mExerciseDao, id).execute(weightList);
    }

    public void updateExerciseName(String name, long id) {
        new updateNameAsyncTask(mExerciseDao, id).execute(name);
    }

    public void updateExercise(Exercise exercise) {

    }

    private static class updateExerciseTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        updateExerciseTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Exercise... exercises) {
            mAsyncTaskDao.updateExercise(exercises[0]);
            return null;
        }
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
            List<Exercise> list = lists[0];
            for(Exercise e : list) {
                long id = mAsyncTaskDao.insertExercise(e);

                for(ExerciseSet exerciseSet : e.exerciseSetList) {
                    exerciseSet.exerciseId = id;
                }

                mAsyncTaskDao.insertExerciseSetList(e.exerciseSetList);
            }
            // mAsyncTaskDao.insertExerciseList(lists[0]);
            return null;
        }
    }

    /*
    private static class getExercisesWithSetsAsyncTask extends AsyncTask<Void, Void, List<ExerciseWithSets>> {
        private ExerciseDao mAsyncTaskDao;
        private String name;
        private AsyncResponse asyncResponse;

        getExercisesWithSetsAsyncTask(ExerciseDao dao, String name, AsyncResponse asyncResponse) {
            mAsyncTaskDao = dao;
            this.name = name;
            this.asyncResponse = asyncResponse;
        }
        @Override
        protected List<ExerciseWithSets> doInBackground(Void... voids) {
            return mAsyncTaskDao.getExercisesWithSets(name);
        }

        @Override
        protected void onPostExecute(List<ExerciseWithSets> exerciseWithSets) {
            asyncResponse.processFinish(exerciseWithSets);
        }
    }

*/
    private static class updateRepsAsyncTask extends AsyncTask<List<String>, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private long id;

        updateRepsAsyncTask(ExerciseDao dao, long id) {
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
        private long id;

        updateWeightAsyncTask(ExerciseDao dao, long id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Void doInBackground(List<String>... lists) {
            mAsyncTaskDao.updateWeightList(lists[0], id);
            return null;
        }
    }

    private static class updateNameAsyncTask extends AsyncTask<String, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private long id;

        updateNameAsyncTask(ExerciseDao dao, long id) {
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