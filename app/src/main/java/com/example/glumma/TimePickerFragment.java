package com.example.glumma;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface TimePickerListener {
        void onTimeSet(TimePicker timePicker, int hour, int minute);
    }

    TimePickerListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            Fragment fragment = getParentFragment();
            if (fragment != null && fragment instanceof TimePickerListener) {
                mListener = (TimePickerListener) fragment;
            } else if (context instanceof TimePickerListener) {
                mListener = (TimePickerListener) context;
            } else {
                throw new ClassCastException("Host must implement TimePickerListener");
            }
        } catch (Exception e) {
            throw new ClassCastException(requireActivity().toString() + " must implement TimePickerListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        return new TimePickerDialog(requireActivity(), this, hour, minute, DateFormat.is24HourFormat(requireContext()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        mListener.onTimeSet(timePicker, hour, minute);
    }
}