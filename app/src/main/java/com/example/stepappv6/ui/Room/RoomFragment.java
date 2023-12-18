package com.example.stepappv6.ui.Room;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import com.example.stepappv6.R;
import com.example.stepappv6.databinding.FragmentRoomBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import raspitransfer.dataretriever;


public class RoomFragment extends Fragment {


    FragmentRoomBinding binding;

    private int currentRoom;
    public static Map<Integer, Integer> roomstatus = new HashMap<>();
    private ImageView status_image1;
    private ImageView status_image2;
    private ImageView status_image3;
    private ImageView status_image4;
    private ImageView status_image5;
    private ImageView status_image6;
    private ArrayList<AutoCompleteTextView> AutoCompleteTextViewArray;

    private ArrayList<ImageView> ImageArray;

    String[] cleaningStatus;

    ArrayAdapter<String> arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        roomstatus.put(1, 0);
//        roomstatus.put(2, 1);
//        roomstatus.put(3, 2);
//        roomstatus.put(4, 0);
//        roomstatus.put(5, 0);
//        roomstatus.put(6, 1);


        dataretriever obj = new dataretriever(6);
        Log.d("SSH", "1");
        String occdata = obj.retrieve(getContext());
        Log.d("SSH", "2");
        roomstatus = obj.parseJsonString(occdata);


        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageArray = new ArrayList<>();

        status_image1 = root.findViewById(R.id.status_room1);
        ImageArray.add(status_image1);
        status_image2 = root.findViewById(R.id.status_room2);
        ImageArray.add(status_image2);
        status_image3 = root.findViewById(R.id.status_room3);
        ImageArray.add(status_image3);
        status_image4 = root.findViewById(R.id.status_room4);
        ImageArray.add(status_image4);
        status_image5 = root.findViewById(R.id.status_room5);
        ImageArray.add(status_image5);
        status_image6 = root.findViewById(R.id.status_room6);
        ImageArray.add(status_image6);


        // dirty = 0, cleaning = 1, cleaned = 2, ready = 3
        cleaningStatus = new String[]{"Dirty", "Cleaning", "Cleaned", "Ready"};
        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);

        AutoCompleteTextViewArray = new ArrayList<>();

        AutoCompleteTextView autoCompleteTextView = root.findViewById(R.id.filled);
        autoCompleteTextView.setAdapter(arrayAdapter);
        AutoCompleteTextViewArray.add(autoCompleteTextView);

        AutoCompleteTextView autoCompleteTextView2 = root.findViewById(R.id.filled2);
        autoCompleteTextView2.setAdapter(arrayAdapter);
        AutoCompleteTextViewArray.add(autoCompleteTextView2);

        AutoCompleteTextView autoCompleteTextView3 = root.findViewById(R.id.filled3);
        autoCompleteTextView3.setAdapter(arrayAdapter);
        AutoCompleteTextViewArray.add(autoCompleteTextView3);

        AutoCompleteTextView autoCompleteTextView4 = root.findViewById(R.id.filled4);
        autoCompleteTextView4.setAdapter(arrayAdapter);
        AutoCompleteTextViewArray.add(autoCompleteTextView4);

        AutoCompleteTextView autoCompleteTextView5 = root.findViewById(R.id.filled5);
        autoCompleteTextView5.setAdapter(arrayAdapter);
        AutoCompleteTextViewArray.add(autoCompleteTextView5);

        AutoCompleteTextView autoCompleteTextView6 = root.findViewById(R.id.filled6);
        autoCompleteTextView5.setAdapter(arrayAdapter);
        AutoCompleteTextViewArray.add(autoCompleteTextView6);

        setOnItemClickListener(autoCompleteTextView, status_image1);
        setOnItemClickListener(autoCompleteTextView2, status_image2);
        setOnItemClickListener(autoCompleteTextView3, status_image3);
        setOnItemClickListener(autoCompleteTextView4, status_image4);
        setOnItemClickListener(autoCompleteTextView5, status_image5);
        setOnItemClickListener(autoCompleteTextView6, status_image6);

        // Iterate over roomstatus map to get room we are in
        // get the AutoCompleteTextView of the current room
        // assuming 0=dirty (default), 1=cleaning, 2=cleaned
        for (Integer key : roomstatus.keySet()) {
            if (roomstatus.get(key) == 0) { //Dirty
                currentRoom = key;
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[0];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(0, image);

            } else if (roomstatus.get(key) == 1) { //Cleaning
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[1];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(1, image);

            } else if (roomstatus.get(key) == 2) { //Cleaned
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[2];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(2, image);

            } else if (roomstatus.get(key) == 3) { //Ready
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[3];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(3, image);

            }

        }

        return root;
    }

    private int choose_status(int pos){
        if (pos == 0)
            return 0;
        else if (pos == 1)
            return 1;
        else if (pos == 2)
            return 2;
        else
            return 3;
    }

    private void set_image(int pos, ImageView status_image){

        if (pos == 0)
            status_image.setImageResource(R.drawable.dirty_50);
        else if (pos == 1)
            status_image.setImageResource(R.drawable.cleaning_64);
        else if (pos == 2)
            status_image.setImageResource(R.drawable.cleaned_64);
        else
            status_image.setImageResource(R.drawable.ready_50);

    }

    private void set_image_by_hand(int pos, ImageView status_image){
        if (pos == 0)
            status_image.setImageResource(R.drawable.dirty_50);
        else if (pos == 1)
            status_image.setImageResource(R.drawable.cleaning_64);
        else if (pos == 2)
            status_image.setImageResource(R.drawable.cleaned_64);
        else
            status_image.setImageResource(R.drawable.ready_50);
    }


    private void setOnItemClickListener(AutoCompleteTextView autoCompleteTextView, ImageView status_image){
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = choose_status(i);
                set_image_by_hand(pos, status_image);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        dataretriever obj = new dataretriever(6);
        Log.d("SSH", "1");
        String occdata = obj.retrieve(getContext());
        Log.d("SSH", "2");
        roomstatus = obj.parseJsonString(occdata);

        for (Integer key : roomstatus.keySet()) {
            if (roomstatus.get(key) == 0) { //Dirty
                currentRoom = key;
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[0];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(0, image);

            } else if (roomstatus.get(key) == 1) { //Cleaning
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[1];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(1, image);

            } else if (roomstatus.get(key) == 2) { //Cleaned
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[2];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(2, image);

            } else if (roomstatus.get(key) == 3) { //Ready
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key - 1);
                ImageView image = ImageArray.get(key - 1);

                String selectedText = cleaningStatus[3];
                a.setText(selectedText);
                arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);
                a.setAdapter(arrayAdapter);

                set_image(3, image);

            }

        }

    }
}