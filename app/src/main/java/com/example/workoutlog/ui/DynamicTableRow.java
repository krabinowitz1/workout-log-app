package com.example.workoutlog.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DynamicTableRow extends TableRow {
    private Context context;
    public EditText repsEditText;
    public EditText weightEditText;
    private int tableChildCount;
    private MyEditTextListener weightsEditTextListener;
    private MyEditTextListener repsEditTextListener;

    public interface OnUpdateExerciseListener {
        void onUpdateExercise(String data, int position);
    }

    public DynamicTableRow(Context context, int tableChildCount) {
        super(context);
        this.context = context;
        this.tableChildCount = tableChildCount;
    }

    public void setWeightsEditTextListener(MyEditTextListener listener) {
        weightsEditTextListener = listener;
    }

    public void setRepsEditTextListener(MyEditTextListener listener) {
        repsEditTextListener = listener;
    }

    private void setParams() {
        setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
    }

    private void setColor() {
        setBackgroundColor(Color.parseColor("#DAE8FC"));
    }

    private void setPadding() {
        setPadding(5,5,5,5);
    }

    public void init() {
        setParams();
        setColor();
        setPadding();
        addViews();
    }

    public void addViews() {
        TextView tv = new TextView(context);
        tv.setLayoutParams(getLayoutParams());
        tv.setText(Integer.toString(tableChildCount));
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        addView(tv);

        weightEditText = new EditText(context);
        weightEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        weightEditText.setLayoutParams(getLayoutParams());
        weightEditText.addTextChangedListener(weightsEditTextListener);

        repsEditText = new EditText(context);
        repsEditText.setLayoutParams(getLayoutParams());
        repsEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        repsEditText.addTextChangedListener(repsEditTextListener);

        addView(repsEditText);
        addView(weightEditText);
    }

    public class MyEditTextListener implements TextWatcher {
        private OnUpdateExerciseListener listener;

        public void setListener(OnUpdateExerciseListener listener) {
            this.listener = listener;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            listener.onUpdateExercise(s.toString(), tableChildCount - 1);
        }
    }
}
