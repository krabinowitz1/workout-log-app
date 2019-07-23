package com.example.workoutlog.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.DialogSupersetBinding;
import com.example.workoutlog.model.Exercise;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupersetDialogFragment extends DialogFragment {
    DialogSupersetBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_superset, null, false);

        ArrayList<String> exerciseNames = getArguments().getStringArrayList("exercise_names");
        String exerciseName = getArguments().getString("exercise_name");
        binding.supersetExerciseA.setText(exerciseName);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, exerciseNames);
        binding.autocomplete.setAdapter(arrayAdapter);
        alertDialogBuilder.setView(binding.getRoot());
        return alertDialogBuilder.create();
    }

    public static SupersetDialogFragment newInstance(String data, ArrayList<String> list) {
        SupersetDialogFragment supersetDialog = new SupersetDialogFragment();
        Bundle args = new Bundle();
        args.putString("exercise_name", data);
        args.putStringArrayList("exercise_names", list);
        supersetDialog.setArguments(args);
        return supersetDialog;
    }
}
