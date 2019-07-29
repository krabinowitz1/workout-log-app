package com.example.workoutlog.ui;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ExercisesListItemBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseWithSets;
import com.example.workoutlog.viewmodel.ExerciseViewModel;

import java.util.List;

public class ExerciseFragment extends Fragment {
    private ExercisesListItemBinding mBinding;
    private ExerciseViewModel mExerciseViewModel;
    private ExerciseWithSets mExerciseWithSets;

    public static ExerciseFragment newInstance(String data, int position) {
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString("workout_name", data);
        args.putInt("position", position);
        exerciseFragment.setArguments(args);
        return exerciseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.exercises_list_item, container, false);
        String workoutName = getArguments().getString("workout_name");
        Integer position = getArguments().getInt("position");
        mExerciseViewModel = ViewModelProviders.of(getActivity(), new ExerciseViewModel.ExerciseViewModelFactory(getActivity().getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseWithSets = mExerciseViewModel.getExerciseWithSetList().getValue().get(position);

        bindData();

        return mBinding.getRoot();
    }

    private void bindData() {
        mBinding.exerciseTitle.setKeyListener(null);
        mBinding.exerciseTitle.setBackground(null);
        mBinding.exerciseTitle.setText(mExerciseWithSets.exercise.name);

        addTableRows();
    }

    private void addTableRows() {
        for(int i = 0; i < mExerciseWithSets.exercise.numSets; i++) {
            mBinding.exerciseTable.addView(initDynamicTableRow(i), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private DynamicTableRow initDynamicTableRow(int i) {
        final DynamicTableRow row = new DynamicTableRow(mBinding.getRoot().getContext(), mBinding.exerciseTable.getChildCount(), null);
        row.init();

        row.weightEditText.setText(mExerciseWithSets.exerciseSetList.get(i).weight);
        row.weightEditText.setSelectAllOnFocus(true);
        row.weightEditText.addTextChangedListener(new CondensedTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        row.repsEditText.setText(mExerciseWithSets.exerciseSetList.get(i).reps);
        row.repsEditText.setSelectAllOnFocus(true);
        row.repsEditText.addTextChangedListener(new CondensedTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return row;
    }
}