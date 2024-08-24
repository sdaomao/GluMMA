package com.example.glumma;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Insulin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Insulin extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ArrayList<String> selectedInsulin = new ArrayList<>();

    public Insulin() {
        // Required empty public constructor
    }

    public static Insulin newInstance(String param1, String param2) {
        Insulin fragment = new Insulin();
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
        View view = inflater.inflate(R.layout.fragment_medication_menu, container, false);

        selectedInsulin.add("Fiasp and NovoRapid®");
        selectedInsulin.add("Humalog®");
        selectedInsulin.add("Apirda®");
        selectedInsulin.add("Lantus®");
        selectedInsulin.add("Toujeo®");
        selectedInsulin.add("Levemir®");
        selectedInsulin.add("NovoMix®");
        selectedInsulin.add("Humalog® Mix 25");
        selectedInsulin.add("Humalog® Mix 30");
        selectedInsulin.add("Ryzodeg® 70:30");
        selectedInsulin.add("Mixtard® 30/70");
        selectedInsulin.add("Mixtard® 50/50");
        selectedInsulin.add("Humulin® 30/70");

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ReplaceFragment(new Medications());
            }
        });

        ImageButton backBtn = view.findViewById(R.id.imageButton13);
        backBtn.setOnClickListener(v -> {
            ReplaceFragment(new Medications());
        });

        CheckBox fiasp_and_novorapid = view.findViewById(R.id.checkBox);
        CheckBox humalog1 = view.findViewById(R.id.checkBox2);
        CheckBox apirda = view.findViewById(R.id.checkBox4);
        CheckBox lantus = view.findViewById(R.id.checkBox5);
        CheckBox toujeo = view.findViewById(R.id.checkBox6);
        CheckBox levemir = view.findViewById(R.id.checkBox7);
        CheckBox novomix = view.findViewById(R.id.checkBox8);
        CheckBox humalog2 = view.findViewById(R.id.checkBox9);
        CheckBox humalog3 = view.findViewById(R.id.checkBox10);
        CheckBox ryzodeg = view.findViewById(R.id.checkBox11);
        CheckBox mixtard1 = view.findViewById(R.id.checkBox14);
        CheckBox mixtard2 = view.findViewById(R.id.checkBox15);
        CheckBox humulinn = view.findViewById(R.id.checkBox16);

        Button SetMedicationns = view.findViewById(R.id.button13);
        SetMedicationns.setOnClickListener(v -> {
            SaveMeds(view);
        });

        loadSelectedInsulinTypes(view);

        return view;
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    private ArrayList<String> getSelectedInsulinTypes(View view) {
        ArrayList<String> selectedInsulin = new ArrayList<>();

        CheckBox fiasp_and_novorapid = view.findViewById(R.id.checkBox);
        if (fiasp_and_novorapid.isChecked()) {
            selectedInsulin.add("Fiasp and NovoRapid®");
        }

        CheckBox humalog1 = view.findViewById(R.id.checkBox2);
        if (humalog1.isChecked()) {
            selectedInsulin.add("Humalog®");
        }

        CheckBox apirda = view.findViewById(R.id.checkBox4);
        if (apirda.isChecked()) {
            selectedInsulin.add("Apirda®");
        }

        CheckBox lantus = view.findViewById(R.id.checkBox5);
        if (lantus.isChecked()) {
            selectedInsulin.add("Lantus®");
        }

        CheckBox toujeo = view.findViewById(R.id.checkBox6);
        if (toujeo.isChecked()) {
            selectedInsulin.add("Toujeo®");
        }

        CheckBox levemir = view.findViewById(R.id.checkBox7);
        if (levemir.isChecked()) {
            selectedInsulin.add("Levemir®");
        }

        CheckBox novomix = view.findViewById(R.id.checkBox8);
        if (novomix.isChecked()) {
            selectedInsulin.add("NovoMix®");
        }

        CheckBox humalog2 = view.findViewById(R.id.checkBox9);
        if (humalog2.isChecked()) {
            selectedInsulin.add("Humalog® Mix 25");
        }

        CheckBox humalog3 = view.findViewById(R.id.checkBox10);
        if (humalog3.isChecked()) {
            selectedInsulin.add("Humalog® Mix 30");
        }

        CheckBox ryzodeg = view.findViewById(R.id.checkBox11);
        if (ryzodeg.isChecked()) {
            selectedInsulin.add("Ryzodeg® 70:30");
        }

        CheckBox mixtard1 = view.findViewById(R.id.checkBox14);
        if (mixtard1.isChecked()) {
            selectedInsulin.add("Mixtard® 30/70");
        }

        CheckBox mixtard2 = view.findViewById(R.id.checkBox15);
        if (mixtard2.isChecked()) {
            selectedInsulin.add("Mixtard® 50/50");
        }

        CheckBox humulinn = view.findViewById(R.id.checkBox16);
        if (humulinn.isChecked()) {
            selectedInsulin.add("Humulin® 30/70");
        }

        return selectedInsulin;
    }

    // Save the seected medications
    private void SaveMeds(View view) {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("Medications", MODE_PRIVATE);
            String existingData = sharedPreferences.getString("data", "[]");
            JSONArray dataArray;
            try {
                dataArray = new JSONArray(existingData);
            } catch (JSONException e) {
                dataArray = new JSONArray();
            }

            JSONObject newData = new JSONObject();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate today = LocalDate.now();
                    LocalTime time = LocalTime.now();
                    newData.put("Date", today.toString());
                    newData.put("time", time.toString());
                    newData.put("Type", "Insulin");
                    newData.put("SelectedMedicine", new JSONArray(getSelectedInsulinTypes(view)));
                    dataArray.put(newData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("data", dataArray.toString());
            editor.apply();

            Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();

            ReplaceFragment(new CurrentMeds());
        } else {
            Toast.makeText(getContext(), "Context is null", Toast.LENGTH_SHORT).show();
        }
    }

    // Load the insulin
    private void loadSelectedInsulinTypes(View view) {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("Medications", MODE_PRIVATE);
            String existingData = sharedPreferences.getString("data", "[]");
            try {
                JSONArray dataArray = new JSONArray(existingData);
                if (dataArray.length() > 0) {
                    JSONObject latestData = dataArray.getJSONObject(dataArray.length() - 1);
                    JSONArray selectedInsulinArray = latestData.getJSONArray("SelectedMedicine");

                    for (int i = 0; i < selectedInsulinArray.length(); i++) {
                        String insulinType = selectedInsulinArray.getString(i);
                        switch (insulinType) {
                            case "Fiasp and NovoRapid®":
                                ((CheckBox) view.findViewById(R.id.checkBox)).setChecked(true);
                                break;
                            case "Humalog®":
                                ((CheckBox) view.findViewById(R.id.checkBox2)).setChecked(true);
                                break;
                            case "Apirda®":
                                ((CheckBox) view.findViewById(R.id.checkBox4)).setChecked(true);
                                break;
                            case "Lantus®":
                                ((CheckBox) view.findViewById(R.id.checkBox5)).setChecked(true);
                                break;
                            case "Toujeo®":
                                ((CheckBox) view.findViewById(R.id.checkBox6)).setChecked(true);
                                break;
                            case "Levemir®":
                                ((CheckBox) view.findViewById(R.id.checkBox7)).setChecked(true);
                                break;
                            case "NovoMix®":
                                ((CheckBox) view.findViewById(R.id.checkBox8)).setChecked(true);
                                break;
                            case "Humalog® Mix 25":
                                ((CheckBox) view.findViewById(R.id.checkBox9)).setChecked(true);
                                break;
                            case "Humalog® Mix 30":
                                ((CheckBox) view.findViewById(R.id.checkBox10)).setChecked(true);
                                break;
                            case "Ryzodeg® 70:30":
                                ((CheckBox) view.findViewById(R.id.checkBox11)).setChecked(true);
                                break;
                            case "Mixtard® 30/70":
                                ((CheckBox) view.findViewById(R.id.checkBox14)).setChecked(true);
                                break;
                            case "Mixtard® 50/50":
                                ((CheckBox) view.findViewById(R.id.checkBox15)).setChecked(true);
                                break;
                            case "Humulin® 30/70":
                                ((CheckBox) view.findViewById(R.id.checkBox16)).setChecked(true);
                                break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}