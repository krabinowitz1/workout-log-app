package com.example.workoutlog.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExercisePerformed;
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseSetWithHint;
import com.example.workoutlog.model.ExerciseWithSets;
import com.example.workoutlog.model.ExerciseWithSetsAndHints;

import java.util.List;

public class ExerciseRepository {
    private ExerciseDao mExerciseDao;
    private LiveData<List<Exercise>> mExerciseList;
    private LiveData<List<ExerciseWithSets>> mExerciseWithSetList;
    private LiveData<List<ExerciseWithSetsAndHints>> mExerciseWithSetsAndHintsList;

    public ExerciseRepository(Application application, String mParam) {
        WorkoutRoomDatabase db = WorkoutRoomDatabase.getDatabase(application);
        mExerciseDao = db.exerciseDao();
        mExerciseList = mExerciseDao.getExerciseList(mParam);
        mExerciseWithSetList = mExerciseDao.getExercisesWithSets(mParam);
        mExerciseWithSetsAndHintsList = mExerciseDao.getExercisesWithSetsAndHints(mParam);
    }

    public LiveData<List<ExerciseWithSets>> getExerciseWithSetsList() {
        return mExerciseWithSetList;
    }

    public LiveData<List<ExerciseWithSetsAndHints>> getExerciseWithSetsAndHintsList() {
        return mExerciseWithSetsAndHintsList;
    }

    public void insertExercise(Exercise exercise) {
        new insertExerciseAsyncTask(mExerciseDao).execute(exercise);
    }

    public void insertExercisePerformed(ExercisePerformed exerciseLogEntry) {

    }

    public void insertExercisePerformedList(ExercisePerformed exerciseLogEntryList) {

    }

    public void insertExercisePerformedDraft(ExercisePerformedDraft exerciseperformedDraft) {
        new insertExercisePerformedDraftAsyncTask(mExerciseDao).execute(exerciseperformedDraft);
    }

    public void insertExercisePerformedDraftList(List<ExercisePerformedDraft> exercisePerformedDraftList) {
        new insertExercisePerformedDraftListAsyncTask(mExerciseDao).execute(exercisePerformedDraftList);
    }

    public void insertExerciseList(List<Exercise> exerciseList) {
        new insertExercisesAsyncTask(mExerciseDao).execute(exerciseList);
    }

    public void insertExerciseSet(ExerciseSet exerciseSet) {
        new insertExerciseSetTask(mExerciseDao).execute(exerciseSet);
    }

    public void insertExerciseSetWithHint(ExerciseSetWithHint exerciseSetWithHint) {
        new insertExerciseSetWithHintTask(mExerciseDao).execute(exerciseSetWithHint);
    }

    public void updateExerciseName(String name, long id) {
        new updateExerciseNameAsyncTask(mExerciseDao, id).execute(name);
    }

    public void updateExercise(Exercise exercise) {
        new updateExerciseTask(mExerciseDao).execute(exercise);
    }

    public void updateExercisePerformedDraftNumSet(int num, long id) {
        new updateExercisePerformedDraftNumSetAsyncTask(mExerciseDao, id).execute(num);
    }

    public void updateExerciseSet(ExerciseSet exerciseSet) {
        new updateExerciseSetTask(mExerciseDao).execute(exerciseSet);
    }

    public void updateExerciseSetWithHint(ExerciseSetWithHint exerciseSetWithHint) {
        new updateExerciseSetWithHintTask(mExerciseDao).execute(exerciseSetWithHint);
    }

    private static class insertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertExerciseAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            Exercise exercise = exercises[0];
            long id = mAsyncTaskDao.insertExercise(exercise);

            for(ExerciseSet eSet : exercise.exerciseSetList) {
                eSet.exerciseId = id;
                mAsyncTaskDao.insertExerciseSet(eSet);
            }

            return null;
        }
    }

    private static class insertExercisePerformedDraftAsyncTask extends AsyncTask<ExercisePerformedDraft, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertExercisePerformedDraftAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(ExercisePerformedDraft... exercisePerformedDrafts) {
            ExercisePerformedDraft exercisePerformedDraft = exercisePerformedDrafts[0];
            long id = mAsyncTaskDao.insertExercisePerformedDraft(exercisePerformedDraft);

            for(ExerciseSetWithHint eswh : exercisePerformedDraft.exerciseSetWithHintList) {
                eswh.exerciseId = id;
                mAsyncTaskDao.insertExerciseSetWithHint(eswh);
            }

            return null;
        }
    }

    private static class insertExercisePerformedDraftListAsyncTask extends AsyncTask<List<ExercisePerformedDraft>, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertExercisePerformedDraftListAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<ExercisePerformedDraft>... exercisePerformedDrafts) {
            List<ExercisePerformedDraft> list = exercisePerformedDrafts[0];

            for(ExercisePerformedDraft epd : list) {
                long id = mAsyncTaskDao.insertExercisePerformedDraft(epd);

                for (ExerciseSetWithHint eswh : epd.exerciseSetWithHintList) {
                    eswh.exerciseId = id;
                }
                mAsyncTaskDao.insertExerciseSetWithHintList(epd.exerciseSetWithHintList);
            }

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

            return null;
        }
    }

    private static class insertExerciseSetTask extends AsyncTask<ExerciseSet, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertExerciseSetTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ExerciseSet... exerciseSets) {
            mAsyncTaskDao.insertExerciseSet(exerciseSets[0]);
            return null;
        }
    }

    private static class insertExerciseSetWithHintTask extends AsyncTask<ExerciseSetWithHint, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        insertExerciseSetWithHintTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ExerciseSetWithHint... exerciseSetWithHints) {
            mAsyncTaskDao.insertExerciseSetWithHint(exerciseSetWithHints[0]);
            return null;
        }
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

    private static class updateExerciseSetTask extends AsyncTask<ExerciseSet, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        updateExerciseSetTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ExerciseSet... exerciseSets) {
            mAsyncTaskDao.updateExerciseSet(exerciseSets[0]);
            return null;
        }
    }

    private static class updateExerciseSetWithHintTask extends AsyncTask<ExerciseSetWithHint, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        updateExerciseSetWithHintTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ExerciseSetWithHint... exerciseSetWithHints) {
            mAsyncTaskDao.updateExerciseSetWithHint(exerciseSetWithHints[0]);
            return null;
        }
    }

    private static class updateExerciseNameAsyncTask extends AsyncTask<String, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private long id;

        updateExerciseNameAsyncTask(ExerciseDao dao, long id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao.updateExerciseName(strings[0], id);
            return null;
        }
    }

    private static class updateExercisePerformedDraftNumSetAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ExerciseDao mAsyncTaskDao;
        private long id;

        updateExercisePerformedDraftNumSetAsyncTask(ExerciseDao dao, long id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.updateExercisePerformedDraftNumSets(integers[0], id);
            return null;
        }
    }
}