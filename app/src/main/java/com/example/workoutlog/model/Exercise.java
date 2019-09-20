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
    private int id;
    private String workoutName;
    private String name;
    private int numSets;
    private static final transient int MINIMUM_SETS = 1;

    @Ignore
    private List<ExerciseSet> exerciseSetList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumSets() {
        return numSets;
    }

    public void setNumSets(int numSets) {
        this.numSets = numSets;
    }

    public void setExerciseSetList(List<ExerciseSet> exerciseSetList) {
        this.exerciseSetList = exerciseSetList;
    }

    public List<ExerciseSet> getExerciseSetList() {
        return exerciseSetList;
    }
}

