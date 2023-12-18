package com.example.stepappv6.ui.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.stepappv6.R;
import com.example.stepappv6.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import raspitransfer.dataretriever;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private TextView textViewGoal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textViewGoal = root.findViewById(R.id.textView_goal);

        // Set up the pie chart
        AnyChartView anyChartView = root.findViewById(R.id.anyChartView);
        com.anychart.AnyChart.pie();
        com.anychart.charts.Pie pie = com.anychart.AnyChart.pie();

        // Customize chart appearance
        pie.palette(new String[]{"#8B0000", "#1E90FF", "#4682B4", "#4169E1"}); // Set custom colors
        pie.title("Check your progress of today"); // Set chart title
        pie.labels().position("outside"); // Position labels outside the pie chart
        pie.legend().position("top").align(Align.CENTER).itemsLayout(LegendLayout.HORIZONTAL); // Adjust legend position
        pie.legend().title().enabled(true);
        pie.legend().title().text("Legend");
        pie.legend().title()
                .padding(0d, 0d, 20d, 0d)
                .background()
                .fill("#fefefe")
                .corners("0 0 7 7")
                .enabled(true)
                .stroke();

        // Retrieve data from the SSH connection
        dataretriever obj = new dataretriever(6);
        Log.d("SSH", "1");
        String occdata = obj.retrieve(getContext());
        Log.d("SSH", "2");
        Map<Integer, Integer> roomstatuses = obj.parseJsonString(occdata);

        // Count the number of rooms in each state
        int dirty = 0;
        int cleaning = 0;
        int cleaned = 0;
        int ready = 0;

        // Loop through the map and count the number of rooms in each state
        for (Integer key : roomstatuses.keySet()) {
            int value = roomstatuses.get(key);

            if (value == 0) {
                dirty += 1;
            } else if (value == 1) {
                cleaning +=1;
            } else if (value == 2) {
                cleaned +=1;
            }
            else if (value == 3) {
                ready +=1;
            }


        }

        Log.d("SSH", String.valueOf(dirty));
        Log.d("SSH", String.valueOf(cleaning));
        Log.d("SSH", String.valueOf(cleaned));
        Log.d("SSH", String.valueOf(ready));


        // Sample data for the pie chart
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Dirty", dirty));
        data.add(new ValueDataEntry("Cleaning", cleaning));
        data.add(new ValueDataEntry("Cleaned", cleaned));
        data.add(new ValueDataEntry("Ready", ready));

        pie.data(data);
        anyChartView.setChart(pie);

        // Calculate the percentage of rooms that are cleaned or ready
        double totalRooms = dirty + cleaning + cleaned + ready;
        double progress = (1 - ((dirty + cleaning) / totalRooms)) * 100;
        textViewGoal.setText("You have completed " + (int)progress + "% already today! Keep it up!");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
