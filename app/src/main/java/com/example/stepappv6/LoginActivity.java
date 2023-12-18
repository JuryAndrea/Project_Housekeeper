package com.example.stepappv6;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




public class LoginActivity extends AppCompatActivity {

    private Button skipButton;

    private TextView text;

    private SharedPreferences preference;

    private SharedPreferences.Editor editor;

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skipButton = findViewById(R.id.skip);
        text = findViewById(R.id.text_msg);

        preference = getSharedPreferences("Login", MODE_PRIVATE);
        editor = preference.edit();

        //The key "logged" exists in the SharedPreferences (preference.contains("checked") is true), and
        //The boolean value associated with the key "checked" is true (preference.getBoolean("checked", false)).
        if (preference.contains("logged") && preference.getBoolean("logged", false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Set click listener for the skipButton to handle user's decision to skip login
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mark the user as logged in by updating SharedPreferences
                editor.putBoolean("logged", true);
                editor.apply();
                // Create an Intent to navigate to the main activity
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Check if NFC is available on device
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
        // Check if NFC is enabled on device
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is not enabled. Please enable NFC in device settings.", Toast.LENGTH_SHORT).show();
        }

        handleIntent(getIntent());
    }


    @Override
    protected void onResume() {
        super.onResume();
        enableNfcForegroundDispatch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableNfcForegroundDispatch();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    // Handle NFC intent
    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        Log.d("JURY", "Intent Action: " + action);
        // Check if intent is NFC intent
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String tagId = byteArrayToHexString(tag.getId());
            Log.d("JURY", "Tag: " + tagId);

            // Check if tag ID matches the ID of the tag that is allowed to log in
            if (tagId.equals("1480275E")){
                Log.d("JURY", "inside the if statement");

                editor.putBoolean("logged", true);
                editor.apply();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }else
                Toast.makeText(this, "Not Authorized", Toast.LENGTH_SHORT).show();
        }
    }

    // Convert byte array to hex string
    private String byteArrayToHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Enable NFC foreground dispatch to detect NFC intents
    private void enableNfcForegroundDispatch() {
        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // Create a PendingIntent to handle NFC foreground dispatch
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Create an IntentFilter array to detect NFC tags
        IntentFilter[] intentFilters = new IntentFilter[]{
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
                new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
                new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        };

        // Create a String array to specify the tech list
        String[][] techLists = new String[][]{
                new String[]{Ndef.class.getName()},
                new String[]{NdefFormatable.class.getName()}
        };

        // Enable NFC foreground dispatch
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists);
    }

    // Disable NFC foreground dispatch
    private void disableNfcForegroundDispatch() {
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

}