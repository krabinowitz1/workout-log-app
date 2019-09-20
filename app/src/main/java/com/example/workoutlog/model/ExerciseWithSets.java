package com.example.workoutlog.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ExerciseWithSets {
    @Embedded
    public Exercise exercise;

    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    public List<ExerciseSet> exerciseSetList;

    public Exercise getExercise() {
        return exercise;
    }

    public List<ExerciseSet> getExerciseSetList() {
        return exerciseSetList;
    }
}
