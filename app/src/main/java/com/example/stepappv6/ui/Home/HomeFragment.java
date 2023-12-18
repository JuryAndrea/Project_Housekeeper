package com.example.stepappv6.ui.Home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.stepappv6.MainActivity;
import com.example.stepappv6.R;
import com.example.stepappv6.StepAppOpenHelper;
import com.example.stepappv6.ui.Room.RoomFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.example.stepappv6.ui.Home.HomeFragment.mNotifyManager;


public class HomeFragment extends Fragment {

    // Text view and Progress Bar variables
    public TextView stepsCountTextView;
    public TextView stepsInsideRoomTextView;
    public TextView stepsOutsideRoomTextView;
    public ProgressBar stepsCountProgressBar;
    public TextView goalTextView;

    // ACC sensors.
    private Sensor mSensorACC;
    private SensorManager mSensorManager;
    private SensorEventListener listener;

    // Step Detector sensor
    private Sensor mSensorStepDetector;

    // Num of steps completed
    static int totalStepsCompleted = 0;
    static int stepsInsideRoom = 0;
    static int stepsOutsideRoom = 0;
    static int stepsGoal = 10000;

    //Create a constant for the notification channel ID
    public String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // Create a variable to store the NotificationManager object
    public static NotificationManager mNotifyManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Get the number of steps stored in the current date
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        totalStepsCompleted = StepAppOpenHelper.loadSingleRecord(getContext(), fDate);
        stepsInsideRoom = StepAppOpenHelper.loadSingleRecordOfStepsInside(getContext(), fDate);
        stepsOutsideRoom = StepAppOpenHelper.loadSingleRecordOfStepsOutside(getContext(), fDate);

        RoomFragment.roomstatus.put(6, 1);

        // Text view & ProgressBar
        goalTextView = (TextView) root.findViewById(R.id.goal);
        stepsCountTextView = (TextView) root.findViewById(R.id.counter);
        stepsCountProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        stepsInsideRoomTextView = (TextView) root.findViewById(R.id.steps_inside_room);
        stepsOutsideRoomTextView = (TextView) root.findViewById(R.id.steps_outside_room);
        stepsCountProgressBar.setMax(stepsGoal);

        // Set the Views with the number of stored steps
        stepsCountTextView.setText(String.valueOf(totalStepsCompleted));
        stepsInsideRoomTextView.setText(String.valueOf(stepsInsideRoom));
        stepsOutsideRoomTextView.setText(String.valueOf(stepsOutsideRoom));
        stepsCountProgressBar.setProgress(totalStepsCompleted);


        //  Get an instance of the sensor manager.
        mSensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorACC = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Step detector instance
        mSensorStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // Get an instance of the database
        StepAppOpenHelper databaseOpenHelper = new StepAppOpenHelper(this.getContext());
        SQLiteDatabase database = databaseOpenHelper.getWritableDatabase();

        // Get the Builder object
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Instantiate the StepCounterListener
        listener = new StepCounterListener(notifyBuilder, database, stepsCountTextView, stepsInsideRoomTextView, stepsOutsideRoomTextView, stepsCountProgressBar);

        boolean userInRoom = true; //

        if(userInRoom) {
            //Place code related to Start button
            Toast.makeText(getContext(), "START", Toast.LENGTH_SHORT).show();

            // Check if the Accelerometer sensor exists
            if (mSensorACC != null) {

                // Register the ACC listener
                mSensorManager.registerListener(listener, mSensorACC, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(getContext(), R.string.acc_sensor_not_available, Toast.LENGTH_SHORT).show();

            }

            // Check if the Step detector sensor exists
            if (mSensorStepDetector != null) {
                // Register the ACC listener
                mSensorManager.registerListener(listener, mSensorStepDetector, SensorManager.SENSOR_DELAY_NORMAL);

            } else {
                Toast.makeText(getContext(), R.string.step_detector_sensor_not_available, Toast.LENGTH_SHORT).show();

            }
        }


        // Create notification channel by Calling createNotificationChannel()
        createNotificationChannel();
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSensorManager.unregisterListener(listener); // unregister the listener
    }


    //Create a createNotificationChannel() method and instantiate the NotificationManager inside the method.
    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            //Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Steps Notification",
                    NotificationManager.IMPORTANCE_HIGH);

            //Set the characteristics of the notification channel
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from StepApp");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    // Helper method
    public NotificationCompat.Builder getNotificationBuilder() {
        // Explicit Intent
        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(getContext(),
                    StepCounterListener.NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);

            // Create and instantiate the notification builder.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getContext(), PRIMARY_CHANNEL_ID)
                // Set the text and the icon
                .setContentTitle("Congratulations!")
                .setContentText("You reached your goal of " + String.valueOf(stepsGoal) + " steps!")
                .setSmallIcon(R.drawable.baseline_celebration_24)

                // Set the intent
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                // Add priority and defaults to your notification for backward compatibility
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);


        return notifyBuilder;
    }

}


// Sensor event listener
class StepCounterListener<totalStepsCompleted> implements SensorEventListener {

    // Constant for the notification ID:
    public static int NOTIFICATION_ID =0 ;

    //Get the number of stored steps for the current day
    public int mACCStepCounter = HomeFragment.totalStepsCompleted;
    public int mACCStepsInsideCounter = HomeFragment.stepsInsideRoom;
    public int mACCStepsOutsideCounter = HomeFragment.stepsOutsideRoom;



