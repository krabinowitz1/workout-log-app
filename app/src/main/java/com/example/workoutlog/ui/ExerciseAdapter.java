package com.example.workoutlog.ui;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.BottomSectionExercisesListItemBinding;
import com.example.workoutlog.databinding.FooterExercisesListItemBinding;
import com.example.workoutlog.databinding.MiddleSectionExercisesListItemBinding;
import com.example.workoutlog.databinding.TopSectionExercisesListItemBinding;
import com.example.workoutlog.model.Exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Integer> mViewTypeList;
    private OnUpdateExerciseListener mListener;
    private ArrayList<Integer> mTopSectionPositions;
    private ArrayList<Exercise> mExerciseList;
    private HashMap<EditText, CondensedTextWatcher> mHashMap;


    public ExerciseAdapter(ArrayList<Integer> viewTypeList, OnUpdateExerciseListener listener, ArrayList<Integer> topSectionPositions) {
        mViewTypeList = viewTypeList;
        mListener = listener;
        mTopSectionPositions = topSectionPositions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TopSectionViewHolder.VIEW_TYPE:
                return new TopSectionViewHolder((TopSectionExercisesListItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.top_section_exercises_list_item, parent,false));

            case MiddleSectionViewHolder.VIEW_TYPE:
                return new MiddleSectionViewHolder((MiddleSectionExercisesListItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.middle_section_exercises_list_item, parent, false));

            case BottomSectionViewHolder.VIEW_TYPE:
                return new BottomSectionViewHolder((BottomSectionExercisesListItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bottom_section_exercises_list_item, parent, false));

            default:
                return new FooterViewHolder((FooterExercisesListItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.footer_exercises_list_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TopSectionViewHolder.VIEW_TYPE:
                setTopSectionViewHolderAtPosition((TopSectionViewHolder) holder, position);
                break;

            case MiddleSectionViewHolder.VIEW_TYPE:
                setMiddleSectionViewHolderAtPosition((MiddleSectionViewHolder) holder, position);
                break;

            default:
                break;
        }
    }

    private void setTopSectionViewHolderAtPosition(TopSectionViewHolder holder, int position) {
        final int whichExercise = Collections.binarySearch(mTopSectionPositions, position);
        Exercise exercise = mExerciseList.get(whichExercise);

        holder.exerciseName.setText(exercise.name);

        if (mHashMap == null)
            mHashMap = new HashMap<>();

        mHashMap.put(holder.exerciseName, new CondensedTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mListener.setName(whichExercise, s.toString());
            }
        });
    }

    private void setMiddleSectionViewHolderAtPosition(MiddleSectionViewHolder holder, final int position) {
        final int whichExercise = (Collections.binarySearch(mTopSectionPositions, position) * -1) - 2 ;
        Exercise exercise = mExerciseList.get(whichExercise);

        holder.numSet.setText(String.valueOf(exercise.exerciseSetList.get(position - mTopSectionPositions.get(whichExercise) - 1).number));

        holder.reps.setText(String.valueOf(exercise.exerciseSetList.get(position - mTopSectionPositions.get(whichExercise) - 1).reps));

        if (mHashMap == null)
            mHashMap = new HashMap<>();

        mHashMap.put(holder.reps, new CondensedTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mListener.setReps(whichExercise, position - mTopSectionPositions.get(whichExercise) - 1, s.toString());
            }
        });
        holder.reps.addTextChangedListener(mHashMap.get(holder.reps));

        holder.weight.setText(String.valueOf(exercise.exerciseSetList.get(position - mTopSectionPositions.get(whichExercise) - 1).weight));
        mHashMap.put(holder.weight, new CondensedTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mListener.setWeight(whichExercise, position - mTopSectionPositions.get(whichExercise) - 1, s.toString());
            }
        });
        holder.weight.addTextChangedListener(mHashMap.get(holder.weight));
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        if (holder.getItemViewType() == MiddleSectionViewHolder.VIEW_TYPE) {
            ((MiddleSectionViewHolder) holder).weight.removeTextChangedListener(mHashMap.get(((MiddleSectionViewHolder) holder).weight));
            ((MiddleSectionViewHolder) holder).reps.removeTextChangedListener(mHashMap.get(((MiddleSectionViewHolder) holder).reps));
        }
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        mExerciseList = exercises;
    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypeList.get(position);
    }

    @Override
    public int getItemCount() {
        return mViewTypeList.size();
    }

    public class TopSectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText exerciseName;
        ImageView restTimeIcon;
        ImageView superSetIcon;
        Context context;

        static final int VIEW_TYPE = 1;

        TopSectionViewHolder(TopSectionExercisesListItemBinding binding) {
            super(binding.getRoot());
            exerciseName = binding.exerciseTitle;
            context = binding.getRoot().getContext();
            restTimeIcon = binding.restTimeIcon;
            superSetIcon = binding.supersetIcon;
            restTimeIcon.setOnClickListener(this);
            superSetIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rest_time_icon:
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    float scale = context.getResources().getDisplayMetrics().density;
                    int width = (int) (200 * scale + 0.5f);
                    View layout = inflater.inflate(R.layout.popup_rest_time, null);
                    PopupWindow popup = new PopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        popup.showAsDropDown(restTimeIcon, 0, 0, Gravity.START);
                    }

                    NumberPicker numberPicker = layout.findViewById(R.id.number_picker);
                    numberPicker.setMinValue(0);
                    numberPicker.setMaxValue(19);
                    break;

                case R.id.superset_icon:
                    mListener.makeSuperset(getAdapterPosition());
                    break;

                default:
                    break;
            }
        }
    }

    public class MiddleSectionViewHolder extends RecyclerView.ViewHolder {
        TextView numSet;
        EditText reps;
        EditText weight;

        static final int VIEW_TYPE = 2;
        MiddleSectionViewHolder(MiddleSectionExercisesListItemBinding binding) {
            super(binding.getRoot());
            numSet = binding.tvSetNumber;
            reps = binding.etRep;
            weight = binding.etWeight;
        }
    }

    public class BottomSectionViewHolder extends RecyclerView.ViewHolder {
        TextView addSet;

        static final int VIEW_TYPE = 3;
        BottomSectionViewHolder(BottomSectionExercisesListItemBinding binding) {
            super(binding.getRoot());
            addSet = binding.tvAddSet;
            addSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int whichExercise = (Collections.binarySearch(mTopSectionPositions, getAdapterPosition()) * -1) - 2 ;
                    mListener.addSet(whichExercise, getAdapterPosition());
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        Button addExerciseButton;

        static final int VIEW_TYPE = 4;

        FooterViewHolder(FooterExercisesListItemBinding binding) {
            super(binding.getRoot());
            addExerciseButton = binding.btnAddExercise;
            addExerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.addExercise(getAdapterPosition());
                }
            });
        }
    }
}
