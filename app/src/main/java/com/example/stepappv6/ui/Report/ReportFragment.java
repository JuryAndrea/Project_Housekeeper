package com.example.stepappv6.ui.Report;

import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.stepappv6.StepAppOpenHelper;
import com.example.stepappv6.databinding.FragmentReportBinding;
import com.example.stepappv6.R;

public class ReportFragment extends Fragment {


    AnyChartView stepsInsideRoom;

    Button shareButton;

    Date cDate = new Date();
    String current_time = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

    public Map<Integer, Integer> stepsByHour = null;

    public Map<Integer, Integer> stepsInsideByHour = null;
    public Map<Integer, Integer> stepsOutsideByHour = null;

    private FragmentReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Create column chart for steps inside rooms
        stepsInsideRoom = root.findViewById(R.id.stepsInsideRoom);

        // Create column chart for steps inside rooms
        Cartesian cartesian = createColumnChart();
        stepsInsideRoom.setBackgroundColor("#FBFDF8");
        stepsInsideRoom.setChart(cartesian);


        // Create the button to share the chart
        shareButton = root.findViewById(R.id.sharebtn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a bitmap from anychart view
                Bitmap bitmapInside = generateBitmap(stepsInsideRoom);

                //Save the bitmap as an image
                saveImage(bitmapInside);

                //Share the bitmap
                share(bitmapInside);

            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void updateColumnChart(Cartesian cartesian){
        // APIlib.getInstance().setActiveAnyChartView(cartesian);
        stepsByHour = StepAppOpenHelper.loadStepsByHour(getContext(), current_time);

        // Creating a new map that contains hours of the day from 0 to 23 and
        //  number of steps during each hour set to 0
        Map<Integer, Integer> graph_map = new TreeMap<>();
        for(int i =0; i<23; i++){
            graph_map.put(i, 0);
        }

        // Replace the number of steps for each hour in graph_map
        //  with the number of steps read from the database
        graph_map.putAll(stepsByHour);
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<Integer,Integer> entry : graph_map.entrySet())
            data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));

        //cartesian.data(new SingleValueDataSet(new Integer[] { random }));
    }

    public Cartesian createColumnChart(){
        //***** Read data from SQLiteDatabase *********/
        // Get the map with hours and number of steps for today
        //  from the database and assign it to variable stepsByHour
        stepsInsideByHour = StepAppOpenHelper.loadInsideStepsByHour(getContext(), current_time);
        stepsOutsideByHour = StepAppOpenHelper.loadOutsideStepsByHour(getContext(), current_time);

        Log.d("INSIDE STEPS TODAY: ", String.valueOf(stepsInsideByHour));
        Log.d("OUTSIDE STEPS TODAY: ", String.valueOf(stepsOutsideByHour));

        // Create a new map that contains hours of the day from 0 to 23
        Map<Integer, Integer> graphMapInside = new TreeMap<>();
        Map<Integer, Integer> graphMapOutside = new TreeMap<>();
        for (int i = 0; i <= 23; i++) {
            graphMapInside.put(i, stepsInsideByHour.getOrDefault(i, 0));
            graphMapOutside.put(i, stepsOutsideByHour.getOrDefault(i, 0));
        }

        // Create and get the cartesian coordinate system for column chart
        Cartesian cartesian = AnyChart.column();

        // Prepare data entries
        List<DataEntry> dataInside = new ArrayList<>();
        List<DataEntry> dataOutside = new ArrayList<>();

        for (int i = 0; i <= 23; i++) {
            int insideSteps = graphMapInside.getOrDefault(i, 0);
            int outsideSteps = graphMapOutside.getOrDefault(i, 0);

            dataInside.add(new CustomDataEntry(String.valueOf(i), insideSteps));
            dataOutside.add(new CustomDataEntry(String.valueOf(i), outsideSteps));
        }


        // Add data to the chart and set up stacked columns
        Column insideColumn = cartesian.column(dataInside);
        insideColumn.name("Inside")
                .color("#000000");


        Column outsideColumn = cartesian.column(dataOutside);
        outsideColumn.name("Outside")
                .color("#777777");


        insideColumn.tooltip()
                .titleFormat("{%X}")
                .format("Inside: {%Value}{groupsSeparator: } Steps");
        outsideColumn.tooltip()
                .titleFormat("{%X}")
                .format("Outside: {%Value}{groupsSeparator: } Steps");

        cartesian.yAxis(0).title("Number of Steps");
        cartesian.xAxis(0).title("Hour");
        cartesian.background().fill("#00000000");
        cartesian.animation(true);

        cartesian.legend()
                .enabled(true) // Enable legend
                .fontSize(12)   // Set font size
                .padding(10, 0, 0, 0); // Set padding

        return cartesian;
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value) {
            super(x, value);
        }
    }
    public Cartesian createInsideColumnChart(){
        //***** Read data from SQLiteDatabase *********/
        // Get the map with hours and number of steps for today
        //  from the database and assign it to variable stepsByHour
        stepsByHour = StepAppOpenHelper.loadInsideStepsByHour(getContext(), current_time);

        // Creating a new map that contains hours of the day from 0 to 23 and
        //  number of steps during each hour set to 0
        Map<Integer, Integer> graph_map = new TreeMap<>();
        for(int i =0; i<= 23; i++){
            graph_map.put(i, 0);
        }

        //  Replace the number of steps for each hour in graph_map
        //  with the number of steps read from the database
        graph_map.putAll(stepsByHour);

        //***** Create column chart using AnyChart library *********/
        // Create and get the cartesian coordinate system for column chart
        Cartesian cartesian = AnyChart.column();

        //  Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<Integer,Integer> entry : graph_map.entrySet())
            data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));

        //  Add the data to column chart and get the columns
        Column column = cartesian.column(data);

        //***** Modify the UI of the chart *********/
       // Change the color of column chart and its border
        column.fill("#000000");
        column.stroke("#000000");


        // Modifying properties of tooltip
        column.tooltip()
                .titleFormat("At hour: {%X}")
                .format("{%Value} Steps")
                .anchor(Anchor.RIGHT_BOTTOM);

        // Modify column chart tooltip properties
        column.tooltip()
                .position(Position.RIGHT_TOP)
                .offsetX(0d)
                .offsetY(5);

        // Modifying properties of cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.yScale().minimum(0);


        //  Modify the UI of the cartesian
        cartesian.yAxis(0).title("Number of steps");
        cartesian.xAxis(0).title("Hour");
        cartesian.background().fill("#00000000");
        cartesian.animation(true);

        return cartesian;
    }

    public Cartesian createOutsideColumnChart(){
        //***** Read data from SQLiteDatabase *********/
        // Get the map with hours and number of steps for today
        //  from the database and assign it to variable stepsByHour
        stepsByHour = StepAppOpenHelper.loadOutsideStepsByHour(getContext(), current_time);

        // Creating a new map that contains hours of the day from 0 to 23 and
        //  number of steps during each hour set to 0
        Map<Integer, Integer> graph_map = new TreeMap<>();
        for(int i =0; i<= 23; i++){
            graph_map.put(i, 0);
        }

        //  Replace the number of steps for each hour in graph_map
        //  with the number of steps read from the database
        graph_map.putAll(stepsByHour);

        //***** Create column chart using AnyChart library *********/
        // Create and get the cartesian coordinate system for column chart
        Cartesian cartesian = AnyChart.column();

        //  Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<Integer,Integer> entry : graph_map.entrySet())
            data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));

        //  Add the data to column chart and get the columns
        Column column = cartesian.column(data);

        //***** Modify the UI of the chart *********/
        // Change the color of column chart and its border
        column.fill("#000000");
        column.stroke("#000000");


        // Modifying properties of tooltip
        column.tooltip()
                .titleFormat("At hour: {%X}")
                .format("{%Value} Steps")
                .anchor(Anchor.RIGHT_BOTTOM);

        // Modify column chart tooltip properties
        column.tooltip()
                .position(Position.RIGHT_TOP)
                .offsetX(0d)
                .offsetY(5);

        // Modifying properties of cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.yScale().minimum(0);


        //  Modify the UI of the cartesian
        cartesian.yAxis(0).title("Number of steps");
        cartesian.xAxis(0).title("Hour");
        cartesian.background().fill("#00000000");
        cartesian.animation(true);

        return cartesian;
    }

    private Bitmap generateBitmap(View view) {
        // 1. Create a bitmap with same dimensions as view
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 2. Create a canvas using bitmap
        Canvas canvas = new Canvas(bitmap);
        // 3. We need to check if view has background image.
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);}
        else {canvas.drawColor(Color.TRANSPARENT);}
        // 4. Draw the view on the canvas
        view.draw(canvas);
        Log.v("MainActivity", "Bitmap generated successfully");
        // 5. Final bitmap
        return bitmap;
    }

    private void saveImage(Bitmap bitmap) {

        // 1. Create Destination folder in external storage.
        // This will require EXTERNAL STORAGE permission
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File imgDir = new File(externalStorageDirectory.getAbsolutePath());

        // 2. Generate a random file name for image
        String imageName = cDate + ".jpeg";
        File localFile = new File(imgDir, imageName);

        String path = "file://" + externalStorageDirectory.getAbsolutePath();


        // 3. Save the image
        try {
            FileOutputStream fos = new FileOutputStream(localFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE",
                    Uri.fromFile(new File(path))));

            Log.v("ReportFragment", "File saved successfully in: " + path);

        } catch (Exception e)  {
            e.printStackTrace();

            Log.v("ReportFragment", "File saving failed");

        }
    }

    private void share(Bitmap bitmap) {
        try {

            Uri shareUri = getImageUri(requireContext(), bitmap);


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, shareUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(intent, "Share image to ..."));
        }
        catch (Exception e) {

            Toast.makeText(getContext(), "No implicit intent to handle this action." + e, Toast.LENGTH_LONG).show();
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                "Title", null);
        return Uri.parse(path);
    }


}