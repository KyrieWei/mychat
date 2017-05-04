package kyrie.mychat;

/**
 * Created by Kyrie_wei on 01/05/2017.
 */

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static final String IP_ADDR = "23.106.150.31";
    public static final int PORT = 4000;
    public Socket socket;

    public  void sendUserInfo(final String userName, String userPassword) {
        final  String _userName = userName;
        final String _userPassword = userPassword;
        new Thread() {
            public void run(){
                super.run();
                try {
                    socket = new Socket(IP_ADDR, PORT);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    String str = _userName + "#" + _userPassword;
                    out.writeUTF(str);
                    out.close();
                } catch(Exception e) {
                    return;
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            return;
                        }
                    }
                }
            }
        }.start();
    }

    public  void sendNewUserRegInfo(String newUserName, String newUserPassword, String conPassword) {
        final String _newUserName = newUserName;
        final String _newUserPassword = newUserPassword;
        final String _conPassword = conPassword;
        new Thread() {
            public void run() {
                super.run();
                try {
                    socket = new Socket(IP_ADDR, PORT);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    String str = _newUserName + "#" + _newUserPassword + "#" + _conPassword;
                    out.writeUTF(str);
                    out.close();
                } catch (Exception e) {
                    return;
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            return;
                        }
                    }
                }
            }
        }.start();
    }
}
