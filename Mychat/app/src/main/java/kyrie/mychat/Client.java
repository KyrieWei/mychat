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
    public static final int PORT = 9602;
    public Socket socket;

    /*final public byte server_failed = 1;
    final public byte username_duplicated = 2;
    final public byte username_not_exist = 3;
    final public byte successed = 0;*/

    public boolean isSuccess = true;


    public  void sendUserInfo(String userName, String userPassword){
        final  String _userName = userName;
        final String _userPassword = userPassword;
        isSuccess = true;
        new Thread() {
            public void run(){
                super.run();
                try {
                    socket = new Socket(IP_ADDR, PORT);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    String str = _userName + "#" + _userPassword;
                    out.writeUTF(str);
                    out.close();

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    byte is_suc = in.readByte();
                    System.out.println("!!!!!!!!!!!the byte is: " + is_suc);
                    in.close();
                } catch(Exception e) {
                    isSuccess = false;
                    System.out.println("something 1 is wrong!!!!!!!!!!");
                    return;
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            System.out.println("something 2 is wrong!!!!!!!!!!");
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
                    String str = _newUserName + "#" + _newUserPassword;
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
