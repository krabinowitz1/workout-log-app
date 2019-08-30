package com.example.workoutlog.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.DialogSaveAsBinding;

import java.util.ArrayList;

public class SaveAsDialogFragment extends DialogFragment {
    private DialogSaveAsBinding mBinding;
    private ArrayList<String> mListData;
    private static final String KEY_LIST_DATA = "KEY_LIST_DATA";

    public interface SaveAsDialogListener {
        void onSaveDialog(String inputText1, String inputText2);
    }

    public static SaveAsDialogFragment newInstance(ArrayList<String> data) {
        SaveAsDialogFragment dialog = new SaveAsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY_LIST_DATA, data);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListData = getArguments().getStringArrayList(KEY_LIST_DATA);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_save_as, null, false);
        mBinding.dialogCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    fillDescription();
                }

                else {
                    clearDescription();
                }
            }
        });

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveAsDialogListener listener = (SaveAsDialogListener) getActivity();
                listener.onSaveDialog(mBinding.dialogWorkoutName.getText().toString(), mBinding.dialogWorkoutDescription.getText().toString());
                dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.setView(mBinding.getRoot()).create();
    }

    private void fillDescription() {
        mBinding.dialogWorkoutDescription.setText(listToString());
    }

    private void clearDescription() {
        mBinding.dialogWorkoutDescription.setText("");
    }


    private String listToString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < mListData.size(); i++) {
            sb.append(mListData.get(i)).append(i == mListData.size() - 1 ? "" : ", ");
        }

        return sb.toString();
    }
}
