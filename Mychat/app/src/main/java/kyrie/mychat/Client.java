package kyrie.mychat;

/**
 * Created by Kyrie_wei on 01/05/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonWriter;
import android.widget.Toast;

import net.sf.json.JSONObject;
//import org.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSON;

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
import java.util.List;
import java.util.logging.SocketHandler;


public class Client{
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

    public String isSuccess = server_failed;
    public static ArrayList<friendInfo> friendList = new ArrayList<friendInfo>();
    public boolean loading = false;

    public Client(){}

    public  void registerRequest(registerUser user){
        final registerUser _user = user;
        new Thread() {
            public void run() {
                super.run();
                try {
                    socket = new Socket(IP_ADDR, PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
                    JSONObject json = new JSONObject();
                    json.put("username",_user.userName);
                    json.put("password",_user.passWord);
                    json.put("type",_user.type);
                    json.put("avatar",_user.avatarUrl_Str);
                    out.write(json.toString() + "\n");
                    out.flush();
                    String data = in.readLine();
                    JSONObject rec_msg = JSONObject.fromObject(data);
                    String is_suc = rec_msg.getString("is_success");

                    System.out.println("!!!!!!!!!!!the byte is: " + is_suc);
                    //System.out.println("!!!!!!!!he has friend: " + friendList);
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

    public  void LoginRequest(loginUser user) {
        final loginUser _user = user;
        new Thread() {
            public void run() {
                super.run();
                try {
                    System.out.println("start connect to socket!!!!!");
                    socket = new Socket(IP_ADDR, PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
                    JSONObject json = new JSONObject();
                    System.out.println("write successfully!!!!!");
                    json.put("username",_user.userName);
                    json.put("password",_user.password);
                    json.put("type",_user.type);

                    out.write(json.toString() + "\n");
                    out.flush();

                    String data = in.readLine();
                    JSONObject rec_msg = JSONObject.fromObject(data);

                    String is_suc = rec_msg.getString("is_success");
                    JSONArray fri = rec_msg.getJSONArray("friendlist");


                    for (int i = 0; i < fri.size(); i ++){
                        JSONObject item = new JSONObject();
                        item = fri.getJSONObject(i);
                        String friendname = item.getString("friend_name");
                        String friendava = item.getString("friend_ava");
                        friendInfo friend_item = new friendInfo(friendname,friendava);
                        friendList.add(friend_item);
                        loading = true;
                        System.out.println("!!!!!!!!!!!the JSONARRAY is: " + friendname);
                    }

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
