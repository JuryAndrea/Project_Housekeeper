package raspitransfer;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class dataretriever extends AppCompatActivity {

    int roomnumber;
    Map<Integer, Integer> occstatus;

    public dataretriever(int roomnumber) {
        this.roomnumber = roomnumber;
        this.occstatus = null;
    }


    public Map<Integer, Integer> parseJsonString(String jsonString) {
        // Remove curly braces and split the string by commas
        String[] keyValuePairs = jsonString
                .replace("{", "")
                .replace("}", "")
                .split(",\\s*");

        // Create a Map to store key-value pairs
        Map<Integer, Integer> dictionary = new HashMap<>();

        // Parse each key-value pair and add it to the dictionary
        for (String pair : keyValuePairs) {
            String[] parts = pair.split(":");
            int roomNumber = Integer.parseInt(parts[0].trim().replaceAll("\\D", ""));
            int value = Integer.parseInt(parts[1].trim());
            dictionary.put(roomNumber, value);
        }
        this.occstatus = dictionary;
        return dictionary;
    }



    public String retrieve(Context context) {
        String remoteFilePath = "roomstatus.json";
        File externalStorageDir = context.getExternalFilesDir(null);
        Log.d("SSH", externalStorageDir.toString());

        String destinationPath = externalStorageDir.toString();

        connetionargs connect = new connetionargs("christianaltrichter", "172.20.10.3", "RV", remoteFilePath, destinationPath);

        SSHConnector ssh = new SSHConnector();
        Session session;
        try {
            session = ssh.execute(connect).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String filepath = destinationPath + "/" + remoteFilePath;

        try (FileInputStream fis = new FileInputStream(new File(filepath));
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder content = new StringBuilder();
            String output = "";
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
                Log.d("SSH", line);
                output = line;
            }

            return output;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
