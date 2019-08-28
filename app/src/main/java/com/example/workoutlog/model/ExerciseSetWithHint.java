package com.example.workoutlog.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciseSetWithHint {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int number;
    public String reps;
    public String weight;
    public String repsHint;
    public String weightHint;
    public long exerciseId;

    public ExerciseSetWithHint(String reps, String weight, int number) {
        this.reps = reps;
        this.weight = weight;
        this.number = number;
    }
}
