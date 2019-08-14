package com.example.workoutlog.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.WorkoutsListItemBinding;
import com.example.workoutlog.model.Workout;

import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Workout> workouts;
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WorkoutsListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.workouts_list_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(binding, mListener);

        return holder;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.name.setText(workouts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(workouts != null)
            return workouts.size();

        else
            return 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ItemViewHolder(WorkoutsListItemBinding binding, final OnItemClickListener listener) {
            super(binding.getRoot());
            name = binding.tvWorkoutName;

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
