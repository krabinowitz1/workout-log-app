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

public class NewWorkoutActivity extends AppCompatActivity implements SaveAsDialogFragment.SaveAsDialogListener {
    private ActivityNewWorkoutBinding binding;

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
        binding.exercisesList.setAdapter(new WorkoutRoutineAdapter(new ArrayList<Exercise>()));
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
            replyIntent.putParcelableArrayListExtra("exercises", list);
            replyIntent.putExtra("workoutName", inputText);
            setResult(RESULT_OK, replyIntent);
        }

        finish();
    }
}