    ArrayList<Integer> mACCSeries = new ArrayList<Integer>();
    ArrayList<String> mTimeSeries = new ArrayList<String>();

    private int lastXPoint = 1;
    int stepThreshold = 10;

    // Android step detector
    public int mAndroidStepCounter = 0;

    // TextView and Progress Bar
    TextView stepsCountTextView;

    TextView stepsInsideRoomTextView;
    TextView stepsOutsideRoomTextView;


    ProgressBar stepsCountProgressBar;

    // SQLite Database
    SQLiteDatabase database;

    public String timestamp;
    public String day;
    public String hour;

    //NotificationBuilder
    NotificationCompat.Builder notificationBuilder;

    // Get the notification builder, database, TextView and ProgressBar as args
    public StepCounterListener(NotificationCompat.Builder nb, SQLiteDatabase db, TextView tv, TextView sitv, TextView sotv, ProgressBar pb){
        stepsCountTextView = tv;
        stepsInsideRoomTextView = sitv;
        stepsOutsideRoomTextView = sotv;
        stepsCountProgressBar = pb;
        database = db;
        notificationBuilder = nb;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {

            // Case of the ACC
            case Sensor.TYPE_LINEAR_ACCELERATION:

                // Get x,y,z
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // Timestamp
                long timeInMillis = System.currentTimeMillis() + (event.timestamp - SystemClock.elapsedRealtimeNanos()) / 1000000;

                // Convert the timestamp to date
                SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                jdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                String date = jdf.format(timeInMillis);


                // Get the date, the day and the hour
                timestamp = date;
                day = date.substring(0,10);
                hour = date.substring(11,13);

                /// STEP COUNTER ACC ////
                double accMag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

                //Update the Magnitude series
                mACCSeries.add((int) accMag);
                //Update the time series
                mTimeSeries.add(timestamp);

                // Calculate ACC peaks and steps
                peakDetection();

                break;

            // case Step detector
            case Sensor.TYPE_STEP_DETECTOR:

                // Calculate the number of steps
                countSteps(event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }


    public void peakDetection() {
        int windowSize = 20;

        /* Peak detection algorithm derived from: A Step Counter Service for Java-Enabled Devices Using a Built-In Accelerometer, Mladenov et al.
         */
        int highestValX = mACCSeries.size(); // get the length of the series
        if (highestValX - lastXPoint < windowSize) { // if the segment is smaller than the processing window skip it
            return;
        }

        List<Integer> valuesInWindow = mACCSeries.subList(lastXPoint,highestValX);
        List<String> timesInWindow = mTimeSeries.subList(lastXPoint,highestValX);

        lastXPoint = highestValX;

        int forwardSlope = 0;
        int downwardSlope = 0;

        List<Integer> dataPointList = new ArrayList<Integer>();
        List<String> timePointList = new ArrayList<String>();


        for (int p =0; p < valuesInWindow.size(); p++){
            dataPointList.add(valuesInWindow.get(p)); // ACC Magnitude data points
            timePointList.add(timesInWindow.get(p)); // Timestamps
        }

        for (int i = 0; i < dataPointList.size(); i++) {
            if (i == 0) {
            }
            else if (i < dataPointList.size() - 1) {
                forwardSlope = dataPointList.get(i + 1) - dataPointList.get(i);
                downwardSlope = dataPointList.get(i)- dataPointList.get(i - 1);

                if (forwardSlope < 0 && downwardSlope > 0 && dataPointList.get(i) > stepThreshold ) {

                    // Update the number of steps
                    mACCStepCounter += 1;
                    if(RoomFragment.roomstatus.get(6) == 1){
                        mACCStepsInsideCounter += 1;
                    }
                    else{
                        mACCStepsOutsideCounter += 1;
                    }


                    if (mACCStepCounter == HomeFragment.stepsGoal) {
                        mNotifyManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                    }


                    // Update the TextView and the ProgressBar
                    stepsCountTextView.setText(String.valueOf(mACCStepCounter));
                    stepsCountProgressBar.setProgress(mACCStepCounter);
                    stepsInsideRoomTextView.setText(String.valueOf(mACCStepsInsideCounter));
                    stepsOutsideRoomTextView.setText(String.valueOf(mACCStepsOutsideCounter));

                    // Insert the data in the database
                    ContentValues values = new ContentValues();
                    values.put(StepAppOpenHelper.KEY_TIMESTAMP, timePointList.get(i));
                    values.put(StepAppOpenHelper.KEY_DAY, day);
                    values.put(StepAppOpenHelper.KEY_HOUR, hour);
                    if(RoomFragment.roomstatus.get(6) == 1){
                        values.put(StepAppOpenHelper.KEY_INSIDE, 1);
                        Log.d("StepCounterListener", "Step recorded as inside");
                    } else {
                        values.put(StepAppOpenHelper.KEY_INSIDE, 0);
                        Log.d("StepCounterListener", "Step recorded as outside");
                    }

                    database.insert(StepAppOpenHelper.TABLE_NAME, null, values);
                }

            }
        }
    }


    // Calculate the number of steps from the step detector
    private void countSteps(float step) {

        //Step count
        mAndroidStepCounter += (int) step;
        Log.d("NUM STEPS ANDROID", "Num.steps: " + String.valueOf(mAndroidStepCounter));
    }

}

