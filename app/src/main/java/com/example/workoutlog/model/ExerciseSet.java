package com.example.workoutlog.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciseSet {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int number;
    private String reps;
    private String weight;
    private long exerciseId;

    public ExerciseSet(String reps, String weight, int number) {
        this.setReps(reps);
        this.setWeight(weight);
        this.setNumber(number);
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

    private void setNumber(int number) {
        this.number = number;
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

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getExerciseId() {
        return exerciseId;
    }
}
