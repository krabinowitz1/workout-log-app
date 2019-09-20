package com.example.workoutlog.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciseSetWithHint {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int number;
    private String reps;
    private String weight;
    private String repsHint;
    private String weightHint;
    private long exerciseId;

    public ExerciseSetWithHint(String reps, String weight, int number) {
        this.reps = repsHint = reps;
        this.weight = weightHint = weight;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRepsHint() {
        return repsHint;
    }

    public void setRepsHint(String repsHint) {
        this.repsHint = repsHint;
    }

    public String getWeightHint() {
        return weightHint;
    }

    public void setWeightHint(String weightHint) {
        this.weightHint = weightHint;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getExerciseId() {
        return exerciseId;
    }
}
