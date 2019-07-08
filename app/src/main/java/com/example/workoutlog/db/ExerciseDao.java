package com.example.workoutlog.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workoutlog.model.Exercise;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertExercise(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertExerciseList(List<Exercise> exerciseList);

    @Query("SELECT * FROM exercise_table WHERE workoutName =:name")
    LiveData<List<Exercise>> getExerciseList(String name);

    @Query("UPDATE exercise_table SET reps = :reps WHERE id =:id")
    void updateRepsList(List<String> reps, String id );

    @Query("UPDATE exercise_table SET weights = :weights WHERE id =:id")
    void updateWeightList(List<String> weights, String id);

    @Query("UPDATE exercise_table SET name = :name WHERE id =:id")
    void updateName(String name, String id);
}
