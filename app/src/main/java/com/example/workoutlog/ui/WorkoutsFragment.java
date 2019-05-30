package com.example.workoutlog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workoutlog.databinding.FragmentWorkoutsListBinding;

public class WorkoutsFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NewWorkoutActivity.class);
        getActivity().startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentWorkoutsListBinding binding = FragmentWorkoutsListBinding.inflate(inflater, container, false);
        binding.setListener(this);
        return binding.getRoot();
    }
}
