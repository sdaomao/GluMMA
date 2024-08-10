package com.example.glumma;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepReport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepReport extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BarChart weekBar, todaybar, monthbar;


    public StepReport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepReport.
     */
    // TODO: Rename and change types and number of parameters
    public static StepReport newInstance(String param1, String param2) {
        StepReport fragment = new StepReport();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_report, container, false);

        Button StepButton = view.findViewById(R.id.button7);
        StepButton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getActivity(), "You are already in step report", Toast.LENGTH_SHORT).show();
        });

        Button CalorieButton = view.findViewById(R.id.button6);
        CalorieButton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getActivity(), "Calorie button clicked", Toast.LENGTH_SHORT).show();
            Fragment fragment = new CaloriesReport();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        });

        weekBar = view.findViewById(R.id.weekbar);
        displayWeek();

        todaybar = view.findViewById(R.id.todaybar);
        displayToday();
        monthbar = view.findViewById(R.id.monthbar);
        displayMonth();

        return view;
    }

    private void displayWeek() {
        // Get the step data from shared preferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("StepData", MODE_PRIVATE);
        String existingData = sharedPreferences.getString("data", "[]");
        JSONArray dataArray;
        try {
            dataArray = new JSONArray(existingData);
        } catch (JSONException e) {
            dataArray = new JSONArray();
        }

        // Initialize an array to store the total steps for each day of the week
        float[] weeklySteps = new float[7];

        // Iterate over the data array
        for (int i = 0; i < dataArray.length(); i++) {
            try {
                JSONObject data = dataArray.getJSONObject(i);
                String dateStr = data.getString("Date");
                LocalDate date = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date = LocalDate.parse(dateStr);
                }
                int dayOfWeek = 0; // Monday is 1, Sunday is 7
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dayOfWeek = date.getDayOfWeek().getValue() % 7;
                }
                int steps = data.getInt("Steps");
                weeklySteps[dayOfWeek] += steps;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Create a list of entries for the bar chart
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < weeklySteps.length; i++) {
            entries.add(new BarEntry(i, weeklySteps[i]));
        }

        // Create a BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Week"); // add entries to dataset
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);

        // Create a BarData object with the dataset
        BarData barData = new BarData(dataSet);

        // Set the BarData object to the BarChart
        weekBar.setData(barData);

        // Set the title (description) of the chart
        Description description = new Description();
        weekBar.setDescription(description);

        // Set the labels for the X axis
        XAxis xAxis = weekBar.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Mon ", "Tue ", "Wed ", "THU ", "Fri ", "Sat ", "Sun "}));
        xAxis.setTextColor(Color.WHITE); // Set the color of the X axis labels to white

        // Set the color of the Y axis labels to white
        weekBar.getAxisLeft().setTextColor(Color.WHITE);
        weekBar.getAxisRight().setTextColor(Color.WHITE);
        weekBar.invalidate(); // refresh the chart
    }


    private void displayToday(){

        // Get the step data from shared preferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("StepData", MODE_PRIVATE);
        String existingData = sharedPreferences.getString("data", "[]");
        JSONArray dataArray;
        try {
            dataArray = new JSONArray(existingData);
        } catch (JSONException e) {
            dataArray = new JSONArray();
        }

        // Initialize an array to store the total steps for each hour of the day
        float[] hourlySteps = new float[24];

        // Get the current date
        LocalDate today = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = LocalDate.now();
        }

        // Iterate over the data array
        for (int i = 0; i < dataArray.length(); i++) {
            try {
                JSONObject data = dataArray.getJSONObject(i);
                String dateStr = data.getString("Date");
                LocalDate date = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date = LocalDate.parse(dateStr);

                    // Check if the data is for the current day
                    if (date != null && date.equals(today)) {
                        String timeStr = data.getString("time");
                        LocalTime time = null;

                        time = LocalTime.parse(timeStr);
                        int hour = 0;
                        hour = time.getHour();
                        int steps = data.getInt("Steps");
                        hourlySteps[hour] += steps;

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Create a list of entries for the bar chart
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < hourlySteps.length; i++) {
            entries.add(new BarEntry(i, hourlySteps[i]));
        }

        // Create a BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Today"); // add entries to dataset
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);

        // Create a BarData object with the dataset
        BarData barData = new BarData(dataSet);

        // Set the BarData object to the BarChart
        todaybar.setData(barData);

        // Set the title (description) of the chart
        Description description = new Description();
        todaybar.setDescription(description);

        // Set the labels for the X axis
        XAxis xAxis = todaybar.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM", "12 AM"}));
        xAxis.setTextColor(Color.WHITE); // Set the color of the X axis labels to white

        // Set the color of the Y axis labels to white
        todaybar.getAxisLeft().setTextColor(Color.WHITE);
        todaybar.getAxisRight().setTextColor(Color.WHITE);
        todaybar.invalidate(); // refresh the chart

    }


    private void displayMonth() {
        // Get the step data from shared preferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("StepData", MODE_PRIVATE);
        String existingData = sharedPreferences.getString("data", "[]");
        JSONArray dataArray;
        try {
            dataArray = new JSONArray(existingData);
        } catch (JSONException e) {
            dataArray = new JSONArray();
        }

        // Get the current date
        LocalDate today = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = LocalDate.now();
        }

        // Get the number of days in the current month
        int daysInMonth = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonth());
            daysInMonth = yearMonth.lengthOfMonth();
        }

        // Initialize an array to store the total steps for each day of the month
        float[] monthlySteps = new float[daysInMonth];

        // Iterate over the data array
        for (int i = 0; i < dataArray.length(); i++) {
            try {
                JSONObject data = dataArray.getJSONObject(i);
                String dateStr = data.getString("Date");
                LocalDate date = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date = LocalDate.parse(dateStr);
                }

                // Check if the data is for the current month
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (date != null && date.getMonth().equals(today.getMonth())) {
                        int dayOfMonth = 0;
                        dayOfMonth = date.getDayOfMonth();
                        int steps = data.getInt("Steps");
                        monthlySteps[dayOfMonth - 1] += steps;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Create a list of entries for the bar chart
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < monthlySteps.length; i++) {
            entries.add(new BarEntry(i, monthlySteps[i]));
        }

        // Create a BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Month"); // add entries to dataset
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);

        // Create a BarData object with the dataset
        BarData barData = new BarData(dataSet);

        // Set the BarData object to the BarChart
        monthbar.setData(barData);

        // Set the title (description) of the chart
        Description description = new Description();
        monthbar.setDescription(description);

        // Set the labels for the X axis
        XAxis xAxis = monthbar.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getDayLabels(daysInMonth)));
        xAxis.setTextColor(Color.WHITE); // Set the color of the X axis labels to white

        // Set the color of the Y axis labels to white
        monthbar.getAxisLeft().setTextColor(Color.WHITE);
        monthbar.getAxisRight().setTextColor(Color.WHITE);
        monthbar.invalidate(); // refresh the chart
    }

    //
    private String[] getDayLabels(int daysInMonth) {
        String[] labels = new String[daysInMonth];
        for (int i = 0; i < daysInMonth; i++) {
            labels[i] = String.valueOf(i + 1);
        }
        return labels;
    }


}