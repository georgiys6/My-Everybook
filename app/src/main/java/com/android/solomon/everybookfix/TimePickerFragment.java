package com.android.solomon.everybookfix;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,null).create();
    }
}
