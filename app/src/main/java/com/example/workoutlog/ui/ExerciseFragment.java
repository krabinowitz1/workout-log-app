package com.example.workoutlog.ui;

import android.content.Context;
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
import com.example.workoutlog.model.ExercisePerformedDraft;
import com.example.workoutlog.model.ExerciseSetWithHint;
import com.example.workoutlog.model.ExerciseWithSetsAndHints;
import com.example.workoutlog.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragment extends Fragment implements OnUpdateExerciseListener {
    private FragmentExerciseBinding mBinding;
    private ExerciseViewModel mExerciseViewModel;

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<Integer> mTopSectionPositions;
    private List<ExerciseWithSetsAndHints> mExerciseWithSetsAndHintsList;

    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnFragmentInteractionListener mOnFragmentInteractionListener;

    private ExerciseAdapter mAdapter;
    private int pos;

    private static final String KEY_STRING_DATA = "KEY_STRING_DATA";
    private static final String KEY_INT_DATA = "KEY_INT_DATA";

    public interface OnFragmentInteractionListener {
        public void onExerciseAdded();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof View.OnFocusChangeListener)
            setOnFocusChangeListener((View.OnFocusChangeListener) context);
    }

    private void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        mOnFocusChangeListener = listener;
    }


    public static ExerciseFragment newInstance(String data, int position) {
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(KEY_STRING_DATA, data);
        args.putInt(KEY_INT_DATA, position);
        exerciseFragment.setArguments(args);
        return exerciseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false);
        String workoutName = getArguments().getString(KEY_STRING_DATA);
        pos = getArguments().getInt(KEY_INT_DATA);
        mExerciseViewModel = ViewModelProviders.of(getActivity(), new ExerciseViewModel.ExerciseViewModelFactory(getActivity().getApplication(), workoutName)).get(ExerciseViewModel.class);

        mExerciseWithSetsAndHintsList = mExerciseViewModel.getExerciseWithSetsAndHintsList().getValue();

        loadRecyclerView();

        return mBinding.getRoot();
    }

    private void loadRecyclerView() {
        mViewTypeList = new ArrayList<>();
        mTopSectionPositions = new ArrayList<>();
        fillListsWithData(mExerciseWithSetsAndHintsList.get(pos));

        mAdapter = new ExerciseAdapter(mViewTypeList, this, mTopSectionPositions, ExerciseFragment.class.getSimpleName());
        mAdapter.setExerciseDraft(getExercise());
        mAdapter.setOnFocusChangeListener(mOnFocusChangeListener);

        mBinding.duringWorkoutExerciseList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.duringWorkoutExerciseList.setAdapter(mAdapter);
    }

    private ExercisePerformedDraft getExercise() {
        ExercisePerformedDraft exercisePerformedDraft = mExerciseWithSetsAndHintsList.get(pos).exercisePerformedDraft;
        exercisePerformedDraft.setExerciseSetWithHintList( mExerciseWithSetsAndHintsList.get(pos).getExerciseSetWithHintList());
        return exercisePerformedDraft;
    }

    private void fillListsWithData(ExerciseWithSetsAndHints exerciseWithSetsAndHints) {
        mViewTypeList.add(ExerciseAdapter.TopSectionViewHolder.VIEW_TYPE);

        for (int j = 0; j < exerciseWithSetsAndHints.getExerciseSetWithHintList().size(); j++) {
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
        mExerciseWithSetsAndHintsList.get(whichExercise).getExerciseSetWithHintList().get(whichSet).setReps(data);
        mExerciseViewModel.updateExerciseSetWithHint(mExerciseWithSetsAndHintsList.get(whichExercise).getExerciseSetWithHintList().get(whichSet));
    }

    @Override
    public void setWeight(int whichExercise, int whichSet, String data) {
        mExerciseWithSetsAndHintsList.get(whichExercise).getExerciseSetWithHintList().get(whichSet).setWeight(data);
        mExerciseViewModel.updateExerciseSetWithHint(mExerciseWithSetsAndHintsList.get(whichExercise).getExerciseSetWithHintList().get(whichSet));
    }

    @Override
    public void setRestTime(int whichExercise, String data) {

    }

    @Override
    public void makeSuperset(int whichExercise) {

    }

    @Override
    public void addSet(int whichExercise, int position) {
        ExercisePerformedDraft exercisePerformedDraft = getExercise();
        exercisePerformedDraft.setNumSets(exercisePerformedDraft.getNumSets() + 1);
        mExerciseViewModel.updateExercisePerformedDraftNumSet(exercisePerformedDraft);
        ExerciseSetWithHint exerciseSetWithHint = new ExerciseSetWithHint("", "", exercisePerformedDraft.getNumSets());
        exerciseSetWithHint.setExerciseId(exercisePerformedDraft.getId());
        exercisePerformedDraft.getExerciseSetWithHintList().add(exerciseSetWithHint);
        mExerciseViewModel.insertExerciseSetWithHint(exerciseSetWithHint);
        mViewTypeList.add(position, ExerciseAdapter.MiddleSectionViewHolder.VIEW_TYPE);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean addExercise(int position) {
        return false;
    }
}