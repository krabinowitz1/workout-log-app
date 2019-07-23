package com.example.workoutlog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityNewWorkoutBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.viewmodel.ExerciseViewModel;

import java.util.ArrayList;

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


        WorkoutRoutineAdapter adapter = new WorkoutRoutineAdapter(exercises);
        adapter.setOnUpdateExerciseListener(NewWorkoutActivity.this);
        binding.exercisesList.setAdapter(adapter);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();

        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
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
            setResult(RESULT_OK, replyIntent);
            replyIntent.putExtra("workoutName", inputText);

            ExerciseViewModel exerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModel.ExerciseViewModelFactory(getApplication(), inputText)).get(ExerciseViewModel.class);
            exerciseViewModel.insertExerciseList(exercises);
        }

        finish();
    }

    @Override
    public void setName(int whichExercise, String data) {
        exercises.get(whichExercise).name = data;
    }

    @Override
    public void setReps(int whichExercise, int whichSet, String data) {
        exercises.get(whichExercise).exerciseSetList.get(whichSet).reps = data;
    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {
        exercises.get(whichExercise).exerciseSetList.get(whichSet).weight = data;
    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {
        ArrayList<String> exerciseNames = new ArrayList<>();
        for(int i = 0; i < exercises.size(); i++) {
            String s = exercises.get(i).name;
            if(s.equals("") )
                exerciseNames.add("unnamed exercise");

            else
                exerciseNames.add(s);
        }
        String currentExerciseName = exerciseNames.get(whichExercise);
        exerciseNames.remove(whichExercise);
        closeKeyboard();
        FragmentManager fm = getSupportFragmentManager();
        SupersetDialogFragment supersetDialog = SupersetDialogFragment.newInstance(currentExerciseName, exerciseNames);
        supersetDialog.show(fm, "fragment_superset");
    }

    @Override
    public void addSet(int whichExercise) {
        exercises.get(whichExercise).numSets++;
        exercises.get(whichExercise).exerciseSetList.add(new ExerciseSet(" ", " ", exercises.get(whichExercise).numSets));
    }

    @Override
    public boolean addExercise() {
        Exercise exercise = new Exercise("");
        exercise.exerciseSetList.add(new ExerciseSet(" ", " ", exercise.numSets));


        exercises.add(exercise);

        return true;
    }
}
