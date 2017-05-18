package kyrie.mychat;

/**
 * Created by Kyrie_wei on 01/05/2017.
 */

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.SocketHandler;


public class Client {
    public static final String IP_ADDR = "23.106.150.31";
    public static final int PORT = 9602;
    public Socket socket;
    public BufferedWriter out;
    public BufferedReader in;

    final public String server_failed = "1";
    final public String username_duplicated = "2";
    final public String username_not_exist = "3";
    final public String password_wrong = "4";
    final public String successed = "0";

    public String isSuccess = successed;
    public ArrayList<String> friendList = new ArrayList<String>();



    public  void sendUserInfo(String userName, String userPassword, String type){
        final  String _userName = userName;
        final String _userPassword = userPassword;
        final String _type = type;
        new Thread() {
            public void run(){
                super.run();
                    try {
                        socket = new Socket(IP_ADDR, PORT);
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        String str = _type + "#" + _userName + "#" + _userPassword;
                        out.writeUTF(str);
                        String is_suc = in.readUTF();
                        System.out.println("!!!!!!!!!!!the byte is: " + is_suc);
                        if(is_suc.equals(username_duplicated)){
                            isSuccess = username_duplicated;
                        }else if(is_suc.equals(username_not_exist)){
                            isSuccess = username_not_exist;
                        }else if(is_suc.equals(password_wrong)){
                            isSuccess = password_wrong;
                        } else{
                            isSuccess = successed;
                        }
                        out.close();
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        isSuccess = server_failed;
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


    public  void LoginRequest(User user) {
        final User _user = user;
        new Thread() {
            public void run() {
                super.run();
                try {
                    socket = new Socket(IP_ADDR, PORT);
                    //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    //DataInputStream in = new DataInputStream(socket.getInputStream());
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
                    JSONObject json = new JSONObject();
                    json.put("username",_user.userName);
                    json.put("password",_user.passWord);
                    //json.put("mIP_ADDR",_user.IP_ADDR);
                    //json.put("mPORT",_user.PORT);
                    json.put("type",_user.type);
                    //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
                    out.write(json.toString() + "\n");
                    out.flush();
                    String data = in.readLine();
                    //String is_suc = in.readUTF();
                    JSONObject rec_msg = new JSONObject(data);
                    String is_suc = rec_msg.getString("is_success");
                    JSONArray fri = rec_msg.getJSONArray("friendlist");
                    for (int i = 0; i < fri.length(); i ++){
                        friendList.add(fri.getString(i));
                    }
                    System.out.println("!!!!!!!!!!!the byte is: " + is_suc);
                    System.out.println("!!!!!!!!he has friend: " + friendList);
                    if(is_suc.equals(username_duplicated)){
                        isSuccess = username_duplicated;
                    }else if(is_suc.equals(username_not_exist)){
                        isSuccess = username_not_exist;
                    }else if(is_suc.equals(password_wrong)){
                        isSuccess = password_wrong;
                    } else{
                        isSuccess = successed;
                    }
                    out.close();
                    in.close();
                } catch (Exception e) {
                    isSuccess = server_failed;
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
