package com.example.workoutlog.model;

import androidx.room.Ignore;

import java.util.Collections;
import java.util.List;

public class ExercisePerformed {
    public int id;

    public int workoutLogId;

    @Ignore
    public List<ExerciseSetPerformed> exerciseSetList;

    public ExercisePerformed(int numSets) {
        exerciseSetList.addAll(Collections.nCopies(numSets, new ExerciseSetPerformed()));
    }
}
