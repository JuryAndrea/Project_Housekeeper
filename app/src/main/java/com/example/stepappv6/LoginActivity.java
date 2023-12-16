package com.example.stepappv6;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    private Button skipButton;

    private TextView text;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkBoxLogin;
    private String email;
    private String password;

    private JSONObject jsonObject;

    private SharedPreferences preference;

    private SharedPreferences.Editor editor;

    private NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skipButton = findViewById(R.id.skip);
        text = findViewById(R.id.text_msg);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
//            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_SHORT).show();
//            finish();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }

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

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        Log.d("JURY", "Intent Action: " + action);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String tagId = byteArrayToHexString(tag.getId());
            Log.d("JURY", "Tag: " + tagId);

            if (tagId.equals("1480275E")){
                Log.d("JURY", "pluto");
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                //TODO: add SharedPreferences to save the login status
            }

//            if (tag != null) {
//                Log.d("JURY", "Tag ID: " + Arrays.toString(tag.getId()));
//                NdefMessage ndefMessage = readNdefMessage(tag);
//                Log.d("JURY", "NDEF Message: " + ndefMessage);
//                if (ndefMessage != null) {
//                    String tagData = new String(ndefMessage.getRecords()[0].getPayload());
//                    text.setText("JURY " + tagData);
//                } else {
//                    Log.e("JURY", "Failed to read NDEF message from tag.");
//                }
//            }
        }
    }

    private String byteArrayToHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

//    private NdefMessage readNdefMessage(Tag tag) {
//        Ndef ndef = Ndef.get(tag);
//        if (ndef != null) {
//            try {
//                ndef.connect();
//                return ndef.getNdefMessage();
//            } catch (IOException e) {
//                Log.e("JURY", "Error reading NDEF message", e);
//            } catch (FormatException e) {
//                Log.e("JURY", "Error formatting NDEF message", e);
//                throw new RuntimeException(e);
//            } finally {
//                try {
//                    ndef.close();
//                } catch (IOException e) {
//                    Log.e("JURY", "Error closing NDEF connection", e);
//                }
//            }
//        }
//        return null;
//    }

    private void enableNfcForegroundDispatch() {
        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        IntentFilter[] intentFilters = new IntentFilter[]{
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
                new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
                new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        };

        String[][] techLists = new String[][]{
                new String[]{Ndef.class.getName()},
                new String[]{NdefFormatable.class.getName()}
        };

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists);
    }

    private void disableNfcForegroundDispatch() {
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

}