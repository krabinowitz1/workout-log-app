package com.example.workoutlog.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_log_entry_table")
public class WorkoutLogEntry {
    @PrimaryKey
    public int id;

    public String timestamp;
}
