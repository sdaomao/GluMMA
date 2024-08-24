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
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Oral#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Oral extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> oralMedications = new ArrayList<String>();

    public Oral() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Oral.
     */
    // TODO: Rename and change types and number of parameters
    public static Oral newInstance(String param1, String param2) {
        Oral fragment = new Oral();
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
        View view = inflater.inflate(R.layout.fragment_oral, container, false);

        oralMedications.add("Acarbose(Precose®)");
        oralMedications.add("Miglitol(Glyset®)");
        oralMedications.add("Glucophage®");
        oralMedications.add("Glucophage XR®");
        oralMedications.add("Glumetza®");
        oralMedications.add("Fortamet®");
        oralMedications.add("Riomet®");
        oralMedications.add("Bromocriptine(Cycloset®)");
        oralMedications.add("Alogliptin(Nesina®)");
        oralMedications.add("Linagliptin(Tradjenta®)");
        oralMedications.add("Saxagliptin(Onglyza®)");
        oralMedications.add("Sitagliptin(Januvia®)");
        oralMedications.add("Nateglinide(Starlix®)");
        oralMedications.add("Repaglinide(Prandin®)");
        oralMedications.add("Canagliflozin(Invokana®)");
        oralMedications.add("Dapagliflozin(Farxiga®)");
        oralMedications.add("Empagliflozin(Jardiance®)");


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // This will replace the fragment with the MapFragment
                ReplaceFragment(new Medications());
            }
        });

        ImageButton backBtn = view.findViewById(R.id.imageButton13);
        backBtn.setOnClickListener(v -> {
            // Do something in response to button click
            ReplaceFragment(new Medications());
        });

        CheckBox acarbos = view.findViewById(R.id.checkBox);
        CheckBox miglitol = view.findViewById(R.id.checkBox2);
        CheckBox glucophage1 = view.findViewById(R.id.checkBox4);
        CheckBox glucophage2 = view.findViewById(R.id.checkBox5);
        CheckBox glumetza = view.findViewById(R.id.checkBox6);
        CheckBox fortamet = view.findViewById(R.id.checkBox7);
        CheckBox riomet = view.findViewById(R.id.checkBox8);
        CheckBox bromocriptine = view.findViewById(R.id.checkBox9);
        CheckBox alogliptin = view.findViewById(R.id.checkBox10);
        CheckBox linagliptin = view.findViewById(R.id.checkBox11);
        CheckBox saxagliptin = view.findViewById(R.id.checkBox14);
        CheckBox sitagliptin = view.findViewById(R.id.checkBox15);
        CheckBox nateglinide = view.findViewById(R.id.checkBox16);
        CheckBox repaglinide = view.findViewById(R.id.checkBox13);
        CheckBox cannagliflozin = view.findViewById(R.id.checkBox17);
        CheckBox dapagliflozin = view.findViewById(R.id.checkBox18);
        CheckBox empagliflozin = view.findViewById(R.id.checkBox19);

        Button saveBtn = view.findViewById(R.id.button13);
        saveBtn.setOnClickListener(v -> {
            SaveOral(view);
        });

        loadOral(view);



        return view;
    }
    private ArrayList<String> getOralType(View view){
        ArrayList<String> oralMedications = new ArrayList<String>();
        CheckBox acarbos = view.findViewById(R.id.checkBox);
        if (acarbos.isChecked()) {
            oralMedications.add("Acarbose(Precose®)");
        }

        CheckBox miglitol = view.findViewById(R.id.checkBox2);
        if (miglitol.isChecked()) {
            oralMedications.add("Miglitol(Glyset®)");
        }

        CheckBox glucophage1 = view.findViewById(R.id.checkBox4);
        if (glucophage1.isChecked()) {
            oralMedications.add("Glucophage®");
        }

        CheckBox glucophage2 = view.findViewById(R.id.checkBox5);
        if (glucophage2.isChecked()) {
            oralMedications.add("Glucophage XR®");
        }

        CheckBox glumetza = view.findViewById(R.id.checkBox6);
        if (glumetza.isChecked()) {
            oralMedications.add("Glumetza®");
        }

        CheckBox fortamet = view.findViewById(R.id.checkBox7);
        if (fortamet.isChecked()) {
            oralMedications.add("Fortamet®");
        }

        CheckBox riomet = view.findViewById(R.id.checkBox8);
        if (riomet.isChecked()) {
            oralMedications.add("Riomet®");
        }

        CheckBox bromocriptine = view.findViewById(R.id.checkBox9);
        if (bromocriptine.isChecked()) {
            oralMedications.add("Bromocriptine(Cycloset®)");
        }

        CheckBox alogliptin = view.findViewById(R.id.checkBox10);
        if (alogliptin.isChecked()) {
            oralMedications.add("Alogliptin(Nesina®)");
        }

        CheckBox linagliptin = view.findViewById(R.id.checkBox11);
        if (linagliptin.isChecked()) {
            oralMedications.add("Linagliptin(Tradjenta®)");
        }

        CheckBox saxagliptin = view.findViewById(R.id.checkBox14);
        if (saxagliptin.isChecked()) {
            oralMedications.add("Saxagliptin(Onglyza®)");
        }

        CheckBox sitagliptin = view.findViewById(R.id.checkBox15);
        if (sitagliptin.isChecked()) {
            oralMedications.add("Sitagliptin(Januvia®)");
        }

        CheckBox nateglinide = view.findViewById(R.id.checkBox16);
        if (nateglinide.isChecked()) {
            oralMedications.add("Nateglinide(Starlix®)");
        }

        CheckBox repaglinide = view.findViewById(R.id.checkBox13);
        if (repaglinide.isChecked()) {
            oralMedications.add("Repaglinide(Prandin®)");
        }

        CheckBox cannagliflozin = view.findViewById(R.id.checkBox17);
        if (cannagliflozin.isChecked()) {
            oralMedications.add("Canagliflozin(Invokana®)");
        }

        CheckBox dapagliflozin = view.findViewById(R.id.checkBox18);
        if (dapagliflozin.isChecked()) {
            oralMedications.add("Dapagliflozin(Farxiga®)");
        }

        CheckBox empagliflozin = view.findViewById(R.id.checkBox19);
        if (empagliflozin.isChecked()) {
            oralMedications.add("Canagliflozin(Invokana®)");
        }



        return oralMedications;
    }

    // Save the seected medications
    private void SaveOral(View view) {
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
                    newData.put("Type", "Oral");
                    newData.put("SelectedMedicine", new JSONArray(getOralType(view)));
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
    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    private void loadOral(View view) {
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

            for (int i = 0; i < dataArray.length(); i++) {
                try {
                    JSONObject data = dataArray.getJSONObject(i);
                    if (data.getString("Type").equals("Oral")) {
                        JSONArray selectedMedicine = data.getJSONArray("SelectedMedicine");
                        for (int j = 0; j < selectedMedicine.length(); j++) {
                            String medicine = selectedMedicine.getString(j);
                            switch (medicine) {
                                case "Acarbose(Precose®)":
                                    CheckBox acarbos = view.findViewById(R.id.checkBox);
                                    acarbos.setChecked(true);
                                    break;
                                case "Miglitol(Glyset®)":
                                    CheckBox miglitol = view.findViewById(R.id.checkBox2);
                                    miglitol.setChecked(true);
                                    break;
                                case "Glucophage®":
                                    CheckBox glucophage1 = view.findViewById(R.id.checkBox4);
                                    glucophage1.setChecked(true);
                                    break;
                                case "Glucophage XR®":
                                    CheckBox glucophage2 = view.findViewById(R.id.checkBox5);
                                    glucophage2.setChecked(true);
                                    break;
                                case "Glumetza®":
                                    CheckBox glumetza = view.findViewById(R.id.checkBox6);
                                    glumetza.setChecked(true);
                                    break;
                                case "Fortamet®":
                                    CheckBox fortamet = view.findViewById(R.id.checkBox7);
                                    fortamet.setChecked(true);
                                    break;
                                case "Riomet®":
                                    CheckBox riomet = view.findViewById(R.id.checkBox8);
                                    riomet.setChecked(true);
                                    break;
                                case "Bromocriptine(Cycloset®)":
                                    CheckBox bromocriptine = view.findViewById(R.id.checkBox9);
                                    bromocriptine.setChecked(true);
                                    break;
                                case "Alogliptin(Nesina®)":
                                    CheckBox alogliptin = view.findViewById(R.id.checkBox10);
                                    alogliptin.setChecked(true);
                                    break;
                                case "Linagliptin(Tradjenta®)":
                                    CheckBox linagliptin = view.findViewById(R.id.checkBox11);
                                    linagliptin.setChecked(true);
                                    break;
                                case "Saxagliptin(Onglyza®)":
                                    CheckBox saxagliptin = view.findViewById(R.id.checkBox14);
                                    saxagliptin.setChecked(true);
                                    break;
                                case "Sitagliptin(Januvia®)":
                                    CheckBox sitagliptin = view.findViewById(R.id.checkBox15);
                                    sitagliptin.setChecked(true);
                                    break;
                                case "Nateglinide(Starlix®)":
                                    CheckBox nateglinide = view.findViewById(R.id.checkBox16);
                                    nateglinide.setChecked(true);
                                    break;
                                case "Repaglinide(Prandin®)":
                                    CheckBox repaglinide = view.findViewById(R.id.checkBox13);
                                    repaglinide.setChecked(true);
                                    break;
                                case "Canagliflozin(Invokana®)":
                                    CheckBox cannagliflozin = view.findViewById(R.id.checkBox17);
                                    cannagliflozin.setChecked(true);
                                    break;
                                case "Dapagliflozin(Farxiga®)":
                                    CheckBox dapagliflozin = view.findViewById(R.id.checkBox18);
                                    dapagliflozin.setChecked(true);
                                    break;
                                case "Empagliflozin(Jardiance®)":
                                    CheckBox empagliflozin = view.findViewById(R.id.checkBox19);
                                    empagliflozin.setChecked(true);
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
}