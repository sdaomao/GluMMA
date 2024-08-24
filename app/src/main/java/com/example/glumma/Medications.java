package com.example.glumma;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Medications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Medications extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Medications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Medications.
     */
    // TODO: Rename and change types and number of parameters
    public static Medications newInstance(String param1, String param2) {
        Medications fragment = new Medications();
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
        View view = inflater.inflate(R.layout.fragment_medications, container, false);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // This will replace the fragment with the MapFragment
                ReplaceFragment(new MedicationFragment());
            }
        });

        Button Insulin = view.findViewById(R.id.button10);
        Insulin.setOnClickListener(v -> {
            // Do something in response to button click
            // Toast.makeText(getApplicationContext(), "Insulin button clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(dashboard.this, Insulin.class));
            ReplaceFragment(new Insulin());
        });

        Button Meds = view.findViewById(R.id.button8);
        Meds.setOnClickListener(v -> {
            // Do something in response to button click
            // Toast.makeText(getApplicationContext(), "Medication button clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(dashboard.this, Medications.class));
            ReplaceFragment(new Oral());
        });

        Button ViewCurrentMedication = view.findViewById(R.id.button9);
        ViewCurrentMedication.setOnClickListener(v -> {
            // Do something in response to button click
            // Toast.makeText(getApplicationContext(), "View current medication button clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(dashboard.this, ViewCurrentMedication.class));
            ReplaceFragment(new CurrentMeds());
        });

        return view;
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}