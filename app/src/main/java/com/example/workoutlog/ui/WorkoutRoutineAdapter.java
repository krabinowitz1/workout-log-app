package com.example.workoutlog.ui;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ExerciseListFooterBinding;
import com.example.workoutlog.databinding.ExercisesListItemBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseSet;
import com.example.workoutlog.model.ExerciseWithSets;

import java.util.ArrayList;
import java.util.List;

public class WorkoutRoutineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Exercise> mExerciseList;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private OnUpdateExerciseListener listener;

    private Context mContext;

    WorkoutRoutineAdapter(ArrayList<Exercise> exerciseList) {
        this.mExerciseList = exerciseList;
    }

    WorkoutRoutineAdapter(ArrayList<Exercise> mExerciseList, Context context) {
        this.mExerciseList = mExerciseList;
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == TYPE_ITEM) {
            ExercisesListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.exercises_list_item, parent, false);
            ItemViewHolder holder = new ItemViewHolder(binding);
//            holder.add.setOnClickListener(holder);
            holder.restTime.setOnClickListener(holder);
            holder.superset.setOnClickListener(holder);

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

    public void setExercises(List<ExerciseWithSets> exercises) {
        mExerciseList = new ArrayList<>(exercises.size());

        for(ExerciseWithSets e : exercises) {
            Exercise exercise = e.exercise;
            exercise.exerciseSetList = e.exerciseSetList;
            mExerciseList.add(exercise);
        }

        notifyDataSetChanged();
    }

    public void setOnUpdateExerciseListener(OnUpdateExerciseListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        long startTime = System.currentTimeMillis();
        if(holder.getItemViewType() == TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.bind();
        }
        Log.i("Kevin", "position " + position);
        Log.i("KEVIN", "bindView time: " + (System.currentTimeMillis() - startTime));
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

    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button add;
        public FooterViewHolder(ExerciseListFooterBinding binding) {
            super(binding.getRoot());
            add = binding.btnAddExercise;
        }

        @Override
        public void onClick(View v) {
            //if(listener.addExercise())
                //notifyItemInserted(getAdapterPosition());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ExercisesListItemBinding binding;
        TableLayout table;
        Button add;
        EditText exerciseName;
        ImageView restTime;
        ImageView superset;

        ExerciseSetListAdapter adapter;
        RecyclerView recyclerView;

        public ItemViewHolder(ExercisesListItemBinding binding) {
            super(binding.getRoot());
            //table = binding.exerciseTable;
            //add = binding.btnAddSet;
            exerciseName = binding.exerciseTitle;
            restTime = binding.restTimeIcon;
            superset = binding.supersetIcon;
            recyclerView = binding.recExerciseSets;

            binding.recExerciseSets.setAdapter(null);

            binding.recExerciseSets.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            this.binding = binding;
        }

        public void bind() {
            addTableRows();


            exerciseName.setText(mExerciseList.get(getAdapterPosition()).name);
            exerciseName.setSelectAllOnFocus(true);
            exerciseName.addTextChangedListener(new CondensedTextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                    listener.setName(getAdapterPosition(), s.toString());
                }
            });
        }


        private void addTableRows() {



            //adapter = new ExerciseSetListAdapter(mExerciseList.get(getAdapterPosition()).exerciseSetList);
            binding.recExerciseSets.setHasFixedSize(true);
            binding.recExerciseSets.setNestedScrollingEnabled(false);
            binding.recExerciseSets.setItemViewCacheSize(mExerciseList.get(getAdapterPosition()).exerciseSetList.size());
            //binding.recExerciseSets.setAdapter(adapter);
            binding.recExerciseSets.swapAdapter(new ExerciseSetListAdapter(mExerciseList.get(getAdapterPosition()).exerciseSetList), false);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {

                case R.id.btn2_add_set:
                    //listener.addSet(getAdapterPosition());
                    //table.addView(initDynamicTableRow(mExerciseList.get(getAdapterPosition()).numSets - 1), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    break;

                case R.id.rest_time_icon:
                    Context context = binding.getRoot().getContext();
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final float scale = context.getResources().getDisplayMetrics().density;
                    int width = (int) (200 * scale + 0.5f);
                    View layout = inflater.inflate(R.layout.popup_rest_time, null);
                    PopupWindow popup = new PopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        popup.showAsDropDown(restTime, 0, 0, Gravity.START);
                    }

                    NumberPicker numberPicker = layout.findViewById(R.id.number_picker);
                    numberPicker.setMinValue(0);
                    numberPicker.setMaxValue(19);
                    
                    break;

                case R.id.superset_icon:
                    listener.makeSuperset(getAdapterPosition());
                    break;

            }
        }
    }
}
