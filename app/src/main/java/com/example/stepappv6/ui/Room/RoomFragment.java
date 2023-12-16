package com.example.stepappv6.ui.Room;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stepappv6.LoginActivity;
import com.example.stepappv6.R;
import com.example.stepappv6.databinding.FragmentCameraBinding;
import com.example.stepappv6.databinding.FragmentRoomBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import raspitransfer.dataretriever;


public class RoomFragment extends Fragment {


    FragmentRoomBinding binding;

    private Button status;
    private int currentRoom;
    private Map<Integer, Integer> roomstatus;
    private ImageView status_image1;
    private ImageView status_image2;
    private ImageView status_image3;
    private ImageView status_image4;
    private ImageView status_image5;
    private ImageView status_image6;
//    private Button logout;
    private ArrayList<AutoCompleteTextView> AutoCompleteTextViewArray;

    private ArrayList<ImageView> ImageArray;

    String[] cleaningStatus;

    ArrayAdapter<String> arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), autoCompleteTextView.getText().toString() + " pos " + i, Toast.LENGTH_SHORT).show();
                int pos = choose_status(i);
                set_image(pos, status_image1);
            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), autoCompleteTextView2.getText().toString(), Toast.LENGTH_SHORT).show();
                int pos = choose_status(i);
                set_image(pos, status_image2);
            }
        });

        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), autoCompleteTextView3.getText().toString(), Toast.LENGTH_SHORT).show();
                int pos = choose_status(i);
                set_image(pos, status_image3);
            }
        });

        autoCompleteTextView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), autoCompleteTextView4.getText().toString(), Toast.LENGTH_SHORT).show();
                int pos = choose_status(i);
                set_image(pos, status_image4);
            }
        });

        autoCompleteTextView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), autoCompleteTextView5.getText().toString(), Toast.LENGTH_SHORT).show();
                int pos = choose_status(i);
                set_image(pos, status_image5);
            }
        });

        autoCompleteTextView6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), autoCompleteTextView5.getText().toString(), Toast.LENGTH_SHORT).show();
                int pos = choose_status(i);
                set_image(pos, status_image6);
            }
        });


//      HERE WE GET THE ROOM STATUS UPDATE
//        dataretriever obj = new dataretriever(6);
//        Log.d("SSH", "1");
//        String occdata = obj.retrieve(getContext());
//        Log.d("SSH", "2");
//        Map<Integer, Integer> roomstatus = obj.parseJsonString(occdata);
//        Log.d("SSH", roomStatus.toString());
        roomstatus = new HashMap<>();
        roomstatus.put(1, 0);
        roomstatus.put(2, 1);
        roomstatus.put(3, 2);
        roomstatus.put(4, 0);
        roomstatus.put(5, 0);
        roomstatus.put(6, 0);

        // Iterate over roomstatus map to get room we are in
        // get the AutoCompleteTextView of the current room
        // assuming 0=dirty (default), 1=cleaning, 2=cleaned
        for (Integer key : roomstatus.keySet()) {
            if(roomstatus.get(key) == 1) {
                currentRoom = key;
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(currentRoom-1);
                ImageView image = ImageArray.get(currentRoom-1);
                a.setText(arrayAdapter.getItem(1));
                set_image(1, image);
            } else if (roomstatus.get(key) == 2) {
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key-1);
                ImageView image = ImageArray.get(key-1);
                a.setText(arrayAdapter.getItem(2));
                set_image(2, image);
            } else if (roomstatus.get(key) == 0) {
                AutoCompleteTextView a = AutoCompleteTextViewArray.get(key-1);
                ImageView image = ImageArray.get(key-1);
                a.setText(arrayAdapter.getItem(0));
                set_image(0, image);
            } else {
                continue;
            }
        }

//        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, cleaningStatus);

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
//        if (pos == 0)
//            status_image.setImageResource(R.drawable.dirty_50);
//        else if (pos == 1)
//            status_image.setImageResource(R.drawable.cleaning_64);
//        else if (pos == 2)
//            status_image.setImageResource(R.drawable.cleaned_64);
//        else
//            status_image.setImageResource(R.drawable.ready_50);

        if(pos == 1) {
            status_image.setImageResource(R.drawable.cleaning_64);
        } else if (pos == 2) {
            status_image.setImageResource(R.drawable.cleaned_64);
        } else {
            status_image.setImageResource(R.drawable.dirty_50);
        }
    }


}