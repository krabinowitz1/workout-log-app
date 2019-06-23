package com.example.workoutlog.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ExerciseListFooterBinding;
import com.example.workoutlog.databinding.ExercisesListItemBinding;
import com.example.workoutlog.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class NewWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Exercise> mExerciseList;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public interface OnUpdateExerciseListener {
        void onUpdateExercise(String data, int position);
    }

    NewWorkoutAdapter(ArrayList<Exercise> mExerciseList) {
        this.mExerciseList = mExerciseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == TYPE_ITEM) {
            ExercisesListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.exercises_list_item, parent, false);
            ItemViewHolder holder = new ItemViewHolder(binding);
            holder.add.setOnClickListener(holder);
            return holder;
        }

        ExerciseListFooterBinding binding = DataBindingUtil.inflate(inflater, R.layout.exercise_list_footer, parent, false);
        FooterViewHolder holder = new FooterViewHolder(binding);
        holder.add.setOnClickListener(holder);
        return holder;
    }

    public ArrayList<Exercise> getExercises() {
        return mExerciseList;
    }

    public void setExercises(List<Exercise> exercises) {
        mExerciseList = new ArrayList<>(exercises.size());
        mExerciseList.addAll(exercises);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.bind();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mExerciseList.size())
            return TYPE_FOOTER;

        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size() + 1;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.table.removeViews(1, itemViewHolder.table.getChildCount() - 1);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button add;
        public FooterViewHolder(ExerciseListFooterBinding binding) {
            super(binding.getRoot());
            add = binding.btnAddExercise;
        }

        @Override
        public void onClick(View v) {
            mExerciseList.add(new Exercise(""));
            notifyItemInserted(getAdapterPosition());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ExercisesListItemBinding binding;
        TableLayout table;
        Button add;
        EditText name;

        public ItemViewHolder(ExercisesListItemBinding binding) {
            super(binding.getRoot());
            table = binding.exerciseTable;
            add = binding.btnAddSet;
            name = binding.exerciseTitle;
            this.binding = binding;
        }

        public void bind() {
            addTableRows();
            name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mExerciseList.get(getAdapterPosition()).name = s.toString();
                }
            });

            name.setText(mExerciseList.get(getAdapterPosition()).name);
        }

        private DynamicTableRow initDynamicTableRow(int i) {
            DynamicTableRow row = new DynamicTableRow(binding.getRoot().getContext(), table.getChildCount());
            DynamicTableRow.MyEditTextListener l1 = row.new MyEditTextListener();
            DynamicTableRow.MyEditTextListener l2 = row.new MyEditTextListener();


            l1.setListener(new OnUpdateExerciseListener() {
                @Override
                public void onUpdateExercise(String data, int position) {
                    mExerciseList.get(getAdapterPosition()).reps.set(position, data);
                }
            });

            l2.setListener(new OnUpdateExerciseListener() {
                @Override
                public void onUpdateExercise(String data, int position) {
                    mExerciseList.get(getAdapterPosition()).weights.set(position, data);
                }
            });

            row.setRepsEditTextListener(l1);
            row.setWeightsEditTextListener(l2);
            row.init();
            row.weightEditText.setHint(mExerciseList.get(getAdapterPosition()).weights.get(i));
            row.repsEditText.setHint(mExerciseList.get(getAdapterPosition()).reps.get(i));


            return row;
        }

        private void addTableRows() {
            for(int i = 0; i < mExerciseList.get(getAdapterPosition()).sets; i++) {
                table.addView(initDynamicTableRow(i), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_add_set:
                    mExerciseList.get(getAdapterPosition()).weights.add("");
                    mExerciseList.get(getAdapterPosition()).reps.add("");
                    mExerciseList.get(getAdapterPosition()).sets++;
                    table.addView(initDynamicTableRow(mExerciseList.get(getAdapterPosition()).sets - 1), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }
}
