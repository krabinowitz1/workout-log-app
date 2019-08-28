package com.example.workoutlog.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "exercise_performed_draft_table")
public class ExercisePerformedDraft {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String workoutName;

    public String name;

    public int numSets;

    @Ignore
    public List<ExerciseSetWithHint> exerciseSetWithHintList;

    public ExercisePerformedDraft(String name, int numSets) {
        this.name = name;
        this.numSets = numSets;
        exerciseSetWithHintList = new ArrayList<>();
    }
}
