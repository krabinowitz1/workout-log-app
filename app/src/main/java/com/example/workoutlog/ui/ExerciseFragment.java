package com.example.workoutlog.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.FragmentExerciseBinding;
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseWithSetsAndHints;
import com.example.workoutlog.viewmodel.ExerciseViewModel;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment {
    private FragmentExerciseBinding mBinding;
    private ExerciseViewModel mExerciseViewModel;

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<Integer> mTopSectionPositions;

    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnUpdateExerciseListener mOnUpdateExerciseListener;

    private ExerciseWithSetsAndHints mExerciseWithSetsAndHints;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof View.OnFocusChangeListener)
            setOnFocusChangeListener((View.OnFocusChangeListener) context);

        if (context instanceof OnUpdateExerciseListener)
            setOnUpdateExerciseListener((OnUpdateExerciseListener) context);
    }

    private void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        mOnFocusChangeListener = listener;
    }

    private void setOnUpdateExerciseListener(OnUpdateExerciseListener listener) {
        mOnUpdateExerciseListener = listener;
    }

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false);
        String workoutName = getArguments().getString("workout_name");
        Integer position = getArguments().getInt("position");
        mExerciseViewModel = ViewModelProviders.of(getActivity(), new ExerciseViewModel.ExerciseViewModelFactory(getActivity().getApplication(), workoutName)).get(ExerciseViewModel.class);
        mExerciseWithSetsAndHints = mExerciseViewModel.getExerciseWithSetsAndHintsList().getValue().get(position);
        loadRecyclerView();

        return mBinding.getRoot();
    }

    private void loadRecyclerView() {
        mViewTypeList = new ArrayList<>();
        mTopSectionPositions = new ArrayList<>();
        fillListsWithData(mExerciseWithSetsAndHints);

        ArrayList<ExercisePerformedDraft> singleItemList = new ArrayList<>();
        singleItemList.add(getExercise());
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(mViewTypeList, mOnUpdateExerciseListener, mTopSectionPositions, ExerciseFragment.class.getSimpleName());
        exerciseAdapter.setExerciseDrafts(singleItemList);

        exerciseAdapter.setOnFocusChangeListener(mOnFocusChangeListener);

        mBinding.duringWorkoutExerciseList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.duringWorkoutExerciseList.setAdapter(exerciseAdapter);
    }

    private ExercisePerformedDraft getExercise() {
        ExercisePerformedDraft exercisePerformedDraft = mExerciseWithSetsAndHints.exercisePerformedDraft;
        exercisePerformedDraft.exerciseSetWithHintList = mExerciseWithSetsAndHints.exerciseSetWithHintList;
        return exercisePerformedDraft;
    }

    private void fillListsWithData(ExerciseWithSetsAndHints exerciseWithSetsAndHints) {

        mViewTypeList.add(ExerciseAdapter.TopSectionViewHolder.VIEW_TYPE);

        for (int j = 0; j < exerciseWithSetsAndHints.exerciseSetWithHintList.size(); j++) {
            mViewTypeList.add(ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
        }

        mViewTypeList.add(ExerciseAdapter.BottomSectionViewHolder.VIEW_TYPE);
        mTopSectionPositions.add(0);
    }
}