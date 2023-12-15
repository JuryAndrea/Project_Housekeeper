package com.example.stepappv6.ui.Room;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


public class RoomFragment extends Fragment {


    FragmentRoomBinding binding;

    private Button status;
    private ImageView status_image1;
    private ImageView status_image2;
    private ImageView status_image3;
    private ImageView status_image4;
    private ImageView status_image5;
    private ImageView status_image6;
//    private Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: make the list scrollable

        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        status_image1 = root.findViewById(R.id.status_room1);
        status_image2 = root.findViewById(R.id.status_room2);
        status_image3 = root.findViewById(R.id.status_room3);
        status_image4 = root.findViewById(R.id.status_room4);
        status_image5 = root.findViewById(R.id.status_room5);
        status_image6 = root.findViewById(R.id.status_room6);

        // dirty = 0, cleaning = 1, cleaned = 2, ready = 3
        String[] status = new String[]{"Dirty", "Cleaning", "Cleaned", "Ready"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, status);

        AutoCompleteTextView autoCompleteTextView = root.findViewById(R.id.filled);
        autoCompleteTextView.setAdapter(arrayAdapter);

        AutoCompleteTextView autoCompleteTextView2 = root.findViewById(R.id.filled2);
        autoCompleteTextView2.setAdapter(arrayAdapter);

        AutoCompleteTextView autoCompleteTextView3 = root.findViewById(R.id.filled3);
        autoCompleteTextView3.setAdapter(arrayAdapter);

        AutoCompleteTextView autoCompleteTextView4 = root.findViewById(R.id.filled4);
        autoCompleteTextView4.setAdapter(arrayAdapter);

        AutoCompleteTextView autoCompleteTextView5 = root.findViewById(R.id.filled5);
        autoCompleteTextView5.setAdapter(arrayAdapter);

        AutoCompleteTextView autoCompleteTextView6 = root.findViewById(R.id.filled6);
        autoCompleteTextView5.setAdapter(arrayAdapter);

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


}