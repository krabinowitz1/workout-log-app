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
public interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkout(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);

    @Query("SELECT * FROM workout_table WHERE name =:name")
    Workout getWorkout(String name);

    @Query("SELECT * from workout_table")
    LiveData<List<Workout>> getAllWorkouts();

    @Query("DELETE FROM workout_table")
    void deleteAll();
}
