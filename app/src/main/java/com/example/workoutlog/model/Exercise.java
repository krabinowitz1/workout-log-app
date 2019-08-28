package com.example.workoutlog.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "exercise_table")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String workoutName;

    public String name;

    public int numSets;

    private static final transient String EMPTY_FIELD = " ";

    private static final transient int MINIMUM_SETS = 1;

    @Ignore
    public List<ExerciseSet> exerciseSetList;

    public Exercise(String name) {
        this.name = name;
        numSets = MINIMUM_SETS;
        exerciseSetList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}

