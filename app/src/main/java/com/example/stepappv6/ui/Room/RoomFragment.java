package com.example.stepappv6.ui.Room;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.stepappv6.LoginActivity;
import com.example.stepappv6.R;
import com.example.stepappv6.databinding.FragmentCameraBinding;
import com.example.stepappv6.databinding.FragmentRoomBinding;


public class RoomFragment extends Fragment {


    FragmentRoomBinding binding;

    private Button status;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: make the list scrollable

        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        status = root.findViewById(R.id.Status);
        logout = root.findViewById(R.id.logout);

        // Setting onClick behavior to the button
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(getContext(), status);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(getContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}