package com.example.workoutlog.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.SimpleExercisesListItemBinding;
import com.example.workoutlog.model.Exercise;

import java.util.ArrayList;

public class SimpleExerciseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Exercise> mExercises;
    private SimpleExerciseListAdapter.OnItemClickListener mListener;

    public SimpleExerciseListAdapter(ArrayList<Exercise> exercises) {
        mExercises = exercises;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SimpleExercisesListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.simple_exercises_list_item, parent, false);
        SimpleExerciseListAdapter.ItemViewHolder holder = new SimpleExerciseListAdapter.ItemViewHolder(binding, mListener);
        return holder;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.name.setText(mExercises.get(position).name);
    }

    @Override
    public int getItemCount() {
        if(mExercises != null)
            return mExercises.size();

        else
            return 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ItemViewHolder(SimpleExercisesListItemBinding binding, final OnItemClickListener listener) {
            super(binding.getRoot());
            name = binding.simpleExerciseName;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
