package com.example.workoutlog.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "workout_table")
public class Workout {
    @PrimaryKey
    @NonNull
    public String name;
    public String description;

    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


}
