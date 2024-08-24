package com.example.glumma;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.CurrentMedAdapter;
import information.MedicineData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentMeds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentMeds extends Fragment implements CurrentMedAdapter.OnDeleteClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private List<MedicineData> MedList;
    private CurrentMedAdapter currentMedAdapter;

    public CurrentMeds() {
        // Required empty public constructor
    }

    public static CurrentMeds newInstance(String param1, String param2) {
        CurrentMeds fragment = new CurrentMeds();
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
        View view = inflater.inflate(R.layout.fragment_current_meds, container, false);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ReplaceFragment(new Medications());
            }
        });

        Button addMedication = view.findViewById(R.id.button11);
        addMedication.setOnClickListener(v -> {
            ReplaceFragment(new Medications());
        });

        Drawable drawable = getResources().getDrawable(R.drawable.baseline_add_24);
        int newDrawableSize = 50;

        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, newDrawableSize, newDrawableSize, true);
        Drawable newDrawable = new BitmapDrawable(getResources(), scaledBitmap);
        addMedication.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        MedList = new ArrayList<>();
        currentMedAdapter = new CurrentMedAdapter(MedList, this);
        recyclerView.setAdapter(currentMedAdapter);
        loadMedications();

        return view;
    }

    private void loadMedications() {
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
                    String type = data.getString("Type");
                    JSONArray selectedInsulinArray = data.getJSONArray("SelectedMedicine");
                    for (int j = 0; j < selectedInsulinArray.length(); j++) {
                        String medicine = selectedInsulinArray.getString(j);
                        MedList.add(new MedicineData(medicine, type));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            currentMedAdapter.notifyDataSetChanged();
        }
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onDeleteClick(int position) {
        // Remove the item from the list
        MedList.remove(position);

        // Update SharedPreferences
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("Medications", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            JSONArray dataArray = new JSONArray();
            for (MedicineData medicineData : MedList) {
                try {
                    JSONObject data = new JSONObject();
                    data.put("Type", medicineData.getType());
                    JSONArray selectedInsulinArray = new JSONArray();
                    selectedInsulinArray.put(medicineData.getMedicine());
                    data.put("SelectedMedicine", selectedInsulinArray);
                    dataArray.put(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            editor.putString("data", dataArray.toString());
            editor.apply();
        }

        // Notify the adapter
        currentMedAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();
    }
}