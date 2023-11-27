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

//        status = root.findViewById(R.id.Status);
//        logout = root.findViewById(R.id.logout);




        // Setting onClick behavior to the button
//        status.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initializing the popup menu and giving the reference as current context
//                PopupMenu popupMenu = new PopupMenu(getContext(), status);
//
//                // Inflating popup menu from popup_menu.xml file
//                popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        // Toast message on menu item clicked
//                        Toast.makeText(getContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                });
//                // Showing the popup menu
//                popupMenu.show();
//            }
//        });


//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });


        return root;
    }
}