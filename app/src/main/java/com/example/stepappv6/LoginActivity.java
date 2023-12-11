package com.example.stepappv6;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private Button skipButton;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkBoxLogin;
    private String email;
    private String password;

    private JSONObject jsonObject;

    private SharedPreferences preference;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skipButton = findViewById(R.id.skip);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        //TODO: NFC reader and SharedPreferences

        /*
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.header_logo, null);
        getSupportActionBar().setCustomView(view);


        buttonLogin = findViewById(R.id.login);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        checkBoxLogin = findViewById(R.id.checkBoxLogin);


        preference = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preference.edit();

        if (preference.contains("checked") && preference.getBoolean("checked", false) == true) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            checkBoxLogin.setChecked(false);
        }

        jsonObject = JsonFileReader.readJsonFromAssets(this, "credentials.json");
        if (jsonObject != null) {
            try {
                // Access JSON data here
                email = jsonObject.getString("mail");
                password = jsonObject.getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmail.getText().toString().equals(email) && editTextPassword.getText().toString().equals(password)) {
                    //https://stackoverflow.com/questions/38975076/how-to-apply-shared-preference-to-checkbox
                    if (checkBoxLogin.isChecked()) {

                        editor.putBoolean("checked", true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        editor.putBoolean("checked", false);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                }


            }
        });
        */

    }

}