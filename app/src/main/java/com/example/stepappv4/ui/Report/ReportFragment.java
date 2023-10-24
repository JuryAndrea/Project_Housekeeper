package com.example.stepappv4.ui.Report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


import com.example.stepappv4.StepAppOpenHelper;
import com.example.stepappv4.databinding.FragmentGalleryBinding;
import com.example.stepappv4.R;

public class ReportFragment extends Fragment {

    public StepAppOpenHelper stepAppOpenHelper;

    public int todaySteps = 0;
    public Button button_get_all;
    public Button button_today;
    public Button button_delete;
    public TextView completedStepsText;

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReportViewModel galleryViewModel =
                new ViewModelProvider(this).get(ReportViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        stepAppOpenHelper = new StepAppOpenHelper(getContext());
        completedStepsText = (TextView) root.findViewById(R.id.todaySteps_txt);

        // Timestamp
        long timeInMillis = System.currentTimeMillis();
        // Convert the timestamp to date
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        final String date = jdf.format(timeInMillis);
        String currentDay = date.substring(0,10);

        // TODO 11 (YOUR TURN): TODAY button
        button_today = (Button) root.findViewById(R.id.todaySteps_btn);
        button_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfSteps = stepAppOpenHelper.loadSingleRecord(getContext(), currentDay);
                Log.d("RETRIEVED STEPS TODAY: ", String.valueOf(numberOfSteps));
                completedStepsText.setText(String.valueOf(numberOfSteps));
            }
        });

        // TODO 12 (YOUR TURN): GET ENTRIES button
        button_get_all = (Button) root.findViewById(R.id.allSteps_btn);
        button_get_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepAppOpenHelper.loadRecords(getContext());

            }
        });

        // TODO 13 (YOUR TURN): button
        button_delete = (Button) root.findViewById(R.id.deleteSteps_btn);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepAppOpenHelper.deleteRecords(getContext());

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}