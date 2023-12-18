package com.example.stepappv6.ui.Maintenance;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stepappv6.R;
import com.example.stepappv6.databinding.FragmentCameraBinding;
import com.google.android.material.textfield.TextInputLayout;


public class CameraFragment extends Fragment {

    private static final int REQUEST_CODE = 22;

    Button btnpicture;
    Button btnsend;
    ImageView imageView;

    EditText description;

    FragmentCameraBinding binding;

    TextInputLayout roomInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCameraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnpicture = root.findViewById(R.id.capture);
        imageView = root.findViewById(R.id.imageview);
        btnsend = root.findViewById(R.id.send);

        description = root.findViewById(R.id.description);

        roomInputLayout = root.findViewById(R.id.select_room);

        String[] rooms = new String[]{"1", "2", "3", "5", "6"};
        String[] objects = new String[]{"Bed", "TV", "Curtains", "Lamp", "Night Stands"};
        String[] priority = new String[]{"High", "Medium", "Low"};

        ArrayAdapter<String> arrayAdapter_rooms = new ArrayAdapter<>(getContext(), R.layout.dropdown_room, rooms);
        ArrayAdapter<String> arrayAdapter_objects = new ArrayAdapter<>(getContext(), R.layout.dropdown_room, objects);
        ArrayAdapter<String> arrayAdapter_priority = new ArrayAdapter<>(getContext(), R.layout.dropdown_room, priority);

        AutoCompleteTextView autoCompleteTextView = root.findViewById(R.id.filled);
        autoCompleteTextView.setAdapter(arrayAdapter_rooms);

        AutoCompleteTextView autoCompleteTextView2 = root.findViewById(R.id.filled_2);
        autoCompleteTextView2.setAdapter(arrayAdapter_objects);


        AutoCompleteTextView autoCompleteTextView3 = root.findViewById(R.id.filled_3);
        autoCompleteTextView3.setAdapter(arrayAdapter_priority);


        btnpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Clear the image view
                imageView.setImageResource(android.R.color.transparent);

                // Clear the description
                description.setText("");
                description.setHint(R.string.description);
                description.setHintTextColor(getResources().getColor(R.color.md_theme_light_outline));

                // Clear text in AutoCompleteTextView
                autoCompleteTextView.getText().clear();
                autoCompleteTextView.setHint(R.string.room);
                autoCompleteTextView.setHintTextColor(getResources().getColor(R.color.md_theme_light_outline));

                autoCompleteTextView2.getText().clear();
                autoCompleteTextView2.setHint(R.string.object);
                autoCompleteTextView2.setHintTextColor(getResources().getColor(R.color.md_theme_light_outline));

                autoCompleteTextView3.getText().clear();
                autoCompleteTextView3.setHint(R.string.priority);
                autoCompleteTextView3.setHintTextColor(getResources().getColor(R.color.md_theme_light_outline));

            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        } else {
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

}