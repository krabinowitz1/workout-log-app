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
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseSetWithHint;
import com.example.workoutlog.viewmodel.ExerciseViewModel;

import java.util.ArrayList;

public class NewWorkoutActivity extends AppCompatActivity implements SaveAsDialogFragment.SaveAsDialogListener, OnUpdateExerciseListener {
    private ActivityNewWorkoutBinding binding;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<ExercisePerformedDraft> exercisePerformedDrafts = new ArrayList<>();
    private ExerciseAdapter exerciseAdapter;
    private ArrayList<Integer> mViewTypeList;
    private ArrayList<Integer> mTopSectionPositions;

    private static final String EMPTY_FIELD = "";
    private static final int MINIMUM_SETS = 1;

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
        SaveAsDialogFragment saveasDialog = SaveAsDialogFragment.newInstance(getExerciseNames());
        saveasDialog.show(fm, "fragment_save_as");
    }

    private ArrayList<String> getExerciseNames() {
        ArrayList<String> names = new ArrayList<>(exercises.size());
        for(int i = 0; i < exercises.size(); i++) {
            names.add(exercises.get(i).getName());
        }

        return names;
    }

    private void loadRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.exercisesList.setLayoutManager(linearLayoutManager);

        mViewTypeList = new ArrayList<>();
        mViewTypeList.add(ExerciseAdapter.FooterViewHolder.VIEW_TYPE);
        mTopSectionPositions = new ArrayList<>();

        exerciseAdapter = new ExerciseAdapter(mViewTypeList, this, mTopSectionPositions, NewWorkoutActivity.class.getSimpleName());
        exerciseAdapter.setExercises(exercises);
        binding.exercisesList.setAdapter(exerciseAdapter);
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
    public void onSaveDialog(String inputText1, String inputText2) {
        Intent replyIntent = new Intent();

        if(TextUtils.isEmpty(inputText1)) {
            setResult(RESULT_CANCELED, replyIntent);
        }

        else {
            setResult(RESULT_OK, replyIntent);
            replyIntent.putExtra("workoutName", inputText1);
            replyIntent.putExtra("workoutDescription", inputText2);

            ExerciseViewModel exerciseViewModel = ViewModelProviders.of(this, new ExerciseViewModel.ExerciseViewModelFactory(getApplication(), inputText1)).get(ExerciseViewModel.class);
            exerciseViewModel.insertExerciseList(exercises);
            exerciseViewModel.insertExercisePerformedDraftList(exercisePerformedDrafts);
        }

        finish();
    }

    @Override
    public void setName(int whichExercise, String data) {
        exercises.get(whichExercise).setName(data);
        exercisePerformedDrafts.get(whichExercise).setName(data);
    }

    @Override
    public void setReps(int whichExercise, int whichSet, String data) {
        exercises.get(whichExercise).getExerciseSetList().get(whichSet).setReps(data);
        exercisePerformedDrafts.get(whichExercise).getExerciseSetWithHintList().get(whichSet).setRepsHint(data);
    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {
        exercises.get(whichExercise).getExerciseSetList().get(whichSet).setWeight(data);
        exercisePerformedDrafts.get(whichExercise).getExerciseSetWithHintList().get(whichSet).setWeightHint(data);
    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {
        ArrayList<String> exerciseNames = new ArrayList<>();
        for(int i = 0; i < exercises.size(); i++) {
            String s = exercises.get(i).getName();
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
    public void addSet(int whichExercise, int position) {
        exercises.get(whichExercise).setNumSets(exercises.get(whichExercise).getNumSets() + 1);
        exercises.get(whichExercise).getExerciseSetList().add(new ExerciseSet(EMPTY_FIELD, EMPTY_FIELD, exercises.get(whichExercise).getNumSets()));

        exercisePerformedDrafts.get(whichExercise).setNumSets(exercisePerformedDrafts.get(whichExercise).getNumSets() + 1);
        exercisePerformedDrafts.get(whichExercise).getExerciseSetWithHintList().add(new ExerciseSetWithHint(EMPTY_FIELD, EMPTY_FIELD, exercises.get(whichExercise).getNumSets()));

        for(int i = whichExercise + 1; i < mTopSectionPositions.size(); i++) {
            mTopSectionPositions.set(i, mTopSectionPositions.get(i) + 1);
        }

        mViewTypeList.add(position, ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
        exerciseAdapter.notifyDataSetChanged();
        binding.exercisesList.scrollToPosition(position + 1);
    }

    @Override
    public boolean addExercise(int position) {
        Exercise exercise = new Exercise(EMPTY_FIELD);
        exercise.getExerciseSetList().add(new ExerciseSet(EMPTY_FIELD, EMPTY_FIELD, exercise.getNumSets()));

        ExercisePerformedDraft exercisePerformedDraft = new ExercisePerformedDraft(EMPTY_FIELD, MINIMUM_SETS);
        exercisePerformedDraft.getExerciseSetWithHintList().add(new ExerciseSetWithHint(EMPTY_FIELD, EMPTY_FIELD, exercise.getNumSets()));
        exercisePerformedDrafts.add(exercisePerformedDraft);

        exercises.add(exercise);
        mTopSectionPositions.add(position);

        mViewTypeList.add(mViewTypeList.size() - 1, ExerciseAdapter.TopSectionViewHolder.VIEW_TYPE);
        mViewTypeList.add(mViewTypeList.size() - 1, ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
        mViewTypeList.add(mViewTypeList.size() - 1, ExerciseAdapter.BottomSectionViewHolder.VIEW_TYPE);
        exerciseAdapter.notifyDataSetChanged();
        //binding.exercisesList.scrollToPosition(mViewTypeList.size() - 1);
        return true;
    }
}
