package com.example.glumma;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetTimer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetTimer extends Fragment implements TimePickerFragment.TimePickerListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView tvDisplayTime;
    private EditText Label;
    private SwitchCompat Vibration;
    private CheckBox Notification;
    private CheckBox Alarm;
    private boolean monday1 = false;
    private boolean tuesday1 = false;
    private boolean wednesday1 = false;
    private boolean thursday1= false;
    private boolean friday1 = false;
    private boolean saturday1 = false;
    private boolean sunday1 = false;


    public SetTimer() {
        // Required empty public constructor
    }

    public static SetTimer newInstance(String param1, String param2) {
        SetTimer fragment = new SetTimer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_timer, container, false);

        tvDisplayTime = view.findViewById(R.id.tvDisplayTime);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ReplaceFragment(new Reminders());
            }
        });

        Button btnShowTimePicker = view.findViewById(R.id.btnShowTimePicker);
        btnShowTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);
                timePickerFragment.show(getParentFragmentManager(), "timePicker");
            }
        });

        Button monday = view.findViewById(R.id.mondaybutton);
        monday.setOnClickListener(v -> {
            if (monday1) {
                monday1 = false;
                monday.setBackgroundColor(getResources().getColor(R.color.baseColor));
                monday.setTextColor(getResources().getColor(R.color.primaryColor));
                Toast.makeText(getContext(), "Unselected Monday", Toast.LENGTH_SHORT).show();
            } else {
                monday1 = true;
                monday.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                monday.setTextColor(getResources().getColor(R.color.baseColor));
                Toast.makeText(getContext(), "Selected Monday", Toast.LENGTH_SHORT).show();
            }
        });

        Button tuesday = view.findViewById(R.id.tuesdaybutton);
        tuesday.setOnClickListener(v -> {
            // Do something in response to button click
            if (tuesday1) {
                tuesday1 = false;
                tuesday.setBackgroundColor(getResources().getColor(R.color.baseColor));
                tuesday.setTextColor(getResources().getColor(R.color.primaryColor));
                Toast.makeText(getContext(), "Unselected Tuesday", Toast.LENGTH_SHORT).show();
            } else {
                tuesday1 = true;
                tuesday.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                tuesday.setTextColor(getResources().getColor(R.color.baseColor));
                Toast.makeText(getContext(), "Selected Tuesday", Toast.LENGTH_SHORT).show();
            }
        });

        Button wednesday = view.findViewById(R.id.wednesdaybutton);
        wednesday.setOnClickListener(v -> {
            // Do something in response to button click
            if (wednesday1) {
                wednesday1 = false;
                wednesday.setBackgroundColor(getResources().getColor(R.color.baseColor));
                wednesday.setTextColor(getResources().getColor(R.color.primaryColor));
                Toast.makeText(getContext(), "Unselected Wednesday", Toast.LENGTH_SHORT).show();
            } else {
                wednesday1 = true;
                wednesday.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                wednesday.setTextColor(getResources().getColor(R.color.baseColor));
                Toast.makeText(getContext(), "Selected Wednesday", Toast.LENGTH_SHORT).show();
            }
        });

        Button thursday = view.findViewById(R.id.thursdaybutton);
        thursday.setOnClickListener(v -> {
            // Do something in response to button click
            if (thursday1) {
                thursday1 = false;
                thursday.setBackgroundColor(getResources().getColor(R.color.baseColor));
                thursday.setTextColor(getResources().getColor(R.color.primaryColor));
                Toast.makeText(getContext(), "Unselected Thursday", Toast.LENGTH_SHORT).show();
            } else {
                thursday1 = true;
                thursday.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                thursday.setTextColor(getResources().getColor(R.color.baseColor));
                Toast.makeText(getContext(), "Selected Thursday", Toast.LENGTH_SHORT).show();
            }
        });

        Button friday = view.findViewById(R.id.fridaybutton);
        friday.setOnClickListener(v -> {
            // Do something in response to button click
            if (friday1) {
                friday1 = false;
                friday.setBackgroundColor(getResources().getColor(R.color.baseColor));
                friday.setTextColor(getResources().getColor(R.color.primaryColor));
                Toast.makeText(getContext(), "Unselected Friday", Toast.LENGTH_SHORT).show();
            } else {
                friday1 = true;
                friday.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                friday.setTextColor(getResources().getColor(R.color.baseColor));
                Toast.makeText(getContext(), "Selected Friday", Toast.LENGTH_SHORT).show();
            }
        });

        Vibration = view.findViewById(R.id.switch2);
        Vibration.setOnClickListener(v -> {
                    // Do something in response to button click
                });

        Button saturday = view.findViewById(R.id.saturdaybutton);
        saturday.setOnClickListener(v -> {
            // Do something in response to button click
            if (saturday1) {
                saturday1 = false;
                saturday.setBackgroundColor(getResources().getColor(R.color.baseColor));
                saturday.setTextColor(getResources().getColor(R.color.primaryColor));
                Toast.makeText(getContext(), "Unselected Saturday", Toast.LENGTH_SHORT).show();
            } else {
                saturday1 = true;
                saturday.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                saturday.setTextColor(getResources().getColor(R.color.baseColor));
                Toast.makeText(getContext(), "Selected Saturday", Toast.LENGTH_SHORT).show();
            }
        });

        Button sunday = view.findViewById(R.id.sundaybutton);
        sunday.setOnClickListener(v -> {
            // Do something in response to button click
            if (sunday1) {
                sunday1 = false;
                sunday.setBackgroundColor(getResources().getColor(R.color.baseColor));
                sunday.setTextColor(getResources().getColor(R.color.primaryColor));
                Toast.makeText(getContext(), "Unselected Sunday", Toast.LENGTH_SHORT).show();
            } else {
                sunday1 = true;
                sunday.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                sunday.setTextColor(getResources().getColor(R.color.baseColor));
                Toast.makeText(getContext(), "Selected Sunday", Toast.LENGTH_SHORT).show();
            }
        });

        Label = view.findViewById(R.id.editTextText2);

        Button DoneText = view.findViewById(R.id.button11);
        DoneText.setOnClickListener(v -> {
            // Do something in response to button click
            if (Label.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a label", Toast.LENGTH_SHORT).show();
                return;
            }
            if (tvDisplayTime.getText().toString().equals("00:00")) {
                Toast.makeText(getContext(), "Please enter a time", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!monday1 && !tuesday1 && !wednesday1 && !thursday1 && !friday1 && !saturday1 && !sunday1) {
                Toast.makeText(getContext(), "Please select a day", Toast.LENGTH_SHORT).show();
                return;
            }

            else {
                SaveTime(view);
            }

        });

        Notification = view.findViewById(R.id.checkBox3);
        Notification.setOnClickListener(v -> {
            // Do something in response to button click

        });

        Alarm = view.findViewById(R.id.checkBox20);
        Alarm.setOnClickListener(v -> {
            // Do something in response to button click

        });

        // This is for the back button
        ImageButton back = view.findViewById(R.id.imageButton14);
        back.setOnClickListener(v -> {
            // Do something in response to button click
            ReplaceFragment(new Reminders());
        });

        ImageButton save = view.findViewById(R.id.imageButton16);
        save.setOnClickListener(v -> {
           if (Label.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a label", Toast.LENGTH_SHORT).show();
                return;
            }
           if (tvDisplayTime.getText().toString().equals("00:00")) {
                Toast.makeText(getContext(), "Please enter a time", Toast.LENGTH_SHORT).show();
                return;
            }

           if (!monday1 && !tuesday1 && !wednesday1 && !thursday1 && !friday1 && !saturday1 && !sunday1) {
                Toast.makeText(getContext(), "Please select a day", Toast.LENGTH_SHORT).show();
                return;
            }

           else {
               SaveTime(view);
           }



        });




        return view;
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        // Convert hour to 12-hour format and determine AM/PM
        String period = (hour >= 12) ? "PM" : "AM";
        hour = (hour > 12) ? hour - 12 : hour;
        hour = (hour == 0) ? 12 : hour; // Handle midnight and noon

        // Update the TextView
        tvDisplayTime.setText(String.format("%02d:%02d %s", hour, minute, period));
    }

    public void updateTimeDisplay(int hour, int minute) {
        // Convert hour to 12-hour format and determine AM/PM
        String period = (hour >= 12) ? "PM" : "AM";
        hour = (hour > 12) ? hour - 12 : hour;
        hour = (hour == 0) ? 12 : hour; // Handle midnight and noon

        // Update the TextView
        tvDisplayTime.setText(String.format("%02d:%02d %s", hour, minute, period));
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    // This is for every day function
    private boolean isEveryDaySelected() {
        return monday1 && tuesday1 && wednesday1 && thursday1 && friday1 && saturday1 && sunday1;
    }

    private void SaveTime(View view) {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("Reminder", MODE_PRIVATE);
            String existingData = sharedPreferences.getString("data", "[]");
            JSONArray dataArray;
            try {
                dataArray = new JSONArray(existingData);
            } catch (JSONException e) {
                dataArray = new JSONArray();
            }

            JSONObject newData = new JSONObject();
            boolean exists = false;
            try {
                newData.put("time", tvDisplayTime.getText().toString());
                newData.put("vibration", Vibration.isChecked());
                newData.put("alarm", Alarm.isChecked());
                newData.put("notification", Notification.isChecked());
                newData.put("label", Label.getText().toString());

                // Check if all days are selected
                if (isEveryDaySelected()) {
                    newData.put("everyday", true);
                } else {
                    newData.put("everyday", false);
                    newData.put("monday", monday1);
                    newData.put("tuesday", tuesday1);
                    newData.put("wednesday", wednesday1);
                    newData.put("thursday", thursday1);
                    newData.put("friday", friday1);
                    newData.put("saturday", saturday1);
                    newData.put("sunday", sunday1);
                }

                // Check if reminder already exists
                exists = false;
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject existingDataObject = dataArray.getJSONObject(i);
                    if (existingDataObject.getString("time").equals(newData.getString("time")) &&
                            existingDataObject.getString("label").equals(newData.getString("label"))) {
                        dataArray.put(i, newData); // Update existing reminder
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    dataArray.put(newData); // Add new reminder if it doesn't exist
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("data", dataArray.toString());
            editor.apply();

            Toast.makeText(getContext(), exists ? "Reminder updated successfully" : "Reminder saved successfully", Toast.LENGTH_SHORT).show();

            ReplaceFragment(new Reminders());
        } else {
            Toast.makeText(getContext(), "Context is null", Toast.LENGTH_SHORT).show();
        }
    }


}