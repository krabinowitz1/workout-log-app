package com.example.workoutlog.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workoutlog.model.WorkoutLogEntry;

@Dao
public interface WorkoutLogEntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkoutLogEntry(WorkoutLogEntry workoutLogEntry);

    @Query("DELETE FROM workout_log_entry_table")
    void deleteAll();
}
