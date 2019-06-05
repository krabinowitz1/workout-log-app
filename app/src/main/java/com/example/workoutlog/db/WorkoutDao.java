package com.example.workoutlog.db;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.Workout;

import java.util.List;

@Dao
public abstract class WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertWorkout(Workout workout);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertExerciseList(List<Exercise> exercises);

    @Delete
    public abstract void deleteWorkout(Workout workout);

    @Query("SELECT * FROM workout_table WHERE name =:name")
    public abstract Workout getWorkout(String name);

    @Query("SELECT * FROM exercise_table WHERE workout =:name")
    public abstract List<Exercise> getExerciseList(String name);

    @Query("SELECT * from workout_table")
    public abstract LiveData<List<Workout>> getAllWorkouts();

    @Query("DELETE FROM workout_table")
    public abstract void deleteAll();

    public void insertWorkoutWithExercise(Workout workout) {
        List<Exercise> exercises = workout.getExercises();
        for(int i = 0; i < exercises.size(); i++) {
            exercises.get(i).setWorkout(workout.getName());
        }
        insertExerciseList(exercises);
        insertWorkout(workout);
    }

    public Workout getWorkoutWithExercises(String name) {
        Workout workout = getWorkout(name);

        return workout;
    }
}
