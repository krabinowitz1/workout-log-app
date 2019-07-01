package com.example.workoutlog.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workoutlog.model.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertExercise(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertExerciseList(List<Exercise> exerciseList);

    @Query("SELECT * FROM exercise_table WHERE workoutName =:name")
    LiveData<List<Exercise>> getExerciseList(String name);

    @Query("UPDATE exercise_table SET reps = :reps WHERE name =:name")
    void update(List<String> reps, String name );
}
