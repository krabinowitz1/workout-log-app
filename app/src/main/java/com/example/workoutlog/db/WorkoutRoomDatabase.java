package com.example.workoutlog.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseSetWithHint;
import com.example.workoutlog.model.Workout;
import com.example.workoutlog.model.WorkoutLogEntry;

@Database(entities = {Workout.class, Exercise.class, ExercisePerformedDraft.class, ExerciseSet.class, ExerciseSetWithHint.class, WorkoutLogEntry.class}, version = 6, exportSchema = false)
@TypeConverters({MyTypeConverters.class})
public abstract class WorkoutRoomDatabase extends RoomDatabase {
    private static volatile WorkoutRoomDatabase INSTANCE;

    static WorkoutRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (WorkoutRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WorkoutRoomDatabase.class, "workout_database").addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();
    public abstract WorkoutLogEntryDao workoutLogEntryDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WorkoutDao mWorkoutDao;
        private final ExerciseDao mExerciseDao;
        private final WorkoutLogEntryDao mWorkoutLogEntryDao;

        PopulateDbAsync(WorkoutRoomDatabase db) {
            mWorkoutDao = db.workoutDao();
            mExerciseDao = db.exerciseDao();
            mWorkoutLogEntryDao = db.workoutLogEntryDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mExerciseDao.deleteAll();
            mExerciseDao.deleteAllExerciseDrafts();
            mWorkoutDao.deleteAll();
            mWorkoutLogEntryDao.deleteAll();
            mWorkoutDao.insertWorkout(new Workout("Workout A"));
            mWorkoutDao.insertWorkout(new Workout("Workout B"));
            mWorkoutDao.insertWorkout(new Workout("Workout C"));
            return null;
        }
    }

}
