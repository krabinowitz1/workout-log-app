package com.example.workoutlog.ui;

public interface OnUpdateExerciseListener {
    void setName(int whichExercise, String data);

    void setReps(int whichExercise, int whichSet, String data);

    void setWeight(int whichExercise, int whichSet, String data);

    void setRestTime(int whichExercise, String data);

    void makeSuperset(int whichExercise);

    void addSet(int whichExercise);

    boolean addExercise();
}
