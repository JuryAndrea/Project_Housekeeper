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
//    private Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: make the list scrollable

        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), autoCompleteTextView2.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), autoCompleteTextView3.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), autoCompleteTextView4.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), autoCompleteTextView5.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}