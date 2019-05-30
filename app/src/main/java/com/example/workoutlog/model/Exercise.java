package com.example.workoutlog.model;

import java.util.ArrayList;

public class Exercise {
    public String name;
    public int repCount;
    public int restTime;
    public int sets = 0;

    public ArrayList<String> reps = new ArrayList<>();
    public ArrayList<String> weights = new ArrayList<>();

    public Exercise(String name) {
        this.name = name;
    }
}
