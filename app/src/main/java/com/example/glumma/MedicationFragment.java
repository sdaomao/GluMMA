package com.example.glumma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MedicationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicationFragment newInstance(String param1, String param2) {
        MedicationFragment fragment = new MedicationFragment();
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
        View view = inflater.inflate(R.layout.fragment_medication, container, false);

        // This is for the send current medication button
        Button sendCurrentMedication = view.findViewById(R.id.button8);
          sendCurrentMedication.setOnClickListener(v -> {
                // Do something in response to button click
                Toast.makeText(getContext(), "Send current medication button clicked", Toast.LENGTH_SHORT).show();
                ReplaceFragment(new Medications());

          });

          // This is for the reminder button
            Button reminder = view.findViewById(R.id.button10);
            reminder.setOnClickListener(v -> {
                // Do something in response to button click
                Toast.makeText(getContext(), "Reminder button clicked", Toast.LENGTH_SHORT).show();
                ReplaceFragment(new Reminders());
            });


        return view;
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}