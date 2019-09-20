package com.example.workoutlog.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ExerciseWithSetsAndHints {
    @Embedded
    public ExercisePerformedDraft exercisePerformedDraft;

    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    public List<ExerciseSetWithHint> exerciseSetWithHintList;

    public ExercisePerformedDraft getExercisePerformedDraft() {
        return exercisePerformedDraft;
    }

    public List<ExerciseSetWithHint> getExerciseSetWithHintList() {
        return exerciseSetWithHintList;
    }
}
