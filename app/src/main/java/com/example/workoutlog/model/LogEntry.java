package com.example.workoutlog.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "log_entry_table")
public class LogEntry {
    @PrimaryKey
    public int id;
    public String timestamp;
}
