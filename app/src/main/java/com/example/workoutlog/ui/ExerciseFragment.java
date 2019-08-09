package com.example.workoutlog.ui;

import android.os.Bundle;
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
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseWithSets;
import com.example.workoutlog.viewmodel.ExerciseViewModel;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment implements OnUpdateExerciseListener {
    private FragmentExerciseBinding mBinding;
    private ExerciseViewModel mExerciseViewModel;
    private ExerciseWithSets mExerciseWithSets;

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<Integer> mTopSectionPositions;

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
        mExerciseWithSets = mExerciseViewModel.getExerciseWithSetList().getValue().get(position);

        loadRecyclerView();

        return mBinding.getRoot();
    }

    private void loadRecyclerView() {
        mViewTypeList = new ArrayList<>();
        mTopSectionPositions = new ArrayList<>();
        fillListsWithData(mExerciseWithSets);

        Exercise exercise = mExerciseWithSets.exercise;
        exercise.exerciseSetList = mExerciseWithSets.exerciseSetList;
        ArrayList<Exercise> singleItemList = new ArrayList<>();
        singleItemList.add(exercise);

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(mViewTypeList, this, mTopSectionPositions);
        exerciseAdapter.setExercises(singleItemList);

        mBinding.duringWorkoutExerciseList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.duringWorkoutExerciseList.setAdapter(exerciseAdapter);
    }

    private void fillListsWithData(ExerciseWithSets exerciseWithSets) {

        mViewTypeList.add(ExerciseAdapter.TopSectionViewHolder.VIEW_TYPE);

        for (int j = 0; j < exerciseWithSets.exerciseSetList.size(); j++) {
            mViewTypeList.add(ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
        }

        mViewTypeList.add(ExerciseAdapter.BottomSectionViewHolder.VIEW_TYPE);
        mTopSectionPositions.add(0);
    }

    @Override
    public void setName(int whichExercise, String data) {

    }

    @Override
    public void setReps(int whichExercise, int whichSet, String data) {

    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {

    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {

    }

    @Override
    public void addSet(int whichExercise, int position) {

    }

    @Override
    public boolean addExercise(int position) {
        return false;
    }
}