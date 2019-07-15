package com.example.workoutlog.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ExerciseListFooterBinding;
import com.example.workoutlog.databinding.ExercisesListItemBinding;
import com.example.workoutlog.model.Exercise;
import com.example.workoutlog.model.ExerciseWithSets;

import java.util.ArrayList;
import java.util.List;

public class WorkoutRoutineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Exercise> mExerciseList;
    private Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private OnUpdateExerciseListener listener;

    WorkoutRoutineAdapter(ArrayList<Exercise> mExerciseList) {
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
        //mExerciseList.addAll(exercises);

        for(ExerciseWithSets e : exercises) {
            Exercise exercise = e.exercise;
            Log.d("KEVIN", "EXERCISE: " + exercise.name);
            exercise.exerciseSetList = e.exerciseSetList;
            mExerciseList.add(exercise); // THIS IS THE LINE
        }

        Log.d("KEVIN", "NOTIFIED DATA SET CHANGED");
        notifyDataSetChanged();
    }

    public void setOnUpdateExerciseListener(OnUpdateExerciseListener listener) {
        this.listener = listener;
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
            //mExerciseList.add(new Exercise(""));
            if(listener.addExercise())
                notifyItemInserted(getAdapterPosition());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ExercisesListItemBinding binding;
        TableLayout table;
        Button add;
        EditText name;
        ImageView restTime;
        ImageView superset;

        public ItemViewHolder(ExercisesListItemBinding binding) {
            super(binding.getRoot());
            table = binding.exerciseTable;
            add = binding.btnAddSet;
            name = binding.exerciseTitle;
            restTime = binding.restTimeIcon;
            superset = binding.supersetIcon;
            this.binding = binding;
        }

        public void bind() {
            addTableRows();
            name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    //mExerciseList.get(getAdapterPosition()).name = s.toString();


                    listener.setName(getAdapterPosition(), s.toString());
                }
            });

            name.setText(mExerciseList.get(getAdapterPosition()).name);
        }

        private DynamicTableRow initDynamicTableRow(int i) {
            final DynamicTableRow row = new DynamicTableRow(binding.getRoot().getContext(), table.getChildCount(), listener);

            /*
            DynamicTableRow.MyEditTextListener l1 = row.new MyEditTextListener();
            DynamicTableRow.MyEditTextListener l2 = row.new MyEditTextListener();

            l1.setListener(new DynamicTableRow.OnUpdateExerciseListener() {
                @Override
                public void onUpdateExercise(String data, int position) {
                    mExerciseList.get(getAdapterPosition()).reps.set(position, data);
                }
            });

            l2.setListener(new DynamicTableRow.OnUpdateExerciseListener() {
                @Override
                public void onUpdateExercise(String data, int position) {
                    mExerciseList.get(getAdapterPosition()).weights.set(position, data);

                }
            });


            row.setRepsEditTextListener(l1);
            row.setWeightsEditTextListener(l2);
            */

            row.init();

            row.weightEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    listener.setWeight(getAdapterPosition(), row.tableChildCount - 1, s.toString());
                }
            });

            row.repsEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    listener.setReps(getAdapterPosition(), row.tableChildCount - 1, s.toString());
                }
            });

             row.weightEditText.setHint(mExerciseList.get(getAdapterPosition()).weights.get(i));
             row.repsEditText.setHint(mExerciseList.get(getAdapterPosition()).reps.get(i));

            //row.weightEditText.setHint(mExerciseList.get(getAdapterPosition()).exerciseSetList.get(i).weight);
            //row.repsEditText.setHint(mExerciseList.get(getAdapterPosition()).exerciseSetList.get(i).reps);

            return row;
        }

        private void addTableRows() {
            int numSets = mExerciseList.get(getAdapterPosition()).reps.size();
            for(int i = 0; i < numSets; i++) {
                table.addView(initDynamicTableRow(i), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {

                case R.id.btn_add_set:
                    listener.addSet(getAdapterPosition());
                    //mExerciseList.get(getAdapterPosition()).weights.add("");
                    //mExerciseList.get(getAdapterPosition()).reps.add("");
                    //mExerciseList.get(getAdapterPosition()).sets++;
                    table.addView(initDynamicTableRow(mExerciseList.get(getAdapterPosition()).numSets - 1), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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

                    /*
                    AutoCompleteTextView tv = layout.findViewById(R.id.number_picker);
                    final String[] COUNTRIES = new String[] {
                            "Belgium", "France", "Italy", "Germany", "Spain"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
                    tv.setAdapter(adapter);
                    */
                    
                    break;

                case R.id.superset_icon:
                    listener.makeSuperset(getAdapterPosition());
                    break;

            }
        }
    }
}
