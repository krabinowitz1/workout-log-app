package com.example.workoutlog.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseSetWithHint;
import com.example.workoutlog.model.ExerciseWithSets;
import com.example.workoutlog.model.ExerciseWithSetsAndHints;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertExercise(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertExercisePerformedDraft(ExercisePerformedDraft exercisePerformedDraft);

    @Insert
    void insertExerciseSetList(List<ExerciseSet> exerciseSetList);

    @Insert
    void insertExerciseSetWithHintList(List<ExerciseSetWithHint> exerciseSetWithHintList);

    @Insert
    void insertExerciseSet(ExerciseSet exerciseSet);

    @Transaction
    @Query("SELECT * FROM exercise_table WHERE workoutName =:name")
    LiveData<List<ExerciseWithSets>> getExercisesWithSets(String name);

    @Query("SELECT * FROM exercise_table WHERE workoutName =:name")
    LiveData<List<Exercise>> getExerciseList(String name);

    @Query("SELECT * FROM exercise_performed_draft_table WHERE workoutName = :name")
    LiveData<List<ExerciseWithSetsAndHints>> getExercisesWithSetsAndHints(String name);

    @Query("UPDATE exercise_table SET name = :name WHERE id =:id")
    void updateExerciseName(String name, long id);

    @Query("Select COUNT(*) FROM exercise_table WHERE workoutName =:name")
    LiveData<Integer> getExerciseCount(String name);

    @Query("DELETE FROM exercise_table")
    void deleteAll();

    @Query("DELETE FROM exercise_performed_draft_table")
    void deleteAllExerciseDrafts();

    @Update
    void updateExercise(Exercise exercise);

    @Update
    void updateExerciseSet(ExerciseSet exerciseSet);
}
