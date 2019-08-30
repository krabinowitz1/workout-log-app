package com.example.workoutlog.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workoutlog.model.LogEntry;

@Dao
public interface LogEntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkoutLogEntry(LogEntry logEntry);

    @Query("DELETE FROM log_entry_table")
    void deleteAll();
}
