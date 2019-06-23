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
import com.example.workoutlog.model.Workout;

@Database(entities = {Workout.class, Exercise.class}, version = 1, exportSchema = false)
@TypeConverters({MyTypeConverters.class})
public abstract class WorkoutRoomDatabase extends RoomDatabase {
    private static volatile WorkoutRoomDatabase INSTANCE;

    static WorkoutRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (WorkoutRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WorkoutRoomDatabase.class, "workout_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();

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

        PopulateDbAsync(WorkoutRoomDatabase db) {
            mWorkoutDao = db.workoutDao();
            mExerciseDao = db.exerciseDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mWorkoutDao.deleteAll();
            mWorkoutDao.insertWorkout(new Workout("Workout A"));
            mWorkoutDao.insertWorkout(new Workout("Workout B"));
            mWorkoutDao.insertWorkout(new Workout("Workout C"));
            return null;
        }
    }

}
