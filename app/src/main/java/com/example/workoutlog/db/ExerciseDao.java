package com.example.workoutlog.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseWithSets;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertExercise(Exercise exercise);

    @Insert
    void insertExerciseSetList(List<ExerciseSet> exerciseSetList);

    @Transaction
    @Query("SELECT * FROM exercise_table where workoutName =:name AND  name =:exerciseName")
    LiveData<ExerciseWithSets> getExerciseWithSets(String name, String exerciseName);

    @Transaction
    @Query("SELECT * FROM exercise_table WHERE workoutName =:name")
    LiveData<List<ExerciseWithSets>> getExercisesWithSets(String name);

    @Query("SELECT * FROM exercise_table WHERE workoutName =:name")
    LiveData<List<Exercise>> getExerciseList(String name);

    @Query("UPDATE exercise_table SET reps = :reps WHERE id =:id")
    void updateRepsList(List<String> reps, long id );

    @Query("UPDATE exercise_table SET weights = :weights WHERE id =:id")
    void updateWeightList(List<String> weights, long id);

    @Query("UPDATE exercise_table SET name = :name WHERE id =:id")
    void updateName(String name, long id);

    @Update
    void updateExercise(Exercise exercise);
}
