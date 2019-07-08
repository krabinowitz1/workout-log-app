package com.example.workoutlog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityNewWorkoutBinding;
import com.example.workoutlog.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class NewWorkoutActivity extends AppCompatActivity implements SaveAsDialogFragment.SaveAsDialogListener, OnUpdateExerciseListener {
    private ActivityNewWorkoutBinding binding;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_workout);
        setUpToolBar();
        loadRecyclerView();
    }

    private void setUpToolBar() {
        setSupportActionBar(binding.activityNewWorkoutsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24px);
        getSupportActionBar().setTitle("New Workout");
        binding.activityNewWorkoutsToolbar.setTitleTextColor(Color.WHITE);
        binding.activityNewWorkoutsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SaveAsDialogFragment saveasDialog = new SaveAsDialogFragment();
        saveasDialog.show(fm, "fragment_save_as");
    }

    private void loadRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.exercisesList.setLayoutManager(linearLayoutManager);
        //binding.exercisesList.setAdapter(new WorkoutRoutineAdapter(new ArrayList<Exercise>()));


        WorkoutRoutineAdapter adapter = new WorkoutRoutineAdapter(exercises);
        adapter.setOnUpdateExerciseListener(NewWorkoutActivity.this);
        binding.exercisesList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSaveDialog(String inputText) {
        Intent replyIntent = new Intent();

        if(TextUtils.isEmpty(inputText)) {
            setResult(RESULT_CANCELED, replyIntent);
        }

        else {
            ArrayList<Exercise> list = ((WorkoutRoutineAdapter) binding.exercisesList.getAdapter()).getExercises();
            replyIntent.putParcelableArrayListExtra("exercises", exercises);
            replyIntent.putExtra("workoutName", inputText);
            setResult(RESULT_OK, replyIntent);
        }

        finish();
    }

    @Override
    public void setName(int whichExercise, String data) {
        exercises.get(whichExercise).name = data;
    }

    @Override
    public void setReps(int whichExercise, int whichSet, String data) {
        exercises.get(whichExercise).reps.set(whichSet, data);
    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {
        exercises.get(whichExercise).weights.set(whichSet, data);
    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {
        FragmentManager fm = getSupportFragmentManager();
        SupersetDialogFragment supersetDialog = new SupersetDialogFragment();
        supersetDialog.show(fm, "fragment_superset");
    }

    @Override
    public void addSet(int whichExercise) {
        exercises.get(whichExercise).reps.add(" ");
        exercises.get(whichExercise).weights.add(" ");
        exercises.get(whichExercise).sets++;
    }

    @Override
    public boolean addExercise() {
        exercises.add(new Exercise(""));

        return true;
    }
}
