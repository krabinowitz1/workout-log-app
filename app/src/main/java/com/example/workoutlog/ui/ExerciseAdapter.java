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
import com.example.workoutlog.model.ExercisePerformedDraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Integer> mViewTypeList;
    private OnUpdateExerciseListener mListener;
    private ArrayList<Integer> mTopSectionPositions;
    private ArrayList<Exercise> mExerciseList;
    private HashMap<EditText, CondensedTextWatcher> mHashMap;
    private View.OnFocusChangeListener mOnFocusChangeListener;

    private ExercisePerformedDraft mExerciseDraft;
    private String mClassName;

    public ExerciseAdapter(ArrayList<Integer> viewTypeList, OnUpdateExerciseListener listener, ArrayList<Integer> topSectionPositions, String className) {
        mViewTypeList = viewTypeList;
        mListener = listener;
        mTopSectionPositions = topSectionPositions;
        mClassName = className;
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

        if(!mClassName.equals(ExerciseFragment.class.getSimpleName())) {
            Exercise exercise = mExerciseList.get(whichExercise);
            holder.exerciseName.setText(exercise.getName());
        }

        else {
            ExercisePerformedDraft exercisePerformedDraft = mExerciseDraft;
            holder.exerciseName.setText(exercisePerformedDraft.getName());
        }

        if (mHashMap == null)
            mHashMap = new HashMap<>();

        mHashMap.put(holder.exerciseName, new CondensedTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mListener.setName(whichExercise, s.toString());
            }
        });
        holder.exerciseName.addTextChangedListener(mHashMap.get(holder.exerciseName));
    }

    private void setMiddleSectionViewHolderAtPosition(MiddleSectionViewHolder holder, final int position) {
        final int whichExercise = (Collections.binarySearch(mTopSectionPositions, position) * -1) - 2 ;
        if(!mClassName.equals(ExerciseFragment.class.getSimpleName())) {
            Exercise exercise = mExerciseList.get(whichExercise);
            holder.numSet.setText(String.valueOf(exercise.getExerciseSetList().get(position - mTopSectionPositions.get(whichExercise) - 1).getNumber()));
            holder.reps.setText(String.valueOf(exercise.getExerciseSetList().get(position - mTopSectionPositions.get(whichExercise) - 1).getReps()));
            holder.weight.setText(String.valueOf(exercise.getExerciseSetList().get(position - mTopSectionPositions.get(whichExercise) - 1).getWeight()));
        }

        else {
            ExercisePerformedDraft exerciseDraft = mExerciseDraft;
            holder.numSet.setText(String.valueOf(exerciseDraft.exerciseSetWithHintList.get(position - mTopSectionPositions.get(whichExercise) - 1).getNumber()));
            holder.reps.setText(String.valueOf(exerciseDraft.exerciseSetWithHintList.get(position - mTopSectionPositions.get(whichExercise) - 1).getReps()));
            holder.weight.setText(String.valueOf(exerciseDraft.exerciseSetWithHintList.get(position - mTopSectionPositions.get(whichExercise) - 1).getWeight()));

            holder.reps.setHint(String.valueOf(exerciseDraft.exerciseSetWithHintList.get(position - mTopSectionPositions.get(whichExercise) - 1).getRepsHint()));
            holder.weight.setHint(String.valueOf(exerciseDraft.exerciseSetWithHintList.get(position - mTopSectionPositions.get(whichExercise) - 1).getWeightHint()));
        }

        if (mHashMap == null)
            mHashMap = new HashMap<>();

        mHashMap.put(holder.reps, new CondensedTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mListener.setReps(whichExercise, position - mTopSectionPositions.get(whichExercise) - 1, s.toString());
            }
        });
        holder.reps.addTextChangedListener(mHashMap.get(holder.reps));

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

        if (holder.getItemViewType() == TopSectionViewHolder.VIEW_TYPE) {
            ((TopSectionViewHolder) holder).exerciseName.removeTextChangedListener(mHashMap.get(((TopSectionViewHolder) holder).exerciseName));
        }
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        mOnFocusChangeListener = listener;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        mExerciseList = exercises;
    }

    public void setExerciseDraft(ExercisePerformedDraft exercisePerformedDraft) {
        mExerciseDraft = exercisePerformedDraft;
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
            exerciseName.setSelectAllOnFocus(true);
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
            reps.setSelectAllOnFocus(true);
            weight.setSelectAllOnFocus(true);
            reps.setOnFocusChangeListener(mOnFocusChangeListener);
            weight.setOnFocusChangeListener(mOnFocusChangeListener);
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
