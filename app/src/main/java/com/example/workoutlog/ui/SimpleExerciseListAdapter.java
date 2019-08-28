package com.example.workoutlog.ui;

import android.graphics.Color;
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
import com.example.workoutlog.model.ExercisePerformedDraft;

import java.util.ArrayList;

public class SimpleExerciseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ExercisePerformedDraft> mExercises;
    private OnItemClickListener mListener;

    private boolean mHasHighlightedRow;
    private ItemViewHolder mItemViewHolder;
    private int mItemClickedPosition;

    public SimpleExerciseListAdapter(ArrayList<ExercisePerformedDraft> exercises) {
        mExercises = exercises;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SimpleExercisesListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.simple_exercises_list_item, parent, false);
        SimpleExerciseListAdapter.ItemViewHolder holder = new SimpleExerciseListAdapter.ItemViewHolder(binding, mListener);
        if(!mHasHighlightedRow) {
            mItemClickedPosition = 0;
            mItemViewHolder = holder;
            holder.binding.getRoot().setBackgroundColor(Color.parseColor("#fff9c0"));
            mHasHighlightedRow = true;
        }
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        SimpleExercisesListItemBinding binding;

        public ItemViewHolder(final SimpleExercisesListItemBinding binding, final OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            name = binding.simpleExerciseName;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            if(position != mItemClickedPosition) {
                                mItemViewHolder.binding.getRoot().setBackgroundColor(Color.WHITE);
                                binding.getRoot().setBackgroundColor(Color.parseColor("#fff9c0"));
                                mItemViewHolder = ItemViewHolder.this;
                                mItemClickedPosition = position;
                            }
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
