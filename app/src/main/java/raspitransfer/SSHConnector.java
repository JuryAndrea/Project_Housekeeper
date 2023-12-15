package raspitransfer;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;

public class SSHConnector extends AsyncTask<connetionargs, Void, Session> {


    private JSch jsch;
    private com.jcraft.jsch.Session session;


    @Override
    protected Session doInBackground(connetionargs... connetionargs) {
        try {
            Log.d("SSH", "connect try: ");
            jsch = new JSch();
            Log.d("SSH", "connect try1: ");
            session = jsch.getSession(connetionargs[0].user, connetionargs[0].host, 22);
            Log.d("SSH", "connect try2: ");

            session.setConfig("StrictHostKeyChecking", "no");
            Log.d("SSH", "connect try3: ");
            session.setPassword(connetionargs[0].pwd);
            Log.d("SSH", "connect try4: ");
            session.connect();
            Log.d("SSH", "connect try5: ");

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            Log.d("SSH", "connect try6: ");
            channelSftp.connect();
            Log.d("SSH", "connect try7: ");

//            // Download the file
            channelSftp.get(connetionargs[0].remoteFilePath, connetionargs[0].localDestinationPath);
            Log.d("SSH", "connect try8: ");

            channelSftp.disconnect();

            Log.d("SSH", "connect try9: ");
            session.disconnect();

            Log.d("SSH", "connect try10: ");

            return session;

        } catch (JSchException e) {
            Log.d("SSH", e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("SSH", e.toString());
        }
        return null;
    }


    public void executeCommand(String command, Session session) {
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            Log.d("SSH", "executeCommand: ");
            // Read the command output
            StringBuilder outputBuffer = new StringBuilder();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    outputBuffer.append(new String(tmp, 0, i));
                    Log.d("SSH", outputBuffer.toString());
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Process the outputBuffer as needed
            String commandOutput = outputBuffer.toString();
            System.out.println("Command Output: " + commandOutput);

            channel.disconnect();
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
    }
}
