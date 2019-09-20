package com.example.workoutlog.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "exercise_performed_draft_table")
public class ExercisePerformedDraft {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String workoutName;
    private String name;
    private int numSets;

    @Ignore
    public List<ExerciseSetWithHint> exerciseSetWithHintList;

    public ExercisePerformedDraft(String name, int numSets) {
        this.setName(name);
        this.setNumSets(numSets);
        exerciseSetWithHintList = new ArrayList<>();
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

    public List<ExerciseSetWithHint> getExerciseSetWithHintList() {
        return exerciseSetWithHintList;
    }

    public void setExerciseSetWithHintList(List<ExerciseSetWithHint> exerciseSetWithHintList) {
        this.exerciseSetWithHintList = exerciseSetWithHintList;
    }
}
