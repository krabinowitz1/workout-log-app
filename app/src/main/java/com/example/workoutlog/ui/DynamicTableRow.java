package com.example.workoutlog.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DynamicTableRow extends TableRow {
    private Context context;
    public EditText repsEditText;
    public EditText weightEditText;
    public int tableChildCount;

    public DynamicTableRow(Context context, int tableChildCount, OnUpdateExerciseListener listener) {
        super(context);
        this.context = context;
        this.tableChildCount = tableChildCount;
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
        weightEditText.setSelectAllOnFocus(true);

        repsEditText = new EditText(context);
        repsEditText.setLayoutParams(getLayoutParams());
        repsEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        repsEditText.setSelectAllOnFocus(true);

        addView(repsEditText);
        addView(weightEditText);
    }
}
